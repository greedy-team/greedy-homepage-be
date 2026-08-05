-- 1. member 테이블에 discriminator 컬럼 및 ExternalMember 전용 컬럼 추가
ALTER TABLE member ADD COLUMN member_type VARCHAR(31) NOT NULL DEFAULT 'INTERNAL';
ALTER TABLE member ADD COLUMN external_member_role VARCHAR(50);

-- 2. external_member 데이터를 member 테이블로 마이그레이션
INSERT INTO member (name, github_url, image_url, description, main_stack_position, external_member_role, member_type, created_at, updated_at, deleted_at)
SELECT name, github_url, NULL, description, stack_position, external_member_role, 'EXTERNAL', created_at, updated_at, deleted_at
FROM external_member;

-- 3. member_action의 external_member_id 참조를 member_id로 이전
UPDATE member_action ma
    JOIN external_member em ON ma.external_member_id = em.id
    JOIN member m ON m.name = em.name AND m.member_type = 'EXTERNAL'
    SET ma.member_id = m.id
WHERE ma.external_member_id IS NOT NULL;

-- 4. project_member의 external_contributor_id 참조를 member_id로 이전
--    external_contributor는 별도의 프로젝트 참여 레코드로 변환
INSERT INTO project_member (project_id, member_id, stack_position, start_date, end_date, created_at, updated_at, deleted_at)
SELECT pm.project_id, m.id, pm.stack_position, pm.start_date, pm.end_date, pm.created_at, pm.updated_at, pm.deleted_at
FROM project_member pm
    JOIN external_member em ON pm.external_contributor_id = em.id
    JOIN member m ON m.name = em.name AND m.member_type = 'EXTERNAL'
WHERE pm.external_contributor_id IS NOT NULL;

-- 5. member_action의 external_member_id FK 제거
ALTER TABLE member_action DROP FOREIGN KEY fk_member_action_external_member;
ALTER TABLE member_action DROP COLUMN external_member_id;
ALTER TABLE member_action MODIFY COLUMN member_id BIGINT NOT NULL;

-- 6. project_member의 external_contributor_id FK 제거
ALTER TABLE project_member DROP FOREIGN KEY fk_project_member_external_contributor;
ALTER TABLE project_member DROP COLUMN external_contributor_id;

-- 7. external_member 테이블 삭제
DROP TABLE external_member;

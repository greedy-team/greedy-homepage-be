ALTER TABLE external_member ADD COLUMN description TEXT;

ALTER TABLE member_action ADD COLUMN external_member_id BIGINT;
ALTER TABLE member_action MODIFY COLUMN member_id BIGINT NULL;
ALTER TABLE member_action ADD CONSTRAINT fk_member_action_external_member FOREIGN KEY (external_member_id) REFERENCES external_member (id);

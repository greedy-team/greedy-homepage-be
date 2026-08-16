ALTER TABLE member_action MODIFY COLUMN member_role VARCHAR(50) NULL;
ALTER TABLE member_action ADD COLUMN external_member_role VARCHAR(50);

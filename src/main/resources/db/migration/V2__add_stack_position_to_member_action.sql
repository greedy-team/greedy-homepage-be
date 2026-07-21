ALTER TABLE member_action
    ADD COLUMN stack_position VARCHAR(255) NOT NULL AFTER member_role;

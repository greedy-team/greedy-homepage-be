CREATE TABLE IF NOT EXISTS member_department
(
    member_id  BIGINT      NOT NULL,
    department VARCHAR(50) NOT NULL,
    CONSTRAINT fk_member_department_member FOREIGN KEY (member_id) REFERENCES member (id)
);

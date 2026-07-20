CREATE TABLE generation
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    number     INT          NOT NULL,
    start_date DATE         NOT NULL,
    end_date   DATE         NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    deleted_at DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    name                VARCHAR(255) NOT NULL,
    github_url          VARCHAR(255),
    image_url           VARCHAR(255),
    description         TEXT,
    main_stack_position VARCHAR(50)  NOT NULL,
    created_at          DATETIME,
    updated_at          DATETIME,
    deleted_at          DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE member_department
(
    member_id  BIGINT      NOT NULL,
    department VARCHAR(50) NOT NULL,
    CONSTRAINT fk_member_department_member FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE member_action
(
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    member_id     BIGINT      NOT NULL,
    member_role   VARCHAR(50) NOT NULL,
    generation_id BIGINT,
    created_at    DATETIME,
    updated_at    DATETIME,
    deleted_at    DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_member_action_member     FOREIGN KEY (member_id)     REFERENCES member (id),
    CONSTRAINT fk_member_action_generation FOREIGN KEY (generation_id) REFERENCES generation (id)
);

CREATE TABLE external_member
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    name                 VARCHAR(255) NOT NULL,
    github_url           VARCHAR(255),
    external_member_role VARCHAR(50)  NOT NULL,
    created_at           DATETIME,
    updated_at           DATETIME,
    deleted_at           DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE activity
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    start_date  DATE         NOT NULL,
    end_date    DATE         NOT NULL,
    created_at  DATETIME,
    updated_at  DATETIME,
    deleted_at  DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE activity_image
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    activity_id BIGINT       NOT NULL,
    image_url   VARCHAR(500) NOT NULL,
    created_at  DATETIME,
    updated_at  DATETIME,
    deleted_at  DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_activity_image_activity FOREIGN KEY (activity_id) REFERENCES activity (id)
);

CREATE TABLE project
(
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    name                 VARCHAR(255) NOT NULL,
    thumbnail_url        VARCHAR(255),
    site_url             VARCHAR(255),
    backend_github_url   VARCHAR(255),
    frontend_github_url  VARCHAR(255),
    summary              VARCHAR(255),
    main_function        TEXT,
    purpose              TEXT,
    project_type         VARCHAR(50)  NOT NULL,
    generation_id        BIGINT       NOT NULL,
    created_at           DATETIME,
    updated_at           DATETIME,
    deleted_at           DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_project_generation FOREIGN KEY (generation_id) REFERENCES generation (id)
);

CREATE TABLE project_backend_stack
(
    project_id BIGINT      NOT NULL,
    stack      VARCHAR(50) NOT NULL,
    CONSTRAINT fk_project_backend_stack_project FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE project_frontend_stack
(
    project_id BIGINT      NOT NULL,
    stack      VARCHAR(50) NOT NULL,
    CONSTRAINT fk_project_frontend_stack_project FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE project_image
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    project_id BIGINT       NOT NULL,
    image_url  VARCHAR(500) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    deleted_at DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_project_image_project FOREIGN KEY (project_id) REFERENCES project (id)
);

CREATE TABLE project_member
(
    id                      BIGINT      NOT NULL AUTO_INCREMENT,
    project_id              BIGINT      NOT NULL,
    member_id               BIGINT      NOT NULL,
    stack_position          VARCHAR(50) NOT NULL,
    start_date              DATE        NOT NULL,
    end_date                DATE,
    external_contributor_id BIGINT,
    created_at              DATETIME,
    updated_at              DATETIME,
    deleted_at              DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT fk_project_member_project             FOREIGN KEY (project_id)              REFERENCES project (id),
    CONSTRAINT fk_project_member_member              FOREIGN KEY (member_id)               REFERENCES member (id),
    CONSTRAINT fk_project_member_external_contributor FOREIGN KEY (external_contributor_id) REFERENCES external_member (id)
);

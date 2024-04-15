CREATE TABLE REGIONS
(
    id          int8         NOT NULL,
    operator_id int8         NOT NULL,
    code        varchar(128) NOT NULL,
    "name"      varchar(128) NOT NULL,
    description varchar(512) NOT NULL,
    time_zone   varchar(64)  NOT NULL,
    created_at  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT OPERATOR_REGION_CODE_UK UNIQUE (operator_id, code),
    CONSTRAINT REGION_PK PRIMARY KEY (id),
    CONSTRAINT REGION_OPERATOR_FK FOREIGN KEY (operator_id) REFERENCES OPERATORS (id) ON DELETE CASCADE
);
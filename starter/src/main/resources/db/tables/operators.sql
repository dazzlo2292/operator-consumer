CREATE TABLE OPERATORS
(
    id          int8         NOT NULL,
    country_id  int8         NOT NULL,
    code        varchar(128) NOT NULL,
    "name"      varchar(128) NOT NULL,
    description varchar(512) NOT NULL,
    created_at  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT OPERATOR_CODE_UK UNIQUE (code),
    CONSTRAINT OPERATOR_PK PRIMARY KEY (id),
    CONSTRAINT OPERATOR_COUNTRY_FK FOREIGN KEY (country_id) REFERENCES COUNTRIES (id) ON DELETE CASCADE
);
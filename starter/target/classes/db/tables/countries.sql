CREATE TABLE COUNTRIES
(
    id          int8         NOT NULL,
    code        varchar(128) NOT NULL,
    "name"      varchar(128) NOT NULL,
    description varchar(512) NOT NULL,
    created_at  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT COUNTRY_CODE_UK UNIQUE (code),
    CONSTRAINT COUNTRY_ID_PK PRIMARY KEY (id)
);
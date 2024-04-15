CREATE TABLE PREFIXES
(
    id         int8       NOT NULL,
    region_id  int8       NOT NULL DEFAULT 1,
    prefix     varchar(32) NULL,
    mnp_fl     varchar(1) NOT NULL DEFAULT 'N':: character varying,
    manual_fl  varchar(1) NOT NULL DEFAULT 'N':: character varying,
    created_at timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PREFIXE_MNP_FL_CH CHECK (((mnp_fl)::text = ANY ((ARRAY['Y':: character varying, 'N':: character varying])::text[])
) ),
	CONSTRAINT PREFIXE_PK PRIMARY KEY (id),
	CONSTRAINT PREFIX_UK UNIQUE (prefix),
	CONSTRAINT manual_fl_ch CHECK (((manual_fl)::text = ANY ((ARRAY['Y'::character varying, 'N'::character varying])::text[]))),
	CONSTRAINT PREFIX_REGION_FK FOREIGN KEY (region_id) REFERENCES REGIONS(id) ON DELETE CASCADE
);
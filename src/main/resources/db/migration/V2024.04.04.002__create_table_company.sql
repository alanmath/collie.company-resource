CREATE TABLE company
(
    id character varying(36) NOT NULL,
    name character varying(256) NOT NULL,
    description character varying(256) NOT NULL,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

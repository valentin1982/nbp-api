DROP TABLE  IF EXISTS exchangeratesseries ;

CREATE TABLE exchangeratesseries (
  id BIGINT NOT NULL ,
  effectivedate VARCHAR(255),
  mid DOUBLE,
  no VARCHAR(255),
  PRIMARY KEY (id));
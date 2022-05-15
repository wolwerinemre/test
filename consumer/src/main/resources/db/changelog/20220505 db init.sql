--liquibase formatted sql
--changeset emre.caglan:0:0

CREATE TABLE request (
  id          BIGINT        NOT NULL  AUTO_INCREMENT,
  name        VARCHAR(100)  NOT NULL  ,
  surname     VARCHAR(100)  NOT NULL  ,
  wage        NUMERIC(7,2)  NOT NULL  ,
  event_time  DATETIME      NOT NULL  ,
  tax         NUMERIC(7,2)  NOT NULL  ,
  PRIMARY KEY(id)
)
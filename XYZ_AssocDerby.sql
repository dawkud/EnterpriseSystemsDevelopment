CREATE TABLE Claims (
  "id" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "mem_id" varchar(20) NOT NULL,
  "date" date NOT NULL,
  "description" long varchar NOT NULL,
  "status" varchar(10) NOT NULL,
  "amount" float NOT NULL
);

TRUNCATE TABLE Claims;

CREATE TABLE Members (
  "id" varchar(20)  NOT NULL primary key,
  "name" varchar(40),
  "address" long varchar,
  "dob" date DEFAULT NULL,
  "dor" date DEFAULT NULL,
  "status" varchar(10) NOT NULL,
  "balance" float NOT NULL
);


TRUNCATE TABLE Members;

CREATE TABLE payments (
  "id" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "mem_id" varchar(20) NOT NULL,
  "type_of_payment" char(10) NOT NULL,
  "amount" float NOT NULL,
  "date" date NOT NULL,
  "time" time Not Null
);

TRUNCATE TABLE payments;

CREATE TABLE users (
  "id" varchar(20) NOT NULL primary key,
  "password" varchar(20) NOT NULL,
  "status" char(10) NOT NULL
);


TRUNCATE TABLE users;

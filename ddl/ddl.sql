CREATE TABLE "user" (
  "user_id" SERIAL PRIMARY KEY,
  "username" varchar NOT NULL,
  "password" varchar NOT NULL,
  "role_id" int NOT NULL
);

CREATE TABLE "role" (
  "role_id" SERIAL PRIMARY KEY,
  "role_name" varchar NOT NULL
);

CREATE TABLE "contact" (
  "contact_id" SERIAL PRIMARY KEY,
  "name" varchar NOT NULL,
  "info" varchar NOT NULL,
  "phone_number" varchar NOT NULL,
  "email" varchar NOT NULL,
  "user_id" int NOT NULL,
  "type_id" int NOT NULL
);

CREATE TABLE "contact_type" (
  "type_id" SERIAL PRIMARY KEY,
  "type_name" varchar NOT NULL
);

ALTER TABLE "user" ADD FOREIGN KEY ("role_id") REFERENCES "role" ("role_id");

ALTER TABLE "contact" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("user_id");

ALTER TABLE "contact" ADD FOREIGN KEY ("type_id") REFERENCES "contact_type" ("type_id");

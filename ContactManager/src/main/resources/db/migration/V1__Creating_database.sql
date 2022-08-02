CREATE TABLE "users" (
                        "user_id" SERIAL PRIMARY KEY,
                        "username" varchar NOT NULL,
                        "password" varchar NOT NULL,
                        "role" varchar NOT NULL
);

CREATE TABLE "contacts" (
                           "contact_id" SERIAL PRIMARY KEY,
                           "name" varchar NOT NULL,
                           "info" varchar NOT NULL,
                           "phone_number" varchar NOT NULL,
                           "email" varchar NOT NULL,
                           "user_id" int NOT NULL,
                           "type_id" int NOT NULL
);

CREATE TABLE "contact_types" (
                                "type_id" SERIAL PRIMARY KEY,
                                "type_name" varchar NOT NULL
);

ALTER TABLE "contacts" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "contacts" ADD FOREIGN KEY ("type_id") REFERENCES "contact_types" ("type_id");

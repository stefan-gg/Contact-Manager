CREATE TABLE "users" (
                        "id" SERIAL PRIMARY KEY,
                        "email" varchar(100) NOT NULL,
                        "password" varchar(255) NOT NULL,
                        "first_name" varchar(50) NOT NULL,
                        "last_name" varchar(50) NOT NULL,
                        "role" varchar(20) NOT NULL
);

CREATE TABLE "contacts" (
                           "id" SERIAL PRIMARY KEY,
                           "first_name" varchar(50) NOT NULL,
                           "last_name" varchar(50) NOT NULL,
                           "info" varchar(100),
                           "address" varchar(100),
                           "phone_number" varchar(50) NOT NULL,
                           "email" varchar(100) NOT NULL,
                           "user_id" int NOT NULL,
                           "contact_type_id" int NOT NULL
);

CREATE TABLE "contact_types" (
                                "id" SERIAL PRIMARY KEY,
                                "contact_type_name" varchar(50) NOT NULL
);

ALTER TABLE "contacts" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "contacts" ADD FOREIGN KEY ("contact_type_id") REFERENCES "contact_types" ("id");

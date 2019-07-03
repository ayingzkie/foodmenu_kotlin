-- CREATE TABLE "public"."users"  (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `name` varchar(40) NOT NULL,
--   `username` varchar(15) NOT NULL,
--   `email` varchar(40) NOT NULL,
--   `password` varchar(100) NOT NULL,
--   `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
--   `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
--   PRIMARY KEY (`id`),
--   UNIQUE KEY `uk_users_username` (`username`),
--   UNIQUE KEY `uk_users_email` (`email`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--
-- CREATE TABLE "public"."roles"  (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `name` varchar(60) NOT NULL,
--   PRIMARY KEY (`id`),
--   UNIQUE KEY `uk_roles_name` (`name`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
--
--
-- CREATE TABLE "public"."user_roles"  (
--   `user_id` bigint(20) NOT NULL,
--   `role_id` bigint(20) NOT NULL,
--   PRIMARY KEY (`user_id`,`role_id`),
--   KEY `fk_user_roles_role_id` (`role_id`),
--   CONSTRAINT `fk_user_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
--   CONSTRAINT `fk_user_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE "public"."users" (
  "id" uuid NOT NULL,
  "name" varchar(255),
  "username" varchar(255),
  "email" varchar(255),
  "password" varchar(255),
  "created_at" timestamp,
  "updated_at" timestamp,
  PRIMARY KEY ("id"),
  CONSTRAINT "uk_users_username" UNIQUE ("username"),
  CONSTRAINT "uk_users_email" UNIQUE ("email")
)
;

CREATE TABLE "public"."roles" (
  "id" int4 NOT NULL,
  "name" varchar(255),
  PRIMARY KEY ("id"),
  CONSTRAINT "uk_roles_name" UNIQUE ("name")
)
;

CREATE TABLE "public"."user_roles" (
  "user_id" uuid NOT NULL,
  "role_id" int4 NOT NULL,
  PRIMARY KEY ("user_id", "role_id"),
  CONSTRAINT "fk_user_roles_role_id" FOREIGN KEY ("role_id") REFERENCES "public"."roles" ("id"),
  CONSTRAINT "fk_user_roles_user_id" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id")
)
;


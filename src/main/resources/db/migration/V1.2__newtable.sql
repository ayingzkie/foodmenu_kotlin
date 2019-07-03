CREATE TABLE "public"."food_menu" (
  "id" uuid NOT NULL,
  "name" varchar,
  "description" varchar,
  "type" varchar,
  "price" numeric,
  "created_at" timestamp,
  "updated_at" timestamp,
  "created_by" varchar,
  "updated_by" varchar,
  "image" bytea,
  PRIMARY KEY ("id")
)
;
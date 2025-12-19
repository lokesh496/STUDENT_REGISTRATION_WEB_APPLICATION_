-- PostgreSQL schema for Student Management (Neon compatible)
-- NOTE: On hosted Neon you typically connect to an existing database. This script
-- creates the `student_details` table in the connected database. Do NOT run
-- `CREATE DATABASE` on Neon-managed clusters.
-- PostgreSQL schema for Student Management (Neon compatible)
-- This script:
-- 1) creates a `courses` table
-- 2) creates a normalized `students` table referencing `courses`
-- 3) inserts the 10 predefined courses (idempotent)
-- 4) creates useful indexes
-- 5) (optional) keeps a legacy `student_details` table for simple apps

BEGIN;

-- 1) Courses
CREATE TABLE IF NOT EXISTS courses (
    id SERIAL PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- insert the 10 predefined courses (idempotent)
INSERT INTO courses (course_name, description)
VALUES
  ('Java','Java programming'),
  ('Python','Python programming'),
  ('C++','C++ programming'),
  ('Data Science','Data Science track'),
  ('Artificial Intelligence','AI topics'),
  ('Machine Learning','ML topics'),
  ('Web Development','Frontend + Backend web dev'),
  ('Cloud Computing','Cloud fundamentals'),
  ('Cyber Security','Security practices'),
  ('DevOps','DevOps workflows')
ON CONFLICT (course_name) DO NOTHING;

-- 2) Students (normalized)
CREATE TABLE IF NOT EXISTS students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    course_id INT NOT NULL,
    phone VARCHAR(15),
    status VARCHAR(8) CHECK (status IN ('ACTIVE', 'INACTIVE')) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    notes TEXT,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- 3) Indexes for students
CREATE INDEX IF NOT EXISTS idx_name ON students(name);
CREATE INDEX IF NOT EXISTS idx_course_id ON students(course_id);
CREATE INDEX IF NOT EXISTS idx_status ON students(status);
CREATE INDEX IF NOT EXISTS idx_created_at ON students(created_at);
CREATE INDEX IF NOT EXISTS idx_deleted_at ON students(deleted_at);

-- 4) (Optional) legacy simple table used by older app versions
CREATE TABLE IF NOT EXISTS student_details (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    course VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX IF NOT EXISTS idx_student_email ON student_details(email);

COMMIT;

-- Usage note:
-- Run this script against the target database on Neon. Do NOT run CREATE DATABASE.
-- Example psql (local psql client):
-- psql "host=ep-curly-art-a456yhbk-pooler.us-east-1.aws.neon.tech dbname=neondb user=neondb_owner password=npg_KpDh4oXLFAG8 sslmode=require" -f db/schema.sql

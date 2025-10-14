-- ============================================================
-- Flyway Migration: Initial Schema
-- PostgreSQL 10+ DDL for Fire Department Management System
-- Using GENERATED ALWAYS AS IDENTITY
-- ============================================================

-- ========== USERS ==========
CREATE TABLE users
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255),
    enabled  BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE user_roles
(
    user_id BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role    VARCHAR(100) NOT NULL,
    PRIMARY KEY (user_id, role)
);

-- ========== ADDRESSES ==========
CREATE TABLE address
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    street      VARCHAR(255),
    city        VARCHAR(255),
    postal_code VARCHAR(20),
    country     VARCHAR(100)
);

-- ========== FIRE STATIONS ==========
CREATE TABLE fire_station
(
    id               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    station_number   VARCHAR(50),
    phone_number     VARCHAR(50),
    email            VARCHAR(255),
    commander_name   VARCHAR(255),
    address_id       BIGINT       REFERENCES address (id) ON DELETE SET NULL,
    established_date DATE,
    active           BOOLEAN DEFAULT TRUE
);

-- ========== FIREMEN ==========
CREATE TABLE fireman
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    surname         VARCHAR(255) NOT NULL,
    address_id      BIGINT       REFERENCES address (id) ON DELETE SET NULL,
    id_number       VARCHAR(50),
    bank_account    VARCHAR(50),
    fire_station_id BIGINT       REFERENCES fire_station (id) ON DELETE SET NULL
);

-- ========== FIRE TRUCKS ==========
CREATE TABLE fire_truck
(
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    side_number        VARCHAR(100) NOT NULL,
    plate_number       VARCHAR(100) NOT NULL UNIQUE,
    model              VARCHAR(255),
    year_of_production INTEGER,
    capacity_liters    INTEGER,
    type               VARCHAR(100),
    type_description   TEXT,
    station_id         BIGINT       REFERENCES fire_station (id) ON DELETE SET NULL,
    event_id           BIGINT       REFERENCES event (id) ON DELETE SET NULL,
    remarks            TEXT
);

-- ========== EVENTS ==========
CREATE TABLE event
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name            VARCHAR(255)     NOT NULL,
    description     TEXT,
    place           VARCHAR(255)     NOT NULL,
    date            DATE             NOT NULL,
    duration_hours  DOUBLE PRECISION NOT NULL,
    start_timestamp TIMESTAMP,
    end_timestamp   TIMESTAMP,
    mileage         INTEGER,
    fire_units      INTEGER,
    CONSTRAINT chk_duration_positive CHECK (duration_hours > 0)
);

-- Many-to-many: event <-> fireman
CREATE TABLE event_fireman
(
    event_id   BIGINT NOT NULL REFERENCES event (id) ON DELETE CASCADE,
    fireman_id BIGINT NOT NULL REFERENCES fireman (id) ON DELETE CASCADE,
    PRIMARY KEY (event_id, fireman_id)
);

-- ========== REPORTS ==========
CREATE TABLE report
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type     VARCHAR(20) NOT NULL CHECK (type IN ('WORD', 'XML', 'PDF')),
    content  BYTEA       NOT NULL,
    event_id BIGINT      NOT NULL REFERENCES event (id) ON DELETE CASCADE,
    user_id  BIGINT      NOT NULL REFERENCES users (id)
);

-- Indexes for performance
CREATE INDEX idx_report_event ON report (event_id);
CREATE INDEX idx_report_user ON report (user_id);
CREATE INDEX idx_event_date ON event (date);
CREATE INDEX idx_fireman_station ON fireman (fire_station_id);

-- ============================================================
-- END OF MIGRATION
-- ============================================================
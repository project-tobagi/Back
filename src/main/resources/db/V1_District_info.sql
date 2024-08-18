CREATE TABLE region_boundary (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    boundary_data JSONB NOT NULL
);
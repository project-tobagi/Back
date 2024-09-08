-- -- Create replication user
-- CREATE USER repluser REPLICATION LOGIN ENCRYPTED PASSWORD 'replpassword';
--
-- -- Configure WAL settings
-- ALTER SYSTEM SET wal_level = 'replica';
-- ALTER SYSTEM SET max_wal_senders = '3';
-- ALTER SYSTEM SET wal_keep_size = '64MB';
-- ALTER SYSTEM SET listen_addresses = '*';
--
-- -- Create a replication slot for the slave
-- SELECT * FROM pg_create_physical_replication_slot('replica_slot');
--
-- -- Reload configuration to apply the changes
-- SELECT pg_reload_conf();

-- configuration for json file which contains geo format.
CREATE EXTENSION IF NOT EXISTS postgis;
-- need temp table for load json data
CREATE TEMPORARY TABLE json_data (data JSONB);
-- loaded json file into memory
COPY json_data (data) FROM '/docker-entrypoint-initdb.d/region.json';
-- create GeoJSON table
CREATE TABLE geojson_features (
    id SERIAL PRIMARY KEY,
    feature_type VARCHAR(50),
    geometry GEOMETRY(MultiPolygon, 3857),
    properties JSONB
);
-- Use ST_Multi to convert Polygons to MultiPolygons
INSERT INTO geojson_features (feature_type, geometry, properties)
SELECT
    features->>'type' AS feature_type,
    ST_SetSRID(
        CASE
            WHEN (features->'geometry'->>'type') = 'Polygon' THEN ST_Multi(ST_GeomFromGeoJSON(features->>'geometry'))
            ELSE ST_GeomFromGeoJSON(features->>'geometry')
        END,
        3857
    ) AS geometry,
    (features->'properties')::jsonb AS properties
FROM
    json_data,
    jsonb_array_elements(json_data.data->'features') AS features;

-- get polygon by district name.
-- SELECT
--     properties->>'EMD_KOR_NM' AS emd_kor_nm,
--     ST_AsText(ST_Envelope(ST_Collect(geometry))) AS bounding_box
-- FROM
--     geojson_features
-- WHERE
--     properties->>'EMD_KOR_NM' = '청운동'
-- GROUP BY
--     properties->>'EMD_KOR_NM';

-- get all name of district.
-- SELECT id, properties->>'EMD_KOR_NM' AS property_value
-- FROM geojson_features
-- WHERE properties ? 'EMD_KOR_NM'
--     LIMIT 10;


-- Create the subway_stations table
CREATE TABLE subway_stations (
    id SERIAL PRIMARY KEY,
    철도운영기관명 VARCHAR(100),
    선명 VARCHAR(50),
    역명 VARCHAR(100),
    지번주소 TEXT,
    도로명주소 TEXT
);

-- Load JSON data into a temporary table
CREATE TEMPORARY TABLE temp_subway_stations (data JSONB);

-- Read the JSON file content and insert it into the temporary table
DO $$
DECLARE
    json_content TEXT;
BEGIN
    json_content := pg_read_file('/docker-entrypoint-initdb.d/subway_stations.json');
    INSERT INTO temp_subway_stations (data) VALUES (json_content::JSONB);
END $$;

-- Insert data into the subway_stations table
INSERT INTO subway_stations (철도운영기관명, 선명, 역명, 지번주소, 도로명주소)
SELECT
    data->>'철도운영기관명' AS 철도운영기관명,
    data->>'선명' AS 선명,
    data->>'역명' AS 역명,
    data->>'지번주소' AS 지번주소,
    data->>'도로명주소' AS 도로명주소
FROM temp_subway_stations;
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
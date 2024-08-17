package hello.hello_spring.tobagi.map.entity;

import com.fasterxml.jackson.databind.JsonNode;

public class Boundary {
    private String name;
    private JsonNode geometry;

    public Boundary(String name, JsonNode geometry) {
        this.name = name;
        this.geometry = geometry;
    }

    public JsonNode getGeometry() {
        return geometry;
    }
}
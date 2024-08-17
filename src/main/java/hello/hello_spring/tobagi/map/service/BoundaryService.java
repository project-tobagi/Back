package hello.hello_spring.tobagi.map.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.hello_spring.tobagi.map.entity.Boundary;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Service;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoundaryService {

    private List<Boundary> boundaries;
    private GeometryFactory geometryFactory = new GeometryFactory();

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/administrative_boundaries.geojson");
            if (inputStream == null) {
                throw new IOException("Resource not found: /administrative_boundaries.geojson");
            }
            JsonNode root = mapper.readTree(inputStream);
            boundaries = new ArrayList<>();
            for (JsonNode feature : root.get("features")) {
                JsonNode nameNode = feature.get("properties").get("SIG_KOR_NM");
                if (nameNode == null) {
                    throw new IOException("Missing 'properties' or 'SIG_KOR_NM' in feature");
                }
                String name = nameNode.asText();
                JsonNode geometry = feature.get("geometry");
                boundaries.add(new Boundary(name, geometry));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize boundaries", e);
        }
    }

    public JsonNode findBoundaryByCoordinate(double latitude, double longitude) {
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        System.out.println("point: " + point);

        for (Boundary boundary : boundaries) {
            if (isPointInPolygon(point, boundary.getGeometry())) {
                return boundary.getGeometry();
            }
        }

        return null;
    }

    private boolean isPointInPolygon(Point point, JsonNode geometryJson) {
        try {
            GeoJsonReader reader = new GeoJsonReader(geometryFactory);
            Geometry geometry = reader.read(geometryJson.toString());
            return geometry.contains(point);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

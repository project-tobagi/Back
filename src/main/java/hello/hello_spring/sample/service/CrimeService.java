package hello.hello_spring.sample.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.hello_spring.sample.data.CrimeData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrimeService {

    @Value("${api.crime.url}")
    private String apiUrl;

    @Value("${api.crime.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CrimeService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<CrimeData> getCrimeData() {
        String url = String.format("%s?apiKey=%s", apiUrl, apiKey);
        String response = restTemplate.getForObject(url, String.class);

        List<CrimeData> crimeDataList = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode items = root.path("response").path("body").path("items");

            for (JsonNode item : items) {
                CrimeData crimeData = new CrimeData();
                crimeData.setDate(item.path("date").asText());
                crimeData.setLocation(item.path("location").asText());
                crimeData.setCrimeType(item.path("crimeType").asText());
                crimeDataList.add(crimeData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return crimeDataList;
    }
}

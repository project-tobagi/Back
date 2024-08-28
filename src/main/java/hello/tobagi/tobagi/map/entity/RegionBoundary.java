package hello.tobagi.tobagi.map.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegionBoundary {
    private Long id;
    private String name;
    private String boundaryData;
}
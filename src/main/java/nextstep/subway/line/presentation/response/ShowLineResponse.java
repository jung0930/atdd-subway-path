package nextstep.subway.line.presentation.response;

import nextstep.subway.line.domain.Line;
import nextstep.subway.station.service.StationDto;

import java.util.List;
import java.util.stream.Collectors;

public class ShowLineResponse {

    private Long lineId;

    private String name;

    private String color;

    private List<StationDto> stations;

    private Integer distance;

    private ShowLineResponse() {
    }

    public ShowLineResponse(Long lineId, String name, String color, List<StationDto> stations, Integer distance) {
        this.lineId = lineId;
        this.name = name;
        this.color = color;
        this.stations = stations;
        this.distance = distance;
    }

    public static ShowLineResponse from(Line line) {
        return new ShowLineResponse(
                line.getLineId(),
                line.getName(),
                line.getColor(),
                line.getStations().stream()
                        .distinct()
                        .map(StationDto::from)
                        .collect(Collectors.toList()),
                line.getDistance()
        );
    }

    public Long getLineId() {
        return lineId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<StationDto> getStations() {
        return stations;
    }

    public Integer getDistance() {
        return distance;
    }

}
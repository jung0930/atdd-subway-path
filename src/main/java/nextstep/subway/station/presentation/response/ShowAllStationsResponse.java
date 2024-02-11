package nextstep.subway.station.presentation.response;

import nextstep.subway.station.service.StationDto;

import java.util.List;

public class ShowAllStationsResponse {

    private List<StationDto> stations;

    private ShowAllStationsResponse(List<StationDto> stationDtos) {
        this.stations = stationDtos;
    }

    public static ShowAllStationsResponse of(List<StationDto> stationDtos) {
        return new ShowAllStationsResponse(stationDtos);
    }

    public List<StationDto> getStations() {
        return stations;
    }

}

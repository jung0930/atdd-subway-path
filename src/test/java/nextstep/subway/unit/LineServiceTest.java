package nextstep.subway.unit;

import nextstep.subway.common.Constant;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.presentation.request.AddSectionRequest;
import nextstep.subway.line.service.LineService;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.domain.StationRepository;
import nextstep.subway.station.service.StationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class LineServiceTest {

    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private LineService lineService;

    @Test
    void addSection() {
        // given
        Station 신논현역 = stationRepository.save(Station.from(Constant.신논현역));
        Station 강남역 = stationRepository.save(Station.from(Constant.강남역));
        Line 신분당선 = lineRepository.save(Line.of(Constant.신분당선, Constant.빨간색));
        Long 신분당선_ID = 신분당선.getLineId();

        // when
        lineService.addSection(신분당선_ID, AddSectionRequest.of(신논현역.getStationId(), 강남역.getStationId(), Constant.기본_역_간격));

        // then
        assertThat(lineService.findLine(신분당선_ID).getStations()).hasSize(2);
        assertThat(lineService.findLine(신분당선_ID).getStations()).contains(StationDto.from(신논현역), StationDto.from(강남역));
    }

}

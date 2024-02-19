package nextstep.subway.acceptance.path;

import nextstep.subway.acceptance.util.CommonAcceptanceTest;
import nextstep.subway.common.Constant;
import nextstep.subway.line.presentation.request.CreateLineRequest;
import nextstep.subway.line.presentation.response.CreateLineResponse;
import nextstep.subway.path.presentation.response.FindPathResponse;
import nextstep.subway.station.presentation.request.CreateStationRequest;
import nextstep.subway.station.presentation.response.CreateStationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.subway.acceptance.line.LineAcceptanceStep.지하철_노선_생성;
import static nextstep.subway.acceptance.path.PathAcceptanceStep.지하철_최단_경로_조회;
import static nextstep.subway.acceptance.station.StationAcceptanceStep.지하철_역_생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철 경로 검색")
class PathAcceptanceTest extends CommonAcceptanceTest {

    private Long 교대역_ID;
    private Long 강남역_ID;
    private Long 양재역_ID;
    private Long 남부터미널역_ID;

    private Long 이호선_ID;
    private Long 삼호선_ID;
    private Long 신분당선_ID;

    /**
     * 교대역    --- *2호선* ---   강남역
     * |                        |
     * *3호선*                   *신분당선*
     * |                        |
     * 남부터미널역  --- *3호선* ---   양재역
     */

    @BeforeEach
    protected void setUp() {
        교대역_ID = 지하철_역_생성(CreateStationRequest.from(Constant.교대역)).as(CreateStationResponse.class).getStationId();
        강남역_ID = 지하철_역_생성(CreateStationRequest.from(Constant.강남역)).as(CreateStationResponse.class).getStationId();
        양재역_ID = 지하철_역_생성(CreateStationRequest.from(Constant.양재역)).as(CreateStationResponse.class).getStationId();
        남부터미널역_ID = 지하철_역_생성(CreateStationRequest.from(Constant.남부터미널역)).as(CreateStationResponse.class).getStationId();

        이호선_ID = 지하철_노선_생성(CreateLineRequest.of(Constant.이호선, Constant.초록색, 교대역_ID, 강남역_ID, Constant.역_간격_15)).as(CreateLineResponse.class).getLineId();
//        삼호선_ID = 지하철_노선_생성(CreateLineRequest.of(Constant.삼호선, Constant.주황색, 교대역_ID, 남부터미널역_ID, Constant.역_간격_10)).as(CreateLineResponse.class).getLineId();
//        지하철_구간_추가(AddSectionRequest.of(남부터미널역_ID, 양재역_ID, Constant.역_간격_15), 삼호선_ID);
        신분당선_ID = 지하철_노선_생성(CreateLineRequest.of(Constant.신분당선, Constant.빨간색, 강남역_ID, 양재역_ID, Constant.역_간격_10)).as(CreateLineResponse.class).getLineId();
    }

    /**
     * Given 지하철 역/구간/노선을 생성하고
     * When 같은 노선의 출발역과 도착역의 경로를 검색하면
     * Then 최단 경로를 알려준다.
     */
    @DisplayName("같은 출발역과 도착역의 최단 경로를 조회한다.")
    @Test
    void 같은_노선의_출발역과_도착역의_최단_경로_조회() {
        // when
        FindPathResponse 경로_조회_응답 = 지하철_최단_경로_조회(교대역_ID, 양재역_ID).as(FindPathResponse.class);

        // then
        assertThat(경로_조회_응답.getDistance()).isEqualTo(Constant.역_간격_15 + Constant.역_간격_10);
        assertThat(경로_조회_응답.getStations()).hasSize(2);
    }

    /**
     * Given 지하철 역/구간/노선을 생성하고
     * When 여러 노선의 출발역과 도착역의 경로를 검색하면
     * Then 최단 경로를 알려준다.
     */
    @DisplayName("여러 출발역과 도착역의 최단 경로를 조회한다.")
    @Test
    @Disabled
    void 여러_노선의_출발역과_도착역의_최단_경로_조회() {
    }

}
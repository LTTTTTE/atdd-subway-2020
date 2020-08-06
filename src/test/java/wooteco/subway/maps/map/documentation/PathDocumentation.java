package wooteco.subway.maps.map.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;
import wooteco.security.core.TokenResponse;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.ui.MapController;
import wooteco.subway.maps.station.dto.StationResponse;

@WebMvcTest(controllers = {MapController.class})
public class PathDocumentation extends Documentation {

    @Autowired
    private MapController mapController;
    @MockBean
    private MapService mapService;

    protected TokenResponse tokenResponse;

    @BeforeEach
    public void setUp(WebApplicationContext context,
        RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        tokenResponse = new TokenResponse("token");
    }

    @Test
    void findPath() {
        List<StationResponse> stationResponses = Arrays.asList(
            new StationResponse(1L, "ㅇㅇ역", null, null),
            new StationResponse(2L, "X X역", null, null),
            new StationResponse(3L, "G G역", null, null)
        );
        PathResponse pathResponse = new PathResponse(stationResponses, 5, 10, 1350);
        when(mapService.findPath(anyLong(), anyLong(), any(), any())).thenReturn(pathResponse);

        given().log().all().
            header("Authorization", "Bearer " + tokenResponse.getAccessToken()).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            when().
            get("/paths?source={sourceId}&target={targetId}&type={type}", 1, 3, PathType.DISTANCE).
            then().
            log().all().
            apply(document("path",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")),
                requestParameters(
                    parameterWithName("source").description("출발역 아이디"),
                    parameterWithName("target").description("도착역 아이디"),
                    parameterWithName("type").description("길찾는 타입")
                )
                , responseFields(
                    fieldWithPath("stations[]").type(JsonFieldType.ARRAY).description("경로 목록"),
                    fieldWithPath("stations[].id").type(JsonFieldType.NUMBER)
                        .description("경로 station id"),
                    fieldWithPath("stations[].name").type(JsonFieldType.STRING)
                        .description("경로 station name"),
                    fieldWithPath("duration").type(JsonFieldType.NUMBER).description("경로 걸린 시간"),
                    fieldWithPath("distance").type(JsonFieldType.NUMBER).description("경로 걸린 거리"),
                    fieldWithPath("fare").type(JsonFieldType.NUMBER).description("경로 금액")
                )
            )).
            extract();
    }
}

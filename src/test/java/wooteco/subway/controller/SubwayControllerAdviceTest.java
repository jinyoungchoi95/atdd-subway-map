package wooteco.subway.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.subway.dto.line.LineSaveRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubwayControllerAdviceTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("없는 경로 api 요청")
    void notFoundUrl() {
        RestAssured.given().log().all()
                .when()
                .post("/test/not_found")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("name이 null이고 distance가 음수인 line save request dto 요청할 경우 예외가 발생한다.")
    void invalidNullNameLineSaveRequest() {
        LineSaveRequest request = new LineSaveRequest(null, "bg-red-600", 1, 2, -1);
        String nameNullErrorMessage = "line 이름은 공백 혹은 null이 들어올 수 없습니다.";
        String distanceNegativeErrorMessage = "상행-하행 노선 길이는 양수 값만 들어올 수 있습니다.";

        RestAssured.given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/lines")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", allOf(containsString(nameNullErrorMessage), containsString(distanceNegativeErrorMessage)));
    }
}

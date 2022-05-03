package wooteco.subway.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.dao.LineDao;
import wooteco.subway.domain.Line;

class LineServiceTest {

    private LineDao lineDao;
    private LineService lineService;

    @BeforeEach
    void setUp() {
        lineDao = new LineDao();
        lineService = new LineService(lineDao);
    }

    @Test
    @DisplayName("이미 존재하는 노선의 이름이 있을 때 예외가 발생한다.")
    void saveExceptionByExistName() {
        lineDao.save(new Line("신분당선", "bg-red-600"));
        assertThatThrownBy(() -> lineService.save(new Line("신분당선", "bg-green-600")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 노선 이름입니다.");
    }
}
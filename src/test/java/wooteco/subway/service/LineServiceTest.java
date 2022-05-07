package wooteco.subway.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.dao.InmemoryLineDao;
import wooteco.subway.dao.InmemorySectionDao;
import wooteco.subway.dao.InmemoryStationDao;
import wooteco.subway.domain.Line;
import wooteco.subway.exception.NotFoundException;

class LineServiceTest {

    private final InmemoryLineDao lineDao = InmemoryLineDao.getInstance();
    private final InmemorySectionDao sectionDao = InmemorySectionDao.getInstance();
    private final InmemoryStationDao stationDao = InmemoryStationDao.getInstance();
    private final LineService lineService = new LineService(lineDao, sectionDao, new StationService(stationDao));

    @AfterEach
    void afterEach() {
        lineDao.clear();
        sectionDao.clear();
        stationDao.clear();
    }

    @Test
    @DisplayName("이미 존재하는 노선의 이름이 있을 때 예외가 발생한다.")
    void saveExceptionByExistName() {
        lineDao.save(new Line("신분당선", "bg-red-600"));
        assertThatThrownBy(() -> lineService.save(new Line("신분당선", "bg-green-600")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 노선 이름입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 id로 update하려할 경우 예외가 발생한다.")
    void updateExceptionByNotFoundLine() {
        assertThatThrownBy(() -> lineService.update(new Line(1L, "신분당선", "bg-green-600")))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 Line입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 id로 delete하려할 경우 예외가 발생한다.")
    void deleteExceptionByNotFoundLine() {
        assertThatThrownBy(() -> lineService.delete(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 Line입니다.");
    }
}

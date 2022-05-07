package wooteco.subway.dao;

import java.util.List;
import wooteco.subway.domain.Station;

public interface StationDao {

    Station save(Station station);

    List<Station> findAll();

    Station findById(Long id);

    boolean existByName(String name);

    boolean existById(Long id);

    int delete(Long stationId);
}

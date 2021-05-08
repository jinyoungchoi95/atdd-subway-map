package wooteco.subway.line;

import java.util.List;
import java.util.Optional;

public interface LineDao {
    Line save(Line line);

    List<Line> findAll();

    void update(Long id, String name, String color);

    Line findById(Long id);

    Optional<Line> findByName(String name);

    void delete(Long id);
}
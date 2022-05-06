package wooteco.subway.dao;

import wooteco.subway.domain.Section;

public interface SectionDao {

    Section save(Section section);

    boolean existByUpStationIdAndDownStationId(long upStationId, long downStationId);
}

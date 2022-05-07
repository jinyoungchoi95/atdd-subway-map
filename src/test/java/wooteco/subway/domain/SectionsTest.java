package wooteco.subway.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SectionsTest {

    @Test
    @DisplayName("생성 시 size가 0이면 예외 발생")
    void createExceptionByEmptySize() {
        assertThatThrownBy(() -> new Sections(new ArrayList<>()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("sections는 크기가 0으로는 생성할 수 없습니다.");
    }

    @Test
    @DisplayName("정렬된 Station을 반환할 수 있다.")
    void calculateSortedStations() {
        // given
        Station station1 = new Station(1L, "오리");
        Station station2 = new Station(2L, "배카라");
        Station station3 = new Station(3L, "오카라");
        Station station4 = new Station(4L, "배리");
        Sections sections = new Sections(List.of(new Section(3L, 1L, station3, station4, 4),
                new Section(1L, 1L, station1, station2, 1),
                new Section(2L, 1L, station2, station3, 2)));

        // when
        Stations stations = sections.calculateSortedStations();

        // then
        assertThat(stations).isEqualTo(new Stations(List.of(station1, station2, station3, station4)));
    }

    @Test
    @DisplayName("Section을 추가할 때 상행, 하행역을 하나도 포함하지않으면 예외 발생")
    void addSectionExceptionByNotFoundStation() {
        // given
        Station station1 = new Station(1L, "오리");
        Station station2 = new Station(2L, "배카라");
        Station station3 = new Station(3L, "오카라");
        Station station4 = new Station(4L, "배리");
        Sections sections = new Sections(List.of(new Section(1L, 1L, station1, station2, 2)));

        // when & then
        assertThatThrownBy(() -> sections.addSection(new Section(1L, station3, station4, 2)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("구간 추가는 기존의 상행역 하행역 중 하나를 포함해야합니다.");
    }

    @Test
    @DisplayName("이미 상행에서 하행으로 갈 수 있는 Section이면 예외 발생")
    void addSectionExceptionByExistSection() {
        // given
        Station station1 = new Station(1L, "오리");
        Station station2 = new Station(2L, "배카라");
        Station station3 = new Station(3L, "오카라");
        Sections sections = new Sections(List.of(new Section(1L, 1L, station1, station2, 2),
                new Section(2L, 1L, station2, station3, 3)));

        // when & then
        assertThatThrownBy(() -> sections.addSection(new Section(1L, station1, station3, 3)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 상행에서 하행으로 갈 수 있는 구간이 존재합니다.");
    }

    @Test
    @DisplayName("입력된 Section의 하행역이 최상행역과 일치할 경우 단순히 추가만 한다.")
    void addSectionByTopSection() {
        // given
        Station station1 = new Station(1L, "오리");
        Station station2 = new Station(2L, "배카라");
        Station station3 = new Station(3L, "오카라");
        Section section = new Section(1L, 1L, station2, station3, 3);
        Section addSection = new Section(2L, 1L, station1, station2, 4);

        Sections sections = new Sections(List.of(section));
        Sections expectedSections = new Sections(List.of(section, addSection));

        // when
        sections.addSection(addSection);

        // then
        assertThat(sections).isEqualTo(expectedSections);
    }

    @Test
    @DisplayName("입력된 Section의 상행역이 최하행역과 일치할 경우 단순히 추가만 한다.")
    void addSectionByBottomSection() {
        // given
        Station station1 = new Station(1L, "오리");
        Station station2 = new Station(2L, "배카라");
        Station station3 = new Station(3L, "오카라");
        Section section = new Section(1L, 1L, station1, station2, 3);
        Section addSection = new Section(2L, 1L, station2, station3, 4);

        Sections sections = new Sections(List.of(section));
        Sections expectedSections = new Sections(List.of(section, addSection));

        // when
        sections.addSection(addSection);

        // then
        assertThat(sections).isEqualTo(expectedSections);
    }
}

package wooteco.subway.web.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.subway.service.SectionFacade;
import wooteco.subway.web.dto.SectionRequest;

@RestController
@RequestMapping(value = "/lines/{lineId}/sections", produces = MediaType.APPLICATION_JSON_VALUE)
public class SectionController {

    private final SectionFacade sectionFacade;

    public SectionController(SectionFacade sectionFacade) {
        this.sectionFacade = sectionFacade;
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable Long lineId,
            @RequestBody @Valid SectionRequest sectionRequest) {
        sectionFacade.add(lineId, sectionRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long lineId,
            @RequestParam(value = "stationId") Long stationId) {
        sectionFacade.delete(lineId, stationId);
        return ResponseEntity.noContent().build();
    }
}

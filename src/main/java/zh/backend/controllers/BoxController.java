package zh.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zh.backend.entities.BatchEntity;
import zh.backend.entities.BoxEntity;
import zh.backend.services.BoxService;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;

import java.util.List;

@RestController
@RequestMapping("/box")
@RequiredArgsConstructor
@Tag(name = "Box")
public class BoxController extends BaseController {

    private final BoxService boxService;

    @GetMapping
    public ResponseEntity<Page<BoxEntity>> getAllBoxes(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                                       @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
        int totalCount = boxService.getTotalCount();
        Page<BoxEntity> batchList = boxService.getPage(pageRequest);
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(totalCount)).body(batchList);
    }
}

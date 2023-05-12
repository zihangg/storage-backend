package zh.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zh.backend.entities.BatchEntity;
import zh.backend.services.BatchService;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;

import java.util.List;
import java.util.Optional;

// only read methods for controller, as modification of batches are only done during asset interaction.
@RestController
@Validated
@RequestMapping("batches")
@Tag(name = "Batches")
public class BatchController extends BaseController {

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public ResponseEntity<Page<BatchEntity>> getBatches(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                                        @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
        int totalCount = batchService.getTotalCount();
        Page<BatchEntity> batchList = batchService.getPage(pageRequest);
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(totalCount)).body(batchList);
    }

    // Batches can be searched either by batchId, or by assetCode.
    @GetMapping("/{batchNumber}")
    public ResponseEntity<BatchEntity> getBatchByBatchNumber(@PathVariable String batchNumber) {
        BatchEntity batch = batchService.getBatchByBatchNumber(batchNumber);
        return ResponseEntity.ok().body(batch);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BatchEntity>> getBatchByAssetCode(@RequestParam String assetCode) {
        Optional<List<BatchEntity>> optionalBatch = batchService.getBatchByAssetCode(assetCode);
        if (optionalBatch.isPresent()) {
            List<BatchEntity> batch = optionalBatch.get();
            return ResponseEntity.ok().body(batch);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

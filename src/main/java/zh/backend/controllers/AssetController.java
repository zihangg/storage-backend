package zh.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zh.backend.dtos.AssetReceiveDto;
import zh.backend.dtos.CreateAssetDto;
import zh.backend.entities.AssetEntity;
import zh.backend.responses.AssetCreatedResponse;
import zh.backend.responses.AssetReceivedResponse;
import zh.backend.services.AssetService;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;

import java.util.List;

@RestController
@Validated
@RequestMapping("assets")
@Tag(name = "Assets")
public class AssetController extends BaseController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<Page<AssetEntity>> getAssets(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                                       @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
        int totalCount = assetService.getTotalCount();
        Page<AssetEntity> assetList = assetService.getPage(pageRequest);
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(totalCount)).body(assetList);
    }

    @PostMapping
    public ResponseEntity<AssetCreatedResponse> createAsset(@Valid @RequestBody CreateAssetDto createAssetDto) {
        AssetCreatedResponse assetCreatedResponse = assetService.createAsset(createAssetDto);
        return ResponseEntity.ok().body(assetCreatedResponse);
    }

    @PostMapping("receive")
    public ResponseEntity<AssetReceivedResponse> receiveAssets(@Valid @RequestBody AssetReceiveDto assetReceiveDto) {
        AssetReceivedResponse assetReceivedResponse = assetService.receiveAssets(assetReceiveDto);
        return ResponseEntity.ok().body(assetReceivedResponse);
    }
}

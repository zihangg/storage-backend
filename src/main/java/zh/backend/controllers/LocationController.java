package zh.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zh.backend.dtos.LocationCreateDto;
import zh.backend.entities.LocationEntity;
import zh.backend.responses.LocationCreatedResponse;
import zh.backend.services.LocationService;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;

@RestController
@Validated
@RequestMapping("locations")
@Tag(name = "Locations")
public class LocationController extends BaseController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<Page<LocationEntity>> getLocations(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                                             @RequestParam(name = "size", defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
        int totalCount = locationService.getTotalCount();
        Page<LocationEntity> assetList = locationService.getPage(pageRequest);
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(totalCount)).body(assetList);
    }

    @PostMapping
    public ResponseEntity<LocationCreatedResponse> createLocation(@Valid @RequestBody LocationCreateDto createLocationDto) {
        LocationCreatedResponse response = locationService.createLocation(createLocationDto);
        return ResponseEntity.ok().body(response);
    }
}

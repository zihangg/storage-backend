package zh.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zh.backend.dtos.LocationBoxesDto;
import zh.backend.dtos.LocationCreateDto;
import zh.backend.entities.BoxEntity;
import zh.backend.entities.LocationEntity;
import zh.backend.exceptions.LocationDoesNotExistException;
import zh.backend.exceptions.LocationInsufficientVolumeException;
import zh.backend.repositories.BoxesRepository;
import zh.backend.repositories.LocationRepository;
import zh.backend.responses.LocationCreatedResponse;
import zh.backend.responses.LocationDetailsResponse;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;
import zh.backend.utils.paging.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService implements Pageable<LocationEntity> {

    private final LocationRepository locationRepository;

    private final BoxesRepository boxesRepository;

    public LocationCreatedResponse createLocation(LocationCreateDto createLocationDto) {
        BigDecimal volume = createLocationDto.getHeight().multiply(createLocationDto.getLength()).multiply(createLocationDto.getWidth());
        LocationEntity location = new LocationEntity(
                createLocationDto.getCode(),
                createLocationDto.getHeight(),
                createLocationDto.getLength(),
                createLocationDto.getWidth(),
                volume,
                volume // availableVolume & totalVolume is same at creation time.
        );

        locationRepository.saveAndFlush(location);


        LocalDateTime now = LocalDateTime.now();

        return new LocationCreatedResponse(location.getId(), location.getCode(), location.getTotalVolume(), now);
    }

    public LocationEntity getLocationByCode(String locationCode) {
        return locationRepository.findByCode(locationCode).orElseThrow(LocationDoesNotExistException::new);
    }

    public void useVolume(String locationId, BigDecimal volume) {
        LocationEntity location = locationRepository.findById(locationId).orElseThrow(LocationDoesNotExistException::new);

        if (location.getAvailableVolume().compareTo(volume) < 0) {
            throw new LocationInsufficientVolumeException();
        }

        location.setAvailableVolume(location.getAvailableVolume().subtract(volume));
        locationRepository.save(location);
    }

    public LocationDetailsResponse getLocationDetails(String locationCode) {
        // 1. get location information
        LocationEntity location = locationRepository.findByCode(locationCode).orElseThrow(LocationDoesNotExistException::new);


        // 2. get assets & their batch within location
        List<BoxEntity> assets = boxesRepository.findByLocationCode(locationCode).orElse(new ArrayList<>());

        List<LocationBoxesDto> boxes = new ArrayList<>();


        // 2.1 filter required information
        for (BoxEntity box : assets) {
            LocationBoxesDto detailBox = new LocationBoxesDto(
                    box.getBoxId(),
                    box.getAssetCode(),
                    box.getBatchNumber(),
                    box.getQuantity(),
                    box.getVolume()
            );
            boxes.add(detailBox);
        }

        // 3. return
        return new LocationDetailsResponse(
                location.getId(),
                location.getCode(),
                location.getTotalVolume(),
                location.getAvailableVolume(),
                boxes
        );
    }

    @Override
    public Page<LocationEntity> getPage(PageRequest pageRequest) {
        List<LocationEntity> items = locationRepository.findAll();
        int totalCount = items.size();
        int offset = (pageRequest.getPageNumber() - 1) * pageRequest.getPageSize();
        List<LocationEntity> content = items.subList(offset, Math.min(offset + pageRequest.getPageSize(), totalCount));
        int totalPages = (int) Math.ceil((double) totalCount / pageRequest.getPageSize());

        return new Page<>(content, pageRequest.getPageNumber(), totalPages);
    }

    @Override
    public int getTotalCount() {
        return locationRepository.findAll().size();
    }
}

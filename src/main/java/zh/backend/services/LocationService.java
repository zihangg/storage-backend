package zh.backend.services;

import org.springframework.stereotype.Service;
import zh.backend.dtos.LocationCreateDto;
import zh.backend.entities.LocationEntity;
import zh.backend.exceptions.LocationInsufficientVolumeException;
import zh.backend.repositories.LocationRepository;
import zh.backend.responses.LocationCreatedResponse;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;
import zh.backend.utils.paging.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LocationService implements Pageable<LocationEntity> {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

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
        return locationRepository.findByCode(locationCode);
    }

    public void useVolume(String locationId, BigDecimal volume) {
        LocationEntity location = locationRepository.findById(locationId).orElseThrow(NoSuchElementException::new);

        if (location.getAvailableVolume().compareTo(volume) < 0) {
            throw new LocationInsufficientVolumeException();
        }

        location.setAvailableVolume(location.getAvailableVolume().subtract(volume));
        locationRepository.save(location);
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

package zh.backend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zh.backend.dtos.BoxTransferDto;
import zh.backend.entities.BatchEntity;
import zh.backend.entities.BoxEntity;
import zh.backend.entities.LocationEntity;
import zh.backend.exceptions.BoxDoesNotExistException;
import zh.backend.exceptions.IncorrectFromLocationException;
import zh.backend.repositories.BoxesRepository;
import zh.backend.responses.GeneralActionResponse;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;
import zh.backend.utils.paging.Pageable;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BoxService implements Pageable<BoxEntity> {
    private final BoxesRepository boxesRepository;
    private final LocationService locationService;

    public List<BoxEntity> getBoxesByAssetCode(String assetCode) {
        return boxesRepository.findByAssetCode(assetCode).orElseThrow();
    }

    public GeneralActionResponse transferBox(BoxTransferDto boxTransferDto) {
        //1. validations

        //1.1 check if box exists, and if it is in fromLocation.
        BoxEntity box = boxesRepository.findByBoxId(boxTransferDto.getBoxId()).orElseThrow(BoxDoesNotExistException::new);
        System.out.println(box.getLocationCode());
        System.out.println(boxTransferDto.getFromLocationCode());

        if (!Objects.equals(box.getLocationCode(), boxTransferDto.getFromLocationCode())) {
            throw new IncorrectFromLocationException();
        }

        //1.2 location exist check is done in location service itself.
        LocationEntity toLocation = locationService.getLocationByCode(boxTransferDto.getToLocationCode());
        LocationEntity fromLocation = locationService.getLocationByCode(boxTransferDto.getFromLocationCode());

        //2. perform move
        locationService.freeLocation(fromLocation.getId(), box.getVolume());
        locationService.useVolume(toLocation.getId(), box.getVolume());
        box.setLocationCode(toLocation.getCode());

        boxesRepository.save(box);

        return new GeneralActionResponse(true);
    }

    @Override
    public Page<BoxEntity> getPage(PageRequest pageRequest) {
        List<BoxEntity> items = boxesRepository.findAll();
        int totalCount = items.size();
        int offset = (pageRequest.getPageNumber() - 1) * pageRequest.getPageSize();
        List<BoxEntity> content = items.subList(offset, Math.min(offset + pageRequest.getPageSize(), totalCount));
        int totalPages = (int) Math.ceil((double) totalCount / pageRequest.getPageSize());

        return new Page<>(content, pageRequest.getPageNumber(), totalPages);
    }

    @Override
    public int getTotalCount() {
        return boxesRepository.findAll().size();
    }

}

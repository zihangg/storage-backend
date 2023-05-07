package zh.backend.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zh.backend.entities.BatchEntity;
import zh.backend.entities.BoxEntity;
import zh.backend.repositories.BoxesRepository;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;
import zh.backend.utils.paging.Pageable;

import java.util.List;

@Service
@AllArgsConstructor
public class BoxService implements Pageable<BoxEntity> {
    private final BoxesRepository boxesRepository;

    public List<BoxEntity> getBoxesByAssetCode(String assetCode) {
        return boxesRepository.findByAssetCode(assetCode).orElseThrow();
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

package zh.backend.services;

import org.springframework.stereotype.Service;
import zh.backend.dtos.AssetBoxDto;
import zh.backend.entities.BatchEntity;
import zh.backend.entities.BoxEntity;
import zh.backend.exceptions.BatchDoesNotExistException;
import zh.backend.repositories.BatchRepository;
import zh.backend.repositories.BoxesRepository;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;
import zh.backend.utils.paging.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatchService implements Pageable<BatchEntity> {

    private final BatchRepository batchRepository;
    private final BoxesRepository boxesRepository;

    public BatchService(BatchRepository batchRepository, BoxesRepository boxesRepository) {
        this.batchRepository = batchRepository;
        this.boxesRepository = boxesRepository;
    }

    public BatchEntity createBatch(String assetId, BigDecimal quantity, LocalDate expiry, List<AssetBoxDto> boxes, String locationCode) {
        String batchNumber = Long.toString(System.currentTimeMillis() / 1000L);
        List<String> boxIds = new ArrayList<>();

        // record boxes.
        for (AssetBoxDto box : boxes) {
            BoxEntity incomingBox = new BoxEntity(
                    batchNumber,
                    assetId,
                    box.getQuantity(),
                    box.getLength(),
                    box.getWidth(),
                    box.getHeight(),
                    box.getBoxNumber(),
                    locationCode
            );
            boxesRepository.save(incomingBox);
            boxIds.add(incomingBox.getBoxId());
        }

        // initial & current quantity is the same on creation.
        BatchEntity batch = new BatchEntity(batchNumber, assetId, quantity, quantity, expiry, boxIds);

        batchRepository.save(batch);

        return batch;
    }

    public BatchEntity getBatchByBatchNumber(String batchNumber) {
        return batchRepository.findByBatchNumber(batchNumber).orElseThrow(BatchDoesNotExistException::new);
    }

    public Optional<List<BatchEntity>> getBatchByAssetCode(String assetCode) {
        return batchRepository.findByAssetCode(assetCode);
    }

    @Override
    public Page<BatchEntity> getPage(PageRequest pageRequest) {
        List<BatchEntity> items = batchRepository.findAll();
        int totalCount = items.size();
        int offset = (pageRequest.getPageNumber() - 1) * pageRequest.getPageSize();
        List<BatchEntity> content = items.subList(offset, Math.min(offset + pageRequest.getPageSize(), totalCount));
        int totalPages = (int) Math.ceil((double) totalCount / pageRequest.getPageSize());

        return new Page<>(content, pageRequest.getPageNumber(), totalPages);
    }

    @Override
    public int getTotalCount() {
        return batchRepository.findAll().size();
    }

}

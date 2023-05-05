package zh.backend.services;

import org.springframework.stereotype.Service;
import zh.backend.entities.BatchEntity;
import zh.backend.repositories.BatchRepository;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;
import zh.backend.utils.paging.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BatchService implements Pageable<BatchEntity> {

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public BatchEntity createBatch(String assetId, BigDecimal quantity, LocalDate expiry) {
        long currentTimeUnix = System.currentTimeMillis() / 1000L;

        // initial & current quantity is the same on creation.
        BatchEntity batch = new BatchEntity(Long.toString(currentTimeUnix), assetId, quantity, quantity, expiry);

        batchRepository.save(batch);

        return batch;
    }

    public Optional<BatchEntity> getBatchByBatchNumber(String batchNumber) {
        return batchRepository.findByBatchNumber(batchNumber);
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

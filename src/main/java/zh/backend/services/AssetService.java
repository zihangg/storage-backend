package zh.backend.services;

import org.springframework.stereotype.Service;
import zh.backend.dtos.AssetReceiveDto;
import zh.backend.dtos.AssetCreateDto;
import zh.backend.entities.AssetEntity;
import zh.backend.entities.AssetReceivedRecordEntity;
import zh.backend.entities.BatchEntity;
import zh.backend.entities.LocationEntity;
import zh.backend.repositories.AssetReceivedRecordRepository;
import zh.backend.repositories.AssetRepository;
import zh.backend.responses.AssetCreatedResponse;
import zh.backend.responses.AssetReceivedResponse;
import zh.backend.utils.paging.Page;
import zh.backend.utils.paging.PageRequest;
import zh.backend.utils.paging.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AssetService implements Pageable<AssetEntity> {

    private final AssetRepository assetRepository;

    private final AssetReceivedRecordRepository assetReceivedRecordRepository;

    private final LocationService locationService;

    private final BatchService batchService;

    public AssetService(AssetRepository assetRepository,
                        AssetReceivedRecordRepository assetReceivedRecordRepository,
                        LocationService locationService,
                        BatchService batchService) {
        this.assetRepository = assetRepository;
        this.assetReceivedRecordRepository = assetReceivedRecordRepository;
        this.locationService = locationService;
        this.batchService = batchService;
    }

    public AssetCreatedResponse createAsset(AssetCreateDto createAssetDto) {
        AssetEntity assetEntity = new AssetEntity(
                createAssetDto.getAssetCode(),
                createAssetDto.getName(),
                createAssetDto.getType(),
                BigDecimal.valueOf(0),
                createAssetDto.getCost());

        assetRepository.saveAndFlush(assetEntity);

        LocalDateTime now = LocalDateTime.now();

        return new AssetCreatedResponse(assetEntity.getId(), assetEntity.getAssetCode(), assetEntity.getName(), now);
    }

    public AssetReceivedResponse receiveAssets(AssetReceiveDto assetReceiveDto) {
        AssetEntity asset = assetRepository.findByAssetCode(assetReceiveDto.getAssetCode()).orElseThrow(NoSuchElementException::new);

        //1. get all information needed.
        BigDecimal totalCost = asset.getCost().multiply(assetReceiveDto.getQuantity());
        LocationEntity location = locationService.getLocationByCode(assetReceiveDto.getLocationCode());
        LocalDate expiry = LocalDate.parse(assetReceiveDto.getExpiry());
        BigDecimal volume = assetReceiveDto.getHeight().multiply(assetReceiveDto.getLength()).multiply(assetReceiveDto.getWidth());

        //1.1 create batch
        BatchEntity batch = batchService.createBatch(assetReceiveDto.getAssetCode(), assetReceiveDto.getQuantity(), expiry);

        //1.2 current time;
        LocalDateTime now = LocalDateTime.now();

        //1.3. update location space availability & reject if no volume available
        locationService.useVolume(location.getId(), volume);

        //2. record the receiving of asset.
        AssetReceivedRecordEntity record = new AssetReceivedRecordEntity(
                asset.getAssetCode(),
                asset.getName(),
                assetReceiveDto.getQuantity(),
                asset.getCost(),
                totalCost,
                location.getId(),
                location.getCode(),
                batch.getId(),
                batch.getBatchNumber(),
                now,
                volume
        );
        recordReceiveAssets(record);

        //3. update balance of asset
        asset.setBalance(asset.getBalance().add(assetReceiveDto.getQuantity()));




        assetRepository.save(asset);

        //4. return response to client
        return new AssetReceivedResponse(
                record.getId(),
                record.getAssetCode(),
                record.getAssetName(),
                record.getQuantity(),
                record.getTotalCost(),
                record.getLocationId(),
                record.getLocationCode(),
                record.getBatchId(),
                record.getBatchNumber(),
                now
        );
    }

    public void recordReceiveAssets(AssetReceivedRecordEntity assetReceivedRecordEntity) {
        assetReceivedRecordRepository.save(assetReceivedRecordEntity);

    }

    @Override
    public Page<AssetEntity> getPage(PageRequest pageRequest) {
        List<AssetEntity> items = assetRepository.findAll();
        int totalCount = items.size();
        int offset = (pageRequest.getPageNumber() - 1) * pageRequest.getPageSize();
        List<AssetEntity> content = items.subList(offset, Math.min(offset + pageRequest.getPageSize(), totalCount));
        int totalPages = (int) Math.ceil((double) totalCount / pageRequest.getPageSize());

        return new Page<>(content, pageRequest.getPageNumber(), totalPages);
    }

    @Override
    public int getTotalCount() {
        return assetRepository.findAll().size();
    }
}

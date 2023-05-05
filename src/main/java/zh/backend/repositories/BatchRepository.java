package zh.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zh.backend.entities.BatchEntity;

import java.util.List;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<BatchEntity, String> {

    Optional<BatchEntity> findByBatchNumber(String batchNumber);

    Optional<List<BatchEntity>> findByAssetCode(String assetCode);
}

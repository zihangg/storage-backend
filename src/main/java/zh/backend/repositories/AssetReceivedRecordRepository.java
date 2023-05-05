package zh.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zh.backend.entities.AssetReceivedRecordEntity;

public interface AssetReceivedRecordRepository extends JpaRepository<AssetReceivedRecordEntity, String> {
}

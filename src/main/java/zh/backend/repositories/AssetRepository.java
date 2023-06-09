package zh.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zh.backend.entities.AssetEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, String> {

    Optional<AssetEntity> findByAssetCode(String assetCode);

}

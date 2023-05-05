package zh.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zh.backend.entities.AssetEntity;

import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, String> {

    public Optional<AssetEntity> findByAssetCode(String assetCode);

}

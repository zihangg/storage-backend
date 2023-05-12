package zh.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zh.backend.entities.BoxEntity;

import java.util.List;
import java.util.Optional;

public interface BoxesRepository extends JpaRepository<BoxEntity, String> {

    Optional<List<BoxEntity>> findByAssetCode(String assetCode);

    Optional<List<BoxEntity>> findByLocationCode(String locationCode);

    Optional<BoxEntity> findByBoxId(String boxId);
}

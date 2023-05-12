package zh.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zh.backend.entities.LocationEntity;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {
     Optional<LocationEntity> findByCode(String locationCode);
}

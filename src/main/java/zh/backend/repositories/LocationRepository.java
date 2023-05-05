package zh.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zh.backend.entities.LocationEntity;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {
    public LocationEntity findByCode(String locationCode);
}

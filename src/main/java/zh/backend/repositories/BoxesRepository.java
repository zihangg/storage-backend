package zh.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zh.backend.entities.BoxEntity;

public interface BoxesRepository extends JpaRepository<BoxEntity, String> {
}

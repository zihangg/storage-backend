package zh.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zh.backend.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
}

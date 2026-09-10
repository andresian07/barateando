package barateando.persistence.repository;

import barateando.persistence.entity.ViajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViajeRepository extends JpaRepository<ViajeEntity, Long> {
}
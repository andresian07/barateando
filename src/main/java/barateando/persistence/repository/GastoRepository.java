package barateando.persistence.repository;

import barateando.persistence.entity.GastoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepository extends JpaRepository<GastoEntity, Long> {
}
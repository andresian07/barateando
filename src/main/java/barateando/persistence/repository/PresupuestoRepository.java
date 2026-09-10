package barateando.persistence.repository;

import barateando.persistence.entity.PresupuestoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresupuestoRepository extends JpaRepository<PresupuestoEntity, Long> {
}
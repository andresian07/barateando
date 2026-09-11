package barateando.persistence.repository;

import barateando.persistence.entity.PresupuestoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PresupuestoRepository extends JpaRepository<PresupuestoEntity, Long> {
    Optional<PresupuestoEntity> findByViaje_Id(Long viajeId);
}
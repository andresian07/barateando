package barateando.persistence.repository;

import barateando.persistence.entity.GastoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GastoRepository extends JpaRepository<GastoEntity, Long> {
    List<GastoEntity> findByViaje_Id(Long viajeId);
}
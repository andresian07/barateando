package barateando.persistence.repository;

import barateando.persistence.entity.PresupuestoEntity;
import barateando.persistence.entity.ViajeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PresupuestoRepositoryTest {
    @Autowired
    private ViajeRepository viajeRepository;
    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @Test
    void findByViajeIdDevuelvePresupuestoDeEseViaje(){
        ViajeEntity viajeExistente = new ViajeEntity();
        viajeExistente.setNombre("Noruega 2026");
        viajeExistente.setDestino("Oslo");
        viajeExistente.setFechaInicio(LocalDate.now());
        viajeExistente.setFechaFin(LocalDate.now().plusDays(10));
        this.viajeRepository.save(viajeExistente);

        PresupuestoEntity presupuesto = new PresupuestoEntity();
        presupuesto.setViaje(viajeExistente);
        presupuesto.setMontoPresupuesto(new BigDecimal(1000));
        this.presupuestoRepository.save(presupuesto);

        Optional<PresupuestoEntity> resultado = this.presupuestoRepository.findByViaje_Id(viajeExistente.getId());
        assertTrue(resultado.isPresent());
    }

}

package barateando.persistence.repository;

import barateando.persistence.entity.GastoEntity;
import barateando.persistence.entity.ViajeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class GastoRepositoryTest {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private ViajeRepository viajeRepository;

    @Test
    void findByViajeIdDevuelveLosGastosDeEseViaje() {
        ViajeEntity viajeExistente = new ViajeEntity();
        viajeExistente.setNombre("Noruega 2026");
        viajeExistente.setDestino("Oslo");
        viajeExistente.setFechaInicio(LocalDate.now());
        viajeExistente.setFechaFin(LocalDate.now().plusDays(10));
        this.viajeRepository.save(viajeExistente);

        GastoEntity gasto = new GastoEntity();
        gasto.setMonto(new BigDecimal(2000));
        gasto.setDescripcion("comida y trasnporte");
        gasto.setFechaGasto(LocalDate.now().plusDays(20));
        gasto.setViaje(viajeExistente);
        this.gastoRepository.save(gasto);

        List<GastoEntity> resultado = this.gastoRepository.findByViaje_Id(viajeExistente.getId());

        assertEquals(1, resultado.size());

    }
}

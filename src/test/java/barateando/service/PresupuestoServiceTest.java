package barateando.service;

import barateando.persistence.entity.PresupuestoEntity;
import barateando.persistence.entity.ViajeEntity;
import barateando.persistence.repository.PresupuestoRepository;
import barateando.persistence.repository.UsuarioRepository;
import barateando.persistence.repository.ViajeRepository;
import barateando.web.dto.PresupuestoDto;
import barateando.web.dto.PresupuestoRequest;
import barateando.web.dto.PresupuestoUpdate;
import barateando.web.exception.RecursoNoEncontradoException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PresupuestoServiceTest {

    @Test
    void getDevuelveElPresupuestoCuandoExiste() {
        ViajeEntity viajeFalso = new ViajeEntity();
        viajeFalso.setId(1L);

        PresupuestoEntity presupuestoFalso = new PresupuestoEntity();
        presupuestoFalso.setId(1L);
        presupuestoFalso.setMontoPresupuesto(new BigDecimal("1000"));
        presupuestoFalso.setViaje(viajeFalso);

        PresupuestoRepository presupuestoRepoFalso = Mockito.mock(PresupuestoRepository.class);
        Mockito.when(presupuestoRepoFalso.findById(1L)).thenReturn(Optional.of(presupuestoFalso));

        PresupuestoService service = new PresupuestoService(presupuestoRepoFalso, null);

        PresupuestoDto resultado = service.get(1L);
        assertEquals(new BigDecimal("1000"), resultado.montoPresupuesto());
    }

    @Test
    void getTiraExcepcionCuandoNoExiste() {
        PresupuestoRepository presupuestoRepoFalso = Mockito.mock(PresupuestoRepository.class);
        PresupuestoService service = new PresupuestoService(presupuestoRepoFalso, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.get(1L));
    }

    @Test
    void updateNotChangeWhenMontoIsNull() {
        PresupuestoUpdate update = new PresupuestoUpdate(null);

        ViajeEntity viajeFalso = new ViajeEntity();
        viajeFalso.setId(1L);

        PresupuestoEntity presupuestoExistente = new PresupuestoEntity();
        presupuestoExistente.setId(1L);
        presupuestoExistente.setMontoPresupuesto(new BigDecimal("1000"));
        presupuestoExistente.setViaje(viajeFalso);

        PresupuestoRepository presupuestoRepoFalso = Mockito.mock(PresupuestoRepository.class);
        Mockito.when(presupuestoRepoFalso.findById(1L)).thenReturn(Optional.of(presupuestoExistente));

        PresupuestoService service = new PresupuestoService(presupuestoRepoFalso, null);

        PresupuestoDto resultado = service.update(1L, update);
        assertEquals(new BigDecimal("1000"), resultado.montoPresupuesto());
    }

    @Test
    void updateTiraExcepcionCuandoNoExiste() {
        PresupuestoUpdate update = new PresupuestoUpdate(new BigDecimal("500"));
        PresupuestoRepository presupuestoRepoFalso = Mockito.mock(PresupuestoRepository.class);
        PresupuestoService service = new PresupuestoService(presupuestoRepoFalso, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.update(1L, update));
    }

    @Test
    void deletePresupuesto() {
        PresupuestoRepository presupuestoRepoFalso = Mockito.mock(PresupuestoRepository.class);
        Mockito.when(presupuestoRepoFalso.existsById(1L)).thenReturn(true);

        PresupuestoService service = new PresupuestoService(presupuestoRepoFalso, null);
        service.delete(1L);

        Mockito.verify(presupuestoRepoFalso).deleteById(1L);
    }

    @Test
    void deleteTiraExcepcionCuandoNoExiste() {
        PresupuestoRepository presupuestoRepoFalso = Mockito.mock(PresupuestoRepository.class);
        PresupuestoService service = new PresupuestoService(presupuestoRepoFalso, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.delete(1L));
    }

    // TODO: crearPresupuesto()
    @Test
    void crearPresupuesto(){
        ViajeEntity viajeFalso = new ViajeEntity();
        viajeFalso.setId(1L);
        PresupuestoRequest newPresupuesto = new PresupuestoRequest(new BigDecimal(1000),1L);
        PresupuestoRepository repoFalso = Mockito.mock(PresupuestoRepository.class);
        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        Mockito.when(viajeRepoFalso.findById(1L)).thenReturn(Optional.of(viajeFalso));
        PresupuestoService service = new PresupuestoService(repoFalso,viajeRepoFalso);
        PresupuestoDto resultado = service.crear(newPresupuesto);
        assertEquals(new BigDecimal(1000), resultado.montoPresupuesto());

    }

    @Test
    void crearTiraExcepcionCuandoViajeNoExiste(){
        PresupuestoRequest newPresupuesto = new PresupuestoRequest(new BigDecimal(1000),1L);
        ViajeRepository viajeFalso = Mockito.mock(ViajeRepository.class);
        PresupuestoRepository presupuestoFalso = Mockito.mock(PresupuestoRepository.class);
        PresupuestoService service = new PresupuestoService(presupuestoFalso, viajeFalso);
        assertThrows(RecursoNoEncontradoException.class, () -> service.crear(newPresupuesto));

    }

}

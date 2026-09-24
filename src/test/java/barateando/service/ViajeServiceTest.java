package barateando.service;

import barateando.persistence.entity.GastoEntity;
import barateando.persistence.entity.PresupuestoEntity;
import barateando.persistence.entity.UsuarioEntity;
import barateando.persistence.entity.ViajeEntity;
import barateando.persistence.repository.GastoRepository;
import barateando.persistence.repository.PresupuestoRepository;
import barateando.persistence.repository.UsuarioRepository;
import barateando.persistence.repository.ViajeRepository;
import barateando.web.dto.ResumenViaje;
import barateando.web.dto.ViajeDto;
import barateando.web.dto.ViajeRequest;
import barateando.web.dto.ViajeUpdate;
import barateando.web.exception.RecursoNoEncontradoException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ViajeServiceTest {

    @Test
    void crearViaje() {
        ViajeRequest request = new ViajeRequest(
                "Noruega 2026",
                "Oslo",
                LocalDate.now(),
                LocalDate.now().plusDays(10)
        );

        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        ViajeService service = new ViajeService(viajeRepoFalso, null, null, null);

        ViajeDto resultado = service.crear(request);

        assertEquals("Noruega 2026", resultado.nombre());
        assertEquals("Oslo", resultado.destino());
    }

    @Test
    void getDevuelveElViajeCuandoExiste() {
        ViajeEntity viajeFalso = new ViajeEntity();
        viajeFalso.setId(1L);
        viajeFalso.setNombre("Noruega 2026");
        viajeFalso.setDestino("Oslo");

        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        Mockito.when(viajeRepoFalso.findById(1L)).thenReturn(Optional.of(viajeFalso));

        ViajeService service = new ViajeService(viajeRepoFalso, null, null, null);

        ViajeDto resultado = service.get(1L);
        assertEquals("Noruega 2026", resultado.nombre());
    }

    @Test
    void getTiraExcepcionCuandoNoExiste() {
        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        ViajeService service = new ViajeService(viajeRepoFalso, null, null, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.get(1L));
    }

    @Test
    void updateNotChangeWhenDestinoIsNull() {
        ViajeUpdate viajeUpdate = new ViajeUpdate("Noruega actualizado", null, null, null);

        ViajeEntity viajeExistente = new ViajeEntity();
        viajeExistente.setId(1L);
        viajeExistente.setNombre("Noruega 2026");
        viajeExistente.setDestino("Oslo");

        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        Mockito.when(viajeRepoFalso.findById(1L)).thenReturn(Optional.of(viajeExistente));

        ViajeService service = new ViajeService(viajeRepoFalso, null, null, null);

        ViajeDto resultado = service.update(1L, viajeUpdate);
        assertEquals("Noruega actualizado", resultado.nombre());
        assertEquals("Oslo", resultado.destino());
    }

    @Test
    void updateTiraExcepcionCuandoNoExiste() {
        ViajeUpdate viajeUpdate = new ViajeUpdate("Noruega actualizado", null, null, null);
        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        ViajeService service = new ViajeService(viajeRepoFalso, null, null, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.update(1L, viajeUpdate));
    }

    @Test
    void deleteViaje() {
        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        Mockito.when(viajeRepoFalso.existsById(1L)).thenReturn(true);

        ViajeService service = new ViajeService(viajeRepoFalso, null, null, null);
        service.delete(1L);

        Mockito.verify(viajeRepoFalso).deleteById(1L);
    }

    @Test
    void deleteTiraExcepcionCuandoNoExiste() {
        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        ViajeService service = new ViajeService(viajeRepoFalso, null, null, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.delete(1L));
    }

    @Test
    void getResumenCalculaLaDiferenciaCorrectamente() {
        PresupuestoEntity presupuestoFalso = new PresupuestoEntity();
        presupuestoFalso.setMontoPresupuesto(new BigDecimal("1000"));

        GastoEntity gasto1 = new GastoEntity();
        gasto1.setMonto(new BigDecimal("200"));
        GastoEntity gasto2 = new GastoEntity();
        gasto2.setMonto(new BigDecimal("150"));

        PresupuestoRepository presupuestoRepoFalso = Mockito.mock(PresupuestoRepository.class);
        Mockito.when(presupuestoRepoFalso.findByViaje_Id(1L)).thenReturn(Optional.of(presupuestoFalso));

        GastoRepository gastoRepoFalso = Mockito.mock(GastoRepository.class);
        Mockito.when(gastoRepoFalso.findByViaje_Id(1L)).thenReturn(List.of(gasto1, gasto2));

        ViajeService service = new ViajeService(null, presupuestoRepoFalso, gastoRepoFalso, null);

        ResumenViaje resumen = service.getResumen(1L);

        assertEquals(new BigDecimal("1000"), resumen.presupuesto());
        assertEquals(new BigDecimal("350"), resumen.totalGastado());
        assertEquals(new BigDecimal("650"), resumen.diferencia());
    }

    @Test
    void getResumenUsaCeroCuandoNoHayPresupuestoNiGastos() {
        PresupuestoRepository presupuestoRepoFalso = Mockito.mock(PresupuestoRepository.class);
        Mockito.when(presupuestoRepoFalso.findByViaje_Id(1L)).thenReturn(Optional.empty());

        GastoRepository gastoRepoFalso = Mockito.mock(GastoRepository.class);
        Mockito.when(gastoRepoFalso.findByViaje_Id(1L)).thenReturn(List.of());

        ViajeService service = new ViajeService(null, presupuestoRepoFalso, gastoRepoFalso, null);

        ResumenViaje resumen = service.getResumen(1L);

        assertEquals(BigDecimal.ZERO, resumen.presupuesto());
        assertEquals(BigDecimal.ZERO, resumen.totalGastado());
        assertEquals(BigDecimal.ZERO, resumen.diferencia());
    }

    @Test
    void agregarParticipanteAgregaElUsuarioAlViaje() {
        ViajeEntity viajeFalso = new ViajeEntity();
        viajeFalso.setId(1L);
        viajeFalso.setNombre("Noruega 2026");

        UsuarioEntity usuarioFalso = new UsuarioEntity();
        usuarioFalso.setId(2L);
        usuarioFalso.setNombre("Ana");
        usuarioFalso.setEmail("ana@gmail.com");

        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        Mockito.when(viajeRepoFalso.findById(1L)).thenReturn(Optional.of(viajeFalso));

        UsuarioRepository usuarioRepoFalso = Mockito.mock(UsuarioRepository.class);
        Mockito.when(usuarioRepoFalso.findById(2L)).thenReturn(Optional.of(usuarioFalso));

        ViajeService service = new ViajeService(viajeRepoFalso, null, null, usuarioRepoFalso);

        ViajeDto resultado = service.agregarParticipante(1L, 2L);

        assertEquals(1, resultado.participantes().size());
        assertEquals("Ana", resultado.participantes().get(0).nombre());
    }

    @Test
    void agregarParticipanteTiraExcepcionCuandoViajeNoExiste() {
        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        ViajeService service = new ViajeService(viajeRepoFalso, null, null, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.agregarParticipante(1L, 2L));
    }

    @Test
    void agregarParticipanteTiraExcepcionCuandoUsuarioNoExiste() {
        ViajeEntity viajeFalso = new ViajeEntity();
        viajeFalso.setId(1L);

        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        Mockito.when(viajeRepoFalso.findById(1L)).thenReturn(Optional.of(viajeFalso));

        UsuarioRepository usuarioRepoFalso = Mockito.mock(UsuarioRepository.class);

        ViajeService service = new ViajeService(viajeRepoFalso, null, null, usuarioRepoFalso);

        assertThrows(RecursoNoEncontradoException.class, () -> service.agregarParticipante(1L, 2L));
    }
}
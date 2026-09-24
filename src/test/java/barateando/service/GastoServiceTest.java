package barateando.service;

import barateando.persistence.entity.GastoEntity;
import barateando.persistence.entity.UsuarioEntity;
import barateando.persistence.entity.ViajeEntity;
import barateando.persistence.repository.GastoRepository;
import barateando.persistence.repository.UsuarioRepository;
import barateando.persistence.repository.ViajeRepository;
import barateando.web.dto.GastoDto;
import barateando.web.dto.GastoRequest;
import barateando.web.dto.GastoUpdate;
import barateando.web.exception.RecursoNoEncontradoException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GastoServiceTest {
    @Test
    void getDevuelveElGastoCuandoExiste() {
        UsuarioEntity usuarioFalso = new UsuarioEntity();
        usuarioFalso.setId(1L);
        usuarioFalso.setNombre("Ana");

        GastoEntity gastoFalso = new GastoEntity();
        gastoFalso.setId(1L);
        gastoFalso.setMonto(new BigDecimal("100"));
        gastoFalso.setDescripcion("Cena");
        gastoFalso.setFechaGasto(LocalDate.now());
        gastoFalso.setUsuario(usuarioFalso);

        GastoRepository repoFalso = Mockito.mock(GastoRepository.class);
        Mockito.when(repoFalso.findById(1L)).thenReturn(Optional.of(gastoFalso));

        GastoService service = new GastoService(repoFalso, null, null);

        GastoDto resultado = service.get(1L);
        assertEquals("Ana", resultado.nombreUsuario());
    }

    @Test
    void getTiraExcepcionCuandoNoExiste() {
        GastoRepository repoFalso = Mockito.mock(GastoRepository.class);
        GastoService service = new GastoService(repoFalso, null, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.get(1L));

    }

    @Test
    void updateNotChangeWhenDescriptionIsNull(){
        GastoUpdate gasto = new GastoUpdate(
            new BigDecimal(200),
            null,
            null

        );


        GastoEntity gastoExistente = new GastoEntity();
        gastoExistente.setId(1L);
        gastoExistente.setMonto(new BigDecimal("100"));
        gastoExistente.setDescripcion("Cena");

        UsuarioEntity usuarioFalso = new UsuarioEntity();
        usuarioFalso.setNombre("Ana");
        gastoExistente.setUsuario(usuarioFalso);



        GastoRepository repoFalso = Mockito.mock(GastoRepository.class);
        Mockito.when(repoFalso.findById(1L)).thenReturn(Optional.of(gastoExistente));

        GastoService service = new GastoService(repoFalso, null, null);

        GastoDto resultado = service.update(1L, gasto);
        assertEquals(new BigDecimal(200), resultado.monto());
        assertEquals("Cena", resultado.descripcion());



    }

    @Test
    void updateNotExist(){
        GastoUpdate gasto = new GastoUpdate(
                new BigDecimal(200),
                null,
                null

        );
        GastoRepository repoFalso = Mockito.mock(GastoRepository.class);
        GastoService service = new GastoService(repoFalso, null, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.update(1L,gasto));
    }

    @Test
    void crearGasto(){

        UsuarioEntity usuarioFalso = new UsuarioEntity();
        usuarioFalso.setId(1L);
        usuarioFalso.setNombre("Ana");

        ViajeEntity viajeFalso = new ViajeEntity();
        viajeFalso.setId(1L);
        viajeFalso.setNombre("noruega");

        GastoRequest newGasto = new GastoRequest(
                new BigDecimal(200),
                new String("viaje a noruega"),
                null,
                usuarioFalso.getId(),
                viajeFalso.getId()

        );

        GastoRepository repoFalso = Mockito.mock(GastoRepository.class);
        UsuarioRepository usuarioRepoFalso = Mockito.mock(UsuarioRepository.class);
        Mockito.when(usuarioRepoFalso.findById(1L)).thenReturn(Optional.of(usuarioFalso));
        ViajeRepository viajeRepoFalso = Mockito.mock(ViajeRepository.class);
        Mockito.when(viajeRepoFalso.findById(1L)).thenReturn(Optional.of(viajeFalso));
        GastoService service = new GastoService(repoFalso, usuarioRepoFalso, viajeRepoFalso);

        GastoDto resultado = service.create(newGasto);
        assertEquals(new BigDecimal(200), resultado.monto());
        assertEquals("Ana", resultado.nombreUsuario());



    }

    @Test
    void deleteGasto(){
        UsuarioEntity usuarioFalso = new UsuarioEntity();
        usuarioFalso.setId(1L);
        usuarioFalso.setNombre("Ana");

        GastoEntity gastoFalso = new GastoEntity();
        gastoFalso.setId(1L);
        gastoFalso.setMonto(new BigDecimal("100"));
        gastoFalso.setDescripcion("Cena");
        gastoFalso.setFechaGasto(LocalDate.now());
        gastoFalso.setUsuario(usuarioFalso);

        GastoRepository repoFalso = Mockito.mock(GastoRepository.class);
        Mockito.when(repoFalso.existsById(1L)).thenReturn(true);

        GastoService service = new GastoService(repoFalso, null, null);
        service.delete(1L);
        Mockito.verify(repoFalso).deleteById(1L);




    }

    @Test
    void deleteTiraExcepcionCuandoNoExiste(){
        GastoRepository repoFalso = Mockito.mock(GastoRepository.class);
        GastoService service = new GastoService(repoFalso, null, null);

        assertThrows(RecursoNoEncontradoException.class, () -> service.delete(1L));

    }

}



package barateando.service;

import barateando.persistence.entity.UsuarioEntity;
import barateando.persistence.repository.UsuarioRepository;
import barateando.web.dto.UsuarioDto;
import barateando.web.dto.UsuarioRequest;
import barateando.web.dto.UsuarioUpdate;
import barateando.web.exception.RecursoNoEncontradoException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsuarioServiceTest {
   @Test
   void buscarPorIdDevuelveElUsuarioCuandoExiste(){
       UsuarioEntity usuarioFalso = new UsuarioEntity();
       usuarioFalso.setId(1L);
       usuarioFalso.setNombre("Andres");
       usuarioFalso.setEmail("andres@gmail.com");

       UsuarioRepository repoFalso = Mockito.mock(UsuarioRepository.class);
       Mockito.when(repoFalso.findById(1L)).thenReturn(Optional.of(usuarioFalso));

       UsuarioService service = new UsuarioService(repoFalso);
       UsuarioDto resultado = service.buscarPorId(1L);
       assertEquals("Andres", resultado.nombre());
   }

   @Test
   void buscarPorIdTiraExcepcionCuandoNoExiste(){
       UsuarioRepository repoFalso = Mockito.mock(UsuarioRepository.class);

       UsuarioService service = new UsuarioService(repoFalso);
       assertThrows(RecursoNoEncontradoException.class, () -> service.buscarPorId(1L));

   }

   @Test
   void crearUsuario(){
       UsuarioRequest newUsuario = new UsuarioRequest("Andres","andres@gmail.com");
       UsuarioRepository repoFalso = Mockito.mock(UsuarioRepository.class);
       UsuarioService service = new UsuarioService(repoFalso);
       UsuarioDto resultado = service.crear(newUsuario);

       assertEquals("Andres", resultado.nombre());
       assertEquals("andres@gmail.com", resultado.email());
   }

   @Test
   void updateUsuario(){
       UsuarioUpdate usuarioUpdate = new UsuarioUpdate("Andres", null);
       UsuarioEntity usuarioExistente = new UsuarioEntity();
       usuarioExistente.setId(1L);
       usuarioExistente.setNombre("Pedro");
       usuarioExistente.setEmail("pedro@gmail.com");

       UsuarioRepository repoFalso = Mockito.mock(UsuarioRepository.class);
       Mockito.when(repoFalso.findById(1L)).thenReturn(Optional.of(usuarioExistente));
       UsuarioService service = new UsuarioService(repoFalso);
       UsuarioDto resultado = service.update(1L,usuarioUpdate);
       assertEquals("Andres",resultado.nombre());
       assertEquals("pedro@gmail.com",resultado.email());

   }

   @Test
   void updateNotExist(){
       UsuarioUpdate usuarioUpdate = new UsuarioUpdate("Andres", null);
       UsuarioRepository repoFalso = Mockito.mock(UsuarioRepository.class);
       UsuarioService service = new UsuarioService(repoFalso);
       assertThrows(RecursoNoEncontradoException.class, () -> service.update(1L, usuarioUpdate) );
   }

   @Test
   void deleteUsuario(){
       UsuarioEntity usuarioFalso = new UsuarioEntity();
       usuarioFalso.setId(1L);
       usuarioFalso.setNombre("Andres");
       usuarioFalso.setEmail("andres@gmail.com");
       UsuarioRepository repoFalso = Mockito.mock(UsuarioRepository.class);
       Mockito.when(repoFalso.existsById(1L)).thenReturn(true);
       UsuarioService service = new UsuarioService(repoFalso);
       service.eliminar(1L);
       Mockito.verify(repoFalso).deleteById(1L);
   }
    @Test
    void deleteTiraExcepcionCuandoNoExiste(){
        UsuarioRepository repoFalso = Mockito.mock(UsuarioRepository.class);
        UsuarioService service = new UsuarioService(repoFalso);

        assertThrows(RecursoNoEncontradoException.class, () -> service.eliminar(1L));

    }


}

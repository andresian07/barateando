package barateando.persistence.repository;

import barateando.persistence.entity.UsuarioEntity;
import barateando.persistence.entity.ViajeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ViajeRepositoryTest {

  @Autowired
  private ViajeRepository viajeRepository;
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Test
  void findByViajeIdDevuelveParticipantes(){
      UsuarioEntity usuario = new UsuarioEntity();
      usuario.setNombre("Andres");
      usuario.setEmail("andres@gmail.com");
      this.usuarioRepository.save(usuario);


      ViajeEntity viajeExistente = new ViajeEntity();
      viajeExistente.setNombre("Noruega 2026");
      viajeExistente.setDestino("Oslo");
      viajeExistente.setFechaInicio(LocalDate.now());
      viajeExistente.setFechaFin(LocalDate.now().plusDays(10));
      viajeExistente.getParticipantes().add(usuario);
      this.viajeRepository.save(viajeExistente);

      Optional<ViajeEntity> resultado = this.viajeRepository.findById(viajeExistente.getId());
      List<UsuarioEntity> participantes = resultado.get().getParticipantes();
      assertEquals(1,participantes.size());
      assertEquals("Andres", participantes.get(0).getNombre());
      assertEquals("andres@gmail.com", participantes.get(0).getEmail());




  }

}

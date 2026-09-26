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

@DataJpaTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ViajeRepository viajeRepository;

    @Test
    void findByUsuarioIdDevuelveViajes(){
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
        usuario.getViajes().add(viajeExistente);
        this.viajeRepository.save(viajeExistente);

        Optional<UsuarioEntity> resultado = this.usuarioRepository.findById(usuario.getId());
        List<ViajeEntity> viajes = resultado.get().getViajes();
        assertEquals("Noruega 2026", viajes.get(0).getNombre());
    }
}

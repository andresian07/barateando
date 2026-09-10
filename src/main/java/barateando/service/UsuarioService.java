package barateando.service;

import barateando.persistence.entity.UsuarioEntity;
import barateando.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioEntity crear(UsuarioEntity usuario) {
        return usuarioRepository.save(usuario);
    }

    public UsuarioEntity buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
    }

    public List<UsuarioEntity> listar() {
        return usuarioRepository.findAll();
    }

    public UsuarioEntity update(Long id, UsuarioEntity usuario) {
        UsuarioEntity existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));

        if (usuario.getNombre() != null) {
            existente.setNombre(usuario.getNombre());
        }

        if (usuario.getEmail() != null) {
            existente.setEmail(usuario.getEmail());
        }

        return usuarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
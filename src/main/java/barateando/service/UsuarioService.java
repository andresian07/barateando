package barateando.service;

import barateando.persistence.entity.UsuarioEntity;
import barateando.persistence.repository.UsuarioRepository;
import barateando.web.dto.UsuarioDto;
import barateando.web.dto.UsuarioRequest;
import barateando.web.dto.UsuarioUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDto crear(UsuarioRequest usuario) {
        UsuarioEntity newUsuario = new UsuarioEntity();
        newUsuario.setNombre(usuario.nombre());
        newUsuario.setEmail(usuario.email());
        this.usuarioRepository.save(newUsuario);
        return toDto(newUsuario);
    }

    public UsuarioDto buscarPorId(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        return toDto(usuario);
    }

    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll().stream().map(this::toDto).toList();
    }

    public UsuarioDto update(Long id, UsuarioUpdate usuario) {
        UsuarioEntity existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));

        if (usuario.nombre() != null) {
            existente.setNombre(usuario.nombre());
        }

        if (usuario.email() != null) {
            existente.setEmail(usuario.email());
        }

        this.usuarioRepository.save(existente);
        return toDto(existente);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDto toDto(UsuarioEntity usuario){
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail()
        );
    }
}
package barateando.web.controller;

import barateando.persistence.entity.UsuarioEntity;
import barateando.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barateando/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public UsuarioEntity get(@PathVariable Long id){
        return this.usuarioService.buscarPorId(id);
    }

    @GetMapping
    public List<UsuarioEntity> getAll(){
        return this.usuarioService.listar();
    }

    @PostMapping
    public UsuarioEntity create(@Valid @RequestBody UsuarioEntity usuario){
        return this.usuarioService.crear(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioEntity update(@PathVariable Long id, @RequestBody UsuarioEntity usuario){
        return this.usuarioService.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.usuarioService.eliminar(id);
    }
}

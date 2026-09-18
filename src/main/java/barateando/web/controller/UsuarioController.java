package barateando.web.controller;

import barateando.service.UsuarioService;
import barateando.web.dto.UsuarioDto;
import barateando.web.dto.UsuarioRequest;
import barateando.web.dto.UsuarioUpdate;
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
    public UsuarioDto get(@PathVariable Long id){
        return this.usuarioService.buscarPorId(id);
    }

    @GetMapping
    public List<UsuarioDto> getAll(){
        return this.usuarioService.listar();
    }

    @PostMapping
    public UsuarioDto create(@Valid @RequestBody UsuarioRequest usuario){
        return this.usuarioService.crear(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioDto update(@PathVariable Long id, @RequestBody UsuarioUpdate usuario){
        return this.usuarioService.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.usuarioService.eliminar(id);
    }
}

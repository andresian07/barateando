package barateando.web.controller;

import barateando.service.PresupuestoService;
import barateando.web.dto.PresupuestoDto;
import barateando.web.dto.PresupuestoRequest;
import barateando.web.dto.PresupuestoUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barateando/presupuesto")
@RequiredArgsConstructor
public class PresupuestoController {
    private final PresupuestoService presupuestoService;

    @GetMapping("/{id}")
    public PresupuestoDto get(@PathVariable Long id){
        return this.presupuestoService.get(id);
    }

    @GetMapping
    public List<PresupuestoDto> getAll(){
        return this.presupuestoService.getAll();
    }

    @PostMapping
    public PresupuestoDto crear(@Valid @RequestBody PresupuestoRequest presupuesto){
        return this.presupuestoService.crear(presupuesto);
    }

    @PutMapping("/{id}")
    public PresupuestoDto update(@PathVariable Long id, @Valid @RequestBody PresupuestoUpdate presupuesto ){
        return this.presupuestoService.update(id, presupuesto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.presupuestoService.delete(id);
    }
}

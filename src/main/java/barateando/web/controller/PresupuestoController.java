package barateando.web.controller;

import barateando.persistence.entity.PresupuestoEntity;
import barateando.service.PresupuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barateando/presupuesto")
@RequiredArgsConstructor
public class PresupuestoController {
    private final PresupuestoService presupuestoService;

    @GetMapping("/{id}")
    public PresupuestoEntity get(@PathVariable Long id){
        return this.presupuestoService.get(id);
    }

    @GetMapping
    public List<PresupuestoEntity> getAll(){
        return this.presupuestoService.getAll();
    }

    @PostMapping
    public PresupuestoEntity crear(@RequestBody PresupuestoEntity presupuesto){
        return this.presupuestoService.crear(presupuesto);
    }

    @PutMapping("/{id}")
    public PresupuestoEntity update(@PathVariable Long id, @RequestBody PresupuestoEntity presupuesto ){
        return this.presupuestoService.update(id, presupuesto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.presupuestoService.delete(id);
    }
}

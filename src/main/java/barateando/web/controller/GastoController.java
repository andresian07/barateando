package barateando.web.controller;

import barateando.persistence.entity.GastoEntity;
import barateando.service.GastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barateando/gasto")
public class GastoController {
    private final GastoService gastoService;

    @GetMapping("/{id}")
    public GastoEntity get(@PathVariable Long id){
        return this.gastoService.get(id);
    }

    @GetMapping
    public List<GastoEntity> getAll(){
        return this.gastoService.getAll();
    }

    @PostMapping
    public GastoEntity create(@RequestBody GastoEntity gasto){
        return this.gastoService.create(gasto);
    }

    @PutMapping("/{id}")
    public GastoEntity update(@PathVariable Long id, @RequestBody GastoEntity gasto){
        return this.gastoService.update(id, gasto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.gastoService.delete(id);
    }
}

package barateando.web.controller;

import barateando.persistence.entity.ViajeEntity;
import barateando.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barateando/viajes")
@RequiredArgsConstructor
public class ViajeController {
    private final ViajeService viajeService;

    @GetMapping("/{id}")
    public ViajeEntity get(@PathVariable Long id){
        return this.viajeService.get(id);
    }

    @GetMapping
    public List<ViajeEntity> getAll(){
        return this.viajeService.getAll();
    }

    @PostMapping
    public ViajeEntity create(@RequestBody ViajeEntity viaje){
        return this.viajeService.crear(viaje);
    }

    @PutMapping("/{id}")
    public ViajeEntity update(@PathVariable Long id, @RequestBody ViajeEntity viaje){
        return this.viajeService.update(id,viaje);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.viajeService.delete(id);
    }

}

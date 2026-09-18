package barateando.web.controller;

import barateando.service.GastoService;
import barateando.web.dto.GastoDto;
import barateando.web.dto.GastoRequest;
import barateando.web.dto.GastoUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barateando/gasto")
public class GastoController {
    private final GastoService gastoService;

    @GetMapping("/{id}")
    public GastoDto get(@PathVariable Long id){
        return this.gastoService.get(id);
    }

    @GetMapping
    public List<GastoDto> getAll(){
        return this.gastoService.getAll();
    }

    @PostMapping
    public GastoDto create(@Valid @RequestBody GastoRequest request){
        return this.gastoService.create(request);
    }

    @PutMapping("/{id}")
    public GastoDto update(@PathVariable Long id, @Valid @RequestBody GastoUpdate update){
        return this.gastoService.update(id, update);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.gastoService.delete(id);
    }
}

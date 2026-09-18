package barateando.web.controller;

import barateando.service.ViajeService;
import barateando.web.dto.GastoDto;
import barateando.web.dto.ResumenViaje;
import barateando.web.dto.ViajeDto;
import barateando.web.dto.ViajeRequest;
import barateando.web.dto.ViajeUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barateando/viajes")
@RequiredArgsConstructor
public class ViajeController {
    private final ViajeService viajeService;

    @GetMapping("/{id}")
    public ViajeDto get(@PathVariable Long id){
        return this.viajeService.get(id);
    }

    @GetMapping
    public List<ViajeDto> getAll(){
        return this.viajeService.getAll();
    }

    @PostMapping
    public ViajeDto create(@Valid @RequestBody ViajeRequest viaje){
        return this.viajeService.crear(viaje);
    }

    @PostMapping("/{viajeId}/participantes/{usuarioId}")
    public ViajeDto agregarParticipante(@PathVariable Long viajeId,@PathVariable Long usuarioId){
        return this.viajeService.agregarParticipante(viajeId,usuarioId);
    }

    @PutMapping("/{id}")
    public ViajeDto update(@PathVariable Long id, @Valid @RequestBody ViajeUpdate viaje){
        return this.viajeService.update(id,viaje);
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.viajeService.delete(id);
    }

    @GetMapping("/{id}/resumen")
    public ResumenViaje getResumen(@PathVariable Long id){
        return this.viajeService.getResumen(id);
    }

    @GetMapping("/{id}/gastos")
    public List<GastoDto> gastoPorViaje(@PathVariable Long id){
        return this.viajeService.getGastosPorViaje(id);
    }

}

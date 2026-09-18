package barateando.service;

import barateando.persistence.entity.PresupuestoEntity;
import barateando.persistence.entity.ViajeEntity;
import barateando.persistence.repository.PresupuestoRepository;
import barateando.persistence.repository.ViajeRepository;
import barateando.web.dto.PresupuestoDto;
import barateando.web.dto.PresupuestoRequest;
import barateando.web.dto.PresupuestoUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PresupuestoService {
    private final PresupuestoRepository presupuestoRepository;
    private final ViajeRepository viajeRepository;

    public PresupuestoDto get(Long id){
        PresupuestoEntity presupuesto = this.presupuestoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado: " + id));
        return toDto(presupuesto);
    }

    public List<PresupuestoDto> getAll(){
        return this.presupuestoRepository.findAll().stream().map(this::toDto).toList();
    }

    public PresupuestoDto crear(PresupuestoRequest request){
        ViajeEntity viaje = this.viajeRepository.findById(request.idViaje())
                .orElseThrow(() -> new IllegalArgumentException("no se encontro el viaje: " + request.idViaje()));
        PresupuestoEntity newPresupuesto = new PresupuestoEntity();
        newPresupuesto.setMontoPresupuesto(request.montoPresupuesto());
        newPresupuesto.setViaje(viaje);
        this.presupuestoRepository.save(newPresupuesto);
        return toDto(newPresupuesto);

    }

    public PresupuestoDto update(Long id, PresupuestoUpdate presupuesto){
        PresupuestoEntity existente = presupuestoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no se encontro el presupuesto: " + id));
        if (presupuesto.montoPresupuesto() != null){
            existente.setMontoPresupuesto(presupuesto.montoPresupuesto());
        }
        this.presupuestoRepository.save(existente);
        return toDto(existente);


    }

    public void delete(Long id){
        this.presupuestoRepository.deleteById(id);
    }

    public PresupuestoDto toDto(PresupuestoEntity presupuesto){
        return new PresupuestoDto(
                presupuesto.getId(),
                presupuesto.getMontoPresupuesto(),
                presupuesto.getViaje().getId()
        );
    }

}

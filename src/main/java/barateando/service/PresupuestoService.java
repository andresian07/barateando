package barateando.service;

import barateando.persistence.entity.PresupuestoEntity;
import barateando.persistence.entity.ViajeEntity;
import barateando.persistence.repository.PresupuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PresupuestoService {
    private final PresupuestoRepository presupuestoRepository;

    public PresupuestoEntity get(Long id){
        return this.presupuestoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Presupuesto no encontrado: " + id));
    }

    public List<PresupuestoEntity> getAll(){
        return this.presupuestoRepository.findAll();
    }

    public PresupuestoEntity crear(PresupuestoEntity presupuestoEntity){
        return this.presupuestoRepository.save(presupuestoEntity);
    }

    public PresupuestoEntity update(Long id, PresupuestoEntity presupuesto){
        PresupuestoEntity existente = presupuestoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no se encontro el presupuesto: " + id));
        if (presupuesto.getMontoPresupuesto() != null){
            existente.setMontoPresupuesto(presupuesto.getMontoPresupuesto());
        }
        return this.presupuestoRepository.save(existente);


    }

    public void delete(Long id){
        this.presupuestoRepository.deleteById(id);
    }

}

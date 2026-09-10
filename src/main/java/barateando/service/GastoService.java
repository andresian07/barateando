package barateando.service;

import barateando.persistence.entity.GastoEntity;
import barateando.persistence.repository.GastoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GastoService {
    private final GastoRepository gastoRepository;

    public GastoEntity get(Long id){
        return this.gastoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no se encontro el gasto: " + id));
    }

    public List<GastoEntity> getAll(){
        return this.gastoRepository.findAll();
    }

    public GastoEntity create(GastoEntity gasto){
        return this.gastoRepository.save(gasto);
    }

    public GastoEntity update(Long id, GastoEntity gasto){
        GastoEntity existente = gastoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no se encontro el gasto: " + id));

        if (gasto.getMonto() != null){
            existente.setMonto(gasto.getMonto());
        }

        if (gasto.getDescripcion() != null){
            existente.setDescripcion(gasto.getDescripcion());
        }

        if (gasto.getFechaGasto() != null){
            existente.setFechaGasto(gasto.getFechaGasto());
        }

        return this.gastoRepository.save(existente);
    }

    public void delete(Long id){
        this.gastoRepository.deleteById(id);
    }
}

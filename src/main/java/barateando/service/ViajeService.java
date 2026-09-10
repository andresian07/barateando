package barateando.service;

import barateando.persistence.entity.ViajeEntity;
import barateando.persistence.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViajeService {
    private final ViajeRepository viajeRepository;

    public ViajeEntity crear (ViajeEntity viaje){
        return this.viajeRepository.save(viaje);
    }

    public ViajeEntity get(Long id){
        return viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado: " + id));
    }

    public List<ViajeEntity> getAll(){
        return this.viajeRepository.findAll();
    }

    public ViajeEntity update(Long id, ViajeEntity viaje){
        Optional<ViajeEntity> optionalViaje = this.viajeRepository.findById(id);
        ViajeEntity viajeEntity = optionalViaje.orElseThrow(() -> new IllegalArgumentException("el viaje no se encontro: " + id));

        if(viaje.getNombre() != null){
            viajeEntity.setNombre(viaje.getNombre());
        }

        if (viaje.getDestino() != null){
            viajeEntity.setDestino(viaje.getDestino());
        }

        if(viaje.getFechaInicio() != null){
            viajeEntity.setFechaInicio(viaje.getFechaInicio());
        }

        if(viaje.getFechaFin() != null){
            viajeEntity.setFechaFin(viaje.getFechaFin());
        }

        return this.viajeRepository.save(viajeEntity);
    }

    public void delete(Long id){
         viajeRepository.deleteById(id);
    }



}

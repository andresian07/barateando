package barateando.service;

import barateando.persistence.entity.GastoEntity;
import barateando.persistence.entity.PresupuestoEntity;
import barateando.persistence.entity.ViajeEntity;
import barateando.persistence.repository.GastoRepository;
import barateando.persistence.repository.PresupuestoRepository;
import barateando.persistence.repository.ViajeRepository;
import barateando.web.dto.GastoDto;
import barateando.web.dto.ResumenViaje;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViajeService {
    private final ViajeRepository viajeRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final GastoRepository gastoRepository;

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

    public ResumenViaje getResumen(Long viajeId){
        BigDecimal presupuesto = presupuestoRepository.findByViaje_Id(viajeId)
                .map(PresupuestoEntity::getMontoPresupuesto)
                .orElse(BigDecimal.ZERO);

        BigDecimal totalGastado = gastoRepository.findByViaje_Id(viajeId).stream()
                .map(GastoEntity::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal diferencia = presupuesto.subtract(totalGastado);

        return new ResumenViaje(presupuesto, totalGastado, diferencia);
    }

    public List<GastoDto> getGastosPorViaje(Long viajeId){
        return gastoRepository.findByViaje_Id(viajeId).stream().map(g -> new GastoDto(
                g.getId(),
                g.getMonto(),
                g.getDescripcion(),
                g.getFechaGasto(),
                g.getUsuario().getNombre()
        )).toList();
    }

}

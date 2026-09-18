package barateando.service;

import barateando.persistence.entity.GastoEntity;
import barateando.persistence.entity.PresupuestoEntity;
import barateando.persistence.entity.UsuarioEntity;
import barateando.persistence.entity.ViajeEntity;
import barateando.persistence.repository.GastoRepository;
import barateando.persistence.repository.PresupuestoRepository;
import barateando.persistence.repository.UsuarioRepository;
import barateando.persistence.repository.ViajeRepository;
import barateando.web.dto.GastoDto;
import barateando.web.dto.ResumenViaje;
import barateando.web.dto.UsuarioDto;
import barateando.web.dto.ViajeDto;
import barateando.web.dto.ViajeRequest;
import barateando.web.dto.ViajeUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeService {
    private final ViajeRepository viajeRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final GastoRepository gastoRepository;
    private final UsuarioRepository usuarioRepository;

    public ViajeDto crear(ViajeRequest request){
        ViajeEntity newViaje = new ViajeEntity();
        newViaje.setNombre(request.nombre());
        newViaje.setDestino(request.destino());
        newViaje.setFechaInicio(request.fechaInicio());
        newViaje.setFechaFin(request.fechaFin());

        this.viajeRepository.save(newViaje);
        return toDto(newViaje);
    }

    public ViajeDto get(Long id){
        ViajeEntity viaje = viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Viaje no encontrado: " + id));
        return toDto(viaje);
    }

    public List<ViajeDto> getAll(){
        return this.viajeRepository.findAll().stream().map(this::toDto).toList();
    }

    public ViajeDto update(Long id, ViajeUpdate viaje){
        ViajeEntity viajeEntity = this.viajeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("el viaje no se encontro: " + id));

        if(viaje.nombre() != null){
            viajeEntity.setNombre(viaje.nombre());
        }

        if (viaje.destino() != null){
            viajeEntity.setDestino(viaje.destino());
        }

        if(viaje.fechaInicio() != null){
            viajeEntity.setFechaInicio(viaje.fechaInicio());
        }

        if(viaje.fechaFin() != null){
            viajeEntity.setFechaFin(viaje.fechaFin());
        }

        this.viajeRepository.save(viajeEntity);
        return toDto(viajeEntity);
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


    public ViajeDto agregarParticipante(Long viajeId, Long usuarioId){
        ViajeEntity viaje = this.viajeRepository.findById(viajeId)
                .orElseThrow(() -> new IllegalArgumentException("el viaje no se encuentra: " + viajeId));
        UsuarioEntity usuario = this.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("el usuario no se encuntra: " + usuarioId));
        viaje.getParticipantes().add(usuario);

        this.viajeRepository.save(viaje);
        return toDto(viaje);
    }

    private ViajeDto toDto(ViajeEntity viaje){
        List<UsuarioDto> participantes = viaje.getParticipantes().stream()
                .map(u -> new UsuarioDto(u.getId(), u.getNombre(), u.getEmail()))
                .toList();

        return new ViajeDto(
                viaje.getId(),
                viaje.getNombre(),
                viaje.getDestino(),
                viaje.getFechaInicio(),
                viaje.getFechaFin(),
                participantes
        );
    }

}

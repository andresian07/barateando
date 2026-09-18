package barateando.service;

import barateando.web.exception.RecursoNoEncontradoException;

import barateando.persistence.entity.GastoEntity;
import barateando.persistence.entity.UsuarioEntity;
import barateando.persistence.entity.ViajeEntity;
import barateando.persistence.repository.GastoRepository;
import barateando.persistence.repository.UsuarioRepository;
import barateando.persistence.repository.ViajeRepository;
import barateando.web.dto.GastoDto;
import barateando.web.dto.GastoRequest;
import barateando.web.dto.GastoUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GastoService {
    private final GastoRepository gastoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ViajeRepository viajeRepository;

    public GastoDto get(Long id){
        GastoEntity gasto = this.gastoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("no se encontro el gasto: " + id));
        return toDto(gasto);
    }

    public List<GastoDto> getAll(){

        return this.gastoRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public GastoDto create(GastoRequest request){
        UsuarioEntity usuario = this.usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("no se encontro el usuario: " + request.idUsuario()));
        ViajeEntity viaje = this.viajeRepository.findById(request.idViaje())
                .orElseThrow(() -> new RecursoNoEncontradoException("no se encontro el viaje: " + request.idViaje()));

        GastoEntity newGasto = new GastoEntity();
        newGasto.setMonto(request.monto());
        newGasto.setDescripcion(request.descripcion());
        newGasto.setFechaGasto(request.fechaGasto());
        newGasto.setViaje(viaje);
        newGasto.setUsuario(usuario);

        this.gastoRepository.save(newGasto);
        return toDto(newGasto);



    }

    public GastoDto update(Long id, GastoUpdate gasto){
        GastoEntity existente = gastoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("no se encontro el gasto: " + id));

        if (gasto.monto() != null){
            existente.setMonto(gasto.monto());
        }

        if (gasto.descripcion() != null){
            existente.setDescripcion(gasto.descripcion());
        }

        if (gasto.fechaGasto() != null){
            existente.setFechaGasto(gasto.fechaGasto());
        }

        this.gastoRepository.save(existente);
        return toDto(existente);
    }

    public void delete(Long id){
        if (!gastoRepository.existsById(id)){
            throw new RecursoNoEncontradoException("no se encontro el gasto: " + id);
        }
        this.gastoRepository.deleteById(id);
    }


    private GastoDto toDto(GastoEntity gasto){
        return new GastoDto(
                gasto.getId(),
                gasto.getMonto(),
                gasto.getDescripcion(),
                gasto.getFechaGasto(),
                gasto.getUsuario().getNombre()
        );
    }
}

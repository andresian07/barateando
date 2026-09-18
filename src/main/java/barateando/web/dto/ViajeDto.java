package barateando.web.dto;

import java.time.LocalDate;
import java.util.List;

public record ViajeDto(
        Long id,
        String nombre,
        String destino,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        List<UsuarioDto> participantes
) { }

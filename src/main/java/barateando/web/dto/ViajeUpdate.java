package barateando.web.dto;

import java.time.LocalDate;

public record ViajeUpdate(
        String nombre,
        String destino,
        LocalDate fechaInicio,
        LocalDate fechaFin
) { }

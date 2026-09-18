package barateando.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ViajeUpdate(
        @Size(max = 120)
        String nombre,
        @Size(max = 120)
        String destino,
        @FutureOrPresent
        LocalDate fechaInicio,
        @Future
        LocalDate fechaFin
) { }

package barateando.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ViajeRequest(
        @NotBlank
        String nombre,
        @NotBlank
        String destino,
        @FutureOrPresent
        LocalDate fechaInicio,
        @Future
        LocalDate fechaFin
) { }

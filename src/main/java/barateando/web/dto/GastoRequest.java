package barateando.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoRequest(
        @Positive
        BigDecimal monto,
        @NotBlank
        String descripcion,
        @PastOrPresent
        LocalDate fechaGasto,
        Long idUsuario,
        Long idViaje
) { }
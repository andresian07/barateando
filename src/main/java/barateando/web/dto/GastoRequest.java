package barateando.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoRequest(
        @Positive @NotNull
        BigDecimal monto,
        @NotBlank @Size(max = 120)
        String descripcion,
        @PastOrPresent @NotNull
        LocalDate fechaGasto,
        @NotNull
        Long idUsuario,
        @NotNull
        Long idViaje
) { }
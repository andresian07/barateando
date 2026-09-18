package barateando.web.dto;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoUpdate(
        @Positive
        BigDecimal monto,
        @Size(max = 120)
        String descripcion,
        @PastOrPresent
        LocalDate fechaGasto
) { }

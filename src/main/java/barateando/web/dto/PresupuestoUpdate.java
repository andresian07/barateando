package barateando.web.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PresupuestoUpdate(
    @Positive
    BigDecimal montoPresupuesto
)
{ }

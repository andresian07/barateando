package barateando.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PresupuestoRequest(
   @NotNull @Positive
   BigDecimal montoPresupuesto,
   @NotNull
   Long idViaje
)
{}

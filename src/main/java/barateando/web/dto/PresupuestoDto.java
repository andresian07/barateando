package barateando.web.dto;

import java.math.BigDecimal;

public record PresupuestoDto(
     Long id,
     BigDecimal montoPresupuesto,
     Long idViaje
) { }

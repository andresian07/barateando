package barateando.web;

import java.math.BigDecimal;

public record ResumenViaje(
      BigDecimal presupuesto,
      BigDecimal totalGastado,
      BigDecimal diferencia
) { }

package barateando.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoDto(
    Long id,
    BigDecimal monto,
    String descripcion,
    LocalDate fechaGasto,
    String nombreUsuario
) { }

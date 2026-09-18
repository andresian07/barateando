package barateando.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoUpdate(
        BigDecimal monto,
        String descripcion,
        LocalDate fechaGasto
) { }

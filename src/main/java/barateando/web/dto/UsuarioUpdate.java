package barateando.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UsuarioUpdate(
        @Size(max = 120)
        String nombre,
        @Email @Size(max = 120)
        String email
) { }

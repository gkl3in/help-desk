package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

public record AuthenticateRequest(

        @Schema(description = "User email", example = "gabriel.klein@mail.com")
        @Email(message = "Invalid email")
        @NotBlank(message = "Email cannot be empty")
        @Size(min = 6, max = 50, message = "Email must contain between 6 and 50 characters")
        String email,

        @Schema(description = "User password", example = "Cotoco.12")
        @Size(min = 6, max = 70, message = "Password must contain between 6 and 70 characters")
        @NotBlank(message = "Password cannot be empty")
        String password

) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
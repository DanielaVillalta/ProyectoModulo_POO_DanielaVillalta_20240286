package DanielaVillalta_20240286.DanielaVillalta_20240286.Models.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class ProviderDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String address;

    @Email(message = "Debe ser un correo electrónico válido")
    private String email;

    @NotBlank
    private String code;

    @NotBlank
    private Integer status;

    @NotBlank
    private String comments;
}

package DanielaVillalta_20240286.DanielaVillalta_20240286.Models.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @EqualsAndHashCode
public class ProviderDTO {
    //Atributos
    private Long id;

    @NotBlank //El texto no puede estar vacío
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String address;

    @Email(message = "Debe ser un correo electrónico válido") //El email debe ser válido
    private String email;

    @NotBlank
    private String code;

    @NotNull //El número no puede estar vacío
    private long status;

    @NotBlank
    private String comments;
}

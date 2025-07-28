package DanielaVillalta_20240286.DanielaVillalta_20240286.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBPROVIDER")
@Getter @Setter @ToString @EqualsAndHashCode
public class ProviderEntity {
    private Long id;
    private String name;
    private String phone;
    private String address;
}

package DanielaVillalta_20240286.DanielaVillalta_20240286.Entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBPROVIDER") //La tabla es TbProvider
@Getter @Setter @ToString @EqualsAndHashCode
public class ProviderEntity {
    //Atributos de la tabla
    @Id
    @Column(name = "PROVIDERID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_provider")
    @SequenceGenerator(name = "seq_provider", sequenceName = "seq_provider", allocationSize = 1) //Genera una secuencia que aumenta de uno en uno para el ID del proveedor
    private Long id;

    @Column(name = "PROVIDERNAME")
    private String name;

    @Column(name = "PROVIDERPHONE")
    private String phone;

    @Column(name = "PROVIDERADDRESS")
    private String address;

    @Column(name = "PROVIDEREMAIL")
    private String email;

    @Column(name = "PROVIDERCODE")
    private String code;

    @Column(name = "PROVIDERSTATUS")
    private long status;

    @Column(name = "PROVIDERCOMMENTS")
    private String comments;
}

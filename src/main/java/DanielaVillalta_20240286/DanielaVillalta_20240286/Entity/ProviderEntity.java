package DanielaVillalta_20240286.DanielaVillalta_20240286.Entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TBPROVIDER")
@Getter @Setter @ToString @EqualsAndHashCode
public class ProviderEntity {
    @Id
    @Column(name = "PROVIDERID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_provider")
    @SequenceGenerator(name = "seq_provider", sequenceName = "seq_provider", allocationSize = 1)
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
    private Integer status;

    @Column(name = "PROVIDERCOMMENTS")
    private String comments;
}

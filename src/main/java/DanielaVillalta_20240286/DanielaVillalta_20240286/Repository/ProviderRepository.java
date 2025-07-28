package DanielaVillalta_20240286.DanielaVillalta_20240286.Repository;

import DanielaVillalta_20240286.DanielaVillalta_20240286.Entity.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {
    //Es equivalente a tener una sentencia LIKE en sql
    List<ProviderEntity> findByNameContainingIgnoreCase(String name);
}

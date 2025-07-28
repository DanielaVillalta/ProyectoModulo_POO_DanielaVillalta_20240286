package DanielaVillalta_20240286.DanielaVillalta_20240286.Repository;

import DanielaVillalta_20240286.DanielaVillalta_20240286.Entity.ProviderEntity;
import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {
}

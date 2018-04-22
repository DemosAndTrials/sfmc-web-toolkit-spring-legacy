package sfmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sfmc.model.Authentication.ApiIntegrationSet;

@Repository("apiIntegrationSetRepository")
public interface ApiIntegrationSetRepository extends JpaRepository<ApiIntegrationSet, Integer> {
    ApiIntegrationSet findByUserId(int userId);
}

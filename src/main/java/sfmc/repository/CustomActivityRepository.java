package sfmc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sfmc.model.CustomActivityConfig;

/**
 * Custom Activity Repository
 * http://docs.spring.io/spring-data/jpa/docs/1.5.1.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
*/
@Repository
public interface CustomActivityRepository extends CrudRepository<CustomActivityConfig, Integer>, CustomActivityRepositoryCustom {

}

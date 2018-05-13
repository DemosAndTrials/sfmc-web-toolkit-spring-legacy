package sfmc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sfmc.model.Scripts.ScriptItem;

@Repository("scriptsRepository")
public interface ScriptsRepository extends CrudRepository<ScriptItem, Integer> {
}

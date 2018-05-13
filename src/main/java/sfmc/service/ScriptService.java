package sfmc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sfmc.model.Scripts.ScriptItem;
import sfmc.repository.ScriptsRepository;

/**
 * Script Service
 */
@Service
public class ScriptService {

    @Qualifier("scriptsRepository")
    @Autowired
    ScriptsRepository scriptsRepository;

    /**
     * Get all scripts
     *
     * @return
     */
    public Iterable<ScriptItem> getScripts() {
        Iterable<ScriptItem> list = scriptsRepository.findAll();
        return list;
    }


    /**
     * Save config into db
     *
     * @param item
     * @return config
     */
    public ScriptItem createScript(ScriptItem item) {
        // save
        ScriptItem savedItem = scriptsRepository.save(item);
        return savedItem;
    }
}

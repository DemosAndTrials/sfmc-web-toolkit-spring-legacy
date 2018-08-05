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
     * Get script item by id
     *
     * @return
     */
    public ScriptItem getScript(String id) {
        try {
            ScriptItem item = scriptsRepository.findById(Integer.parseInt(id)).orElse(null);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save script into db
     *
     * @param item
     * @return script
     */
    public ScriptItem createScript(ScriptItem item) {
        // save
        ScriptItem savedItem = scriptsRepository.save(item);
        return savedItem;
    }

    /**
     * delete
     *
     * @param id
     * @return
     */
    public boolean deleteScriptById(String id) {
        try {
            scriptsRepository.deleteById(Integer.parseInt(id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

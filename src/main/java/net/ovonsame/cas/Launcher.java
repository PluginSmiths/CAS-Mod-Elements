package net.ovonsame.cas;

import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import net.ovonsame.cas.parts.PluginElementTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Launcher extends JavaPlugin {

    private static final Logger LOG = LogManager.getLogger("Creating Addons Support");
    public static ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
    public Launcher(Plugin plugin) {
        super(plugin);

        addListener(PreGeneratorsLoadingEvent.class, e -> PluginElementTypes.load());
        LOG.info("Creating Addons Support plugin was loaded");
    }

}
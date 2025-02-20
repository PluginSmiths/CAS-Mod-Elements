package me.barbod.config;

import me.barbod.config.element.PluginElementType;
import me.barbod.config.ui.blockly.PluginBlocklyEditorType;
import net.mcreator.blockly.data.BlocklyLoader;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher extends JavaPlugin {
   public static final Logger LOGGER = LogManager.getLogger("Cloth Config API");

   public Launcher(Plugin plugin) {
      super(plugin);
      this.addListener(PreGeneratorsLoadingEvent.class, (e) -> {
         BlocklyLoader.addBuiltinCategory("config_screen");
         BlocklyLoader.addBuiltinCategory("config_category");
         BlocklyLoader.addBuiltinCategory("config_entries");
         BlocklyLoader.addBuiltinCategory("config_utils");
         BlocklyLoader.addBuiltinCategory("config_requirements");
         BlocklyLoader.addBuiltinCategory("config_variables");
         BlocklyLoader.INSTANCE.addBlockLoader(PluginBlocklyEditorType.CONFIG);
         PluginElementType.load();
      });
      LOGGER.info("Cloth Config API plugin was loaded");
   }
}

package net.ovonsame.cas;

import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import net.ovonsame.cas.elements.PluginElementTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher extends JavaPlugin {
   public static final Logger LOGGER = LogManager.getLogger("Modded Elements");

   public Launcher(Plugin plugin) {
      super(plugin);
      addListener(PreGeneratorsLoadingEvent.class, e -> PluginElementTypes.load());
      LOGGER.info("Modded Elements plugin was loaded");
   }
}

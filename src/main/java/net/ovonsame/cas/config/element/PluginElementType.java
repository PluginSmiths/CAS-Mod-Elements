package net.ovonsame.cas.config.element;

import net.ovonsame.config.element.types.Config;
import net.ovonsame.config.ui.modgui.ConfigGUI;
import net.mcreator.element.ModElementType;
import net.mcreator.element.ModElementTypeLoader;

public class PluginElementType {
   public static ModElementType<?> CONFIG;

   public static void load() {
      CONFIG = ModElementTypeLoader.register(new ModElementType("config", (Character)null, ConfigGUI::new, Config.class));
   }
}

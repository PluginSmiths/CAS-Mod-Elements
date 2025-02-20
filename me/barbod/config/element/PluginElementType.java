package me.barbod.config.element;

import me.barbod.config.element.types.Config;
import me.barbod.config.ui.modgui.ConfigGUI;
import net.mcreator.element.ModElementType;
import net.mcreator.element.ModElementTypeLoader;

public class PluginElementType {
   public static ModElementType<?> CONFIG;

   public static void load() {
      CONFIG = ModElementTypeLoader.register(new ModElementType("config", (Character)null, ConfigGUI::new, Config.class));
   }
}

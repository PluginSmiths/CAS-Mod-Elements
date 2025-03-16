package net.ovonsame.cas.parts;

import net.mcreator.element.ModElementType;
import net.ovonsame.cas.elements.*;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> MODDED_RECIPE;

    public static void load() {
        MODDED_RECIPE = register(
                new ModElementType<>("modded_recipe", (Character) null, ModdedRecipeGUI::new, ModdedRecipe.class)
        );
    }

}
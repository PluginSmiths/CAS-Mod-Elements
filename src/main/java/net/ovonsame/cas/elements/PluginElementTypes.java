package net.ovonsame.cas.elements;

import net.mcreator.element.ModElementType;

import static net.mcreator.element.ModElementTypeLoader.register;

public class PluginElementTypes {
    public static ModElementType<?> MODDED_RECIPE;

    public static void load() {
        MODDED_RECIPE = register(
                new ModElementType<>("modded_recipe", (Character) null, ModdedRecipeMain::new, ModdedRecipe.class)
        );
    }

}
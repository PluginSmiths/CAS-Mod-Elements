package net.ovonsame.cas.recipes;

import net.mcreator.ui.MCreator;
import net.ovonsame.cas.elements.ModdedRecipe.ModdedRecipeBase;

public class CuttingRecipe extends ModdedRecipeBase {
    public CuttingRecipe(MCreator mcreator) {
        super(mcreator, false, false, false, true, "cutting", new String[]{"Pickaxe", "Knives", "Axe dig", "Axe strip", "Shovel", "Hoe", "Sword"});
    }
}

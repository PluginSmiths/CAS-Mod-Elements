package net.ovonsame.cas.recipes;

import net.mcreator.ui.MCreator;
import net.ovonsame.cas.elements.ModdedRecipe.ModdedRecipeBase;

public class CookingRecipe extends ModdedRecipeBase {
    public CookingRecipe(MCreator mcreator) {
        super(mcreator, false, true, true, true, "cooking", new String[]{"Meals", "Drinks", "Misc"});
    }
}

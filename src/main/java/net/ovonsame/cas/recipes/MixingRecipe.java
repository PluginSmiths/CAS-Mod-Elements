package net.ovonsame.cas.recipes;

import net.mcreator.ui.MCreator;
import net.ovonsame.cas.elements.ModdedRecipeBase;

public class MixingRecipe extends ModdedRecipeBase {
    public MixingRecipe(MCreator mcreator) {
        super(mcreator, false, false, false, true, "mixing", new String[]{"Not Heated", "Heated", "Superheated"});
    }
}

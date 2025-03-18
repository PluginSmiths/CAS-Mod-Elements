package net.ovonsame.cas.recipes;

import net.mcreator.ui.MCreator;
import net.mcreator.workspace.elements.ModElement;
import net.ovonsame.cas.elements.ModdedRecipeBase;

public class CuttingRecipe extends ModdedRecipeBase {
    public CuttingRecipe(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode, true, true, true, true, "cutting", new String[]{"hello", "fgrg"});
    }
}

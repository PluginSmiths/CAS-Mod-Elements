package net.ovonsame.cas.parts.Ingredient;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JSimpleEntriesList;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.ovonsame.cas.elements.ModdedRecipe.ModdedRecipe;

import javax.swing.*;
import java.util.List;

public class IngredientList extends JSimpleEntriesList<IngredientListEntry, ModdedRecipe.Ingredient> {

    public IngredientList(MCreator mcreator, IHelpContext gui) {
        super(mcreator, gui);

        add.setText(L10N.t("elementgui.modded_recipe.add_ingredient"));

        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1),
                        BorderFactory.createEmptyBorder(2, 2, 2, 2))));
    }

    @Override
    protected IngredientListEntry newEntry(JPanel parent, List<IngredientListEntry> entryList, boolean userAction) {
        return new IngredientListEntry(mcreator, gui, parent, entryList);
    }

    public AggregatedValidationResult getValidationResult() {
        AggregatedValidationResult validationResult = new AggregatedValidationResult();
        entryList.forEach(validationResult::addValidationElement);
        return validationResult;
    }

}

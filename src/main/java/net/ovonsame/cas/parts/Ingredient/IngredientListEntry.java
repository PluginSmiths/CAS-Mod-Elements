package net.ovonsame.cas.parts.Ingredient;

import net.mcreator.minecraft.ElementUtil;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JSimpleListEntry;
import net.mcreator.ui.help.IHelpContext;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.Validator;
import net.ovonsame.cas.elements.ModdedRecipe.ModdedRecipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class IngredientListEntry extends JSimpleListEntry<ModdedRecipe.Ingredient> implements IValidable {

    private final MCreator mcreator;

    private final MCItemHolder item;
    private final JSpinner amount;

    public IngredientListEntry(MCreator mcreator, IHelpContext gui, JPanel parent,
                               List<IngredientListEntry> entryList) {
        super(parent, entryList);
        this.mcreator = mcreator;

        item = new MCItemHolder(mcreator, ElementUtil::loadBlocksAndItemsAndTagsAndPotions);
        amount = new JSpinner(new SpinnerNumberModel(1, 0, 64, 1));

        line.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        line.add(L10N.label("elementgui.modded_recipe.item"));
        line.add(item);

        line.add(L10N.label("elementgui.modded_recipe.amount"));
        line.add(amount);
    }

    @Override protected void setEntryEnabled(boolean enabled) {
        item.setEnabled(enabled);
        amount.setEnabled(enabled);
    }

    @Override public ModdedRecipe.Ingredient getEntry() {
        ModdedRecipe.Ingredient entry = new ModdedRecipe.Ingredient();
        entry.setWorkspace(mcreator.getWorkspace());
        entry.amount = (int) amount.getValue();
        entry.item = item.getBlock();
        return entry;
    }

    @Override public void setEntry(ModdedRecipe.Ingredient e) {
        item.setBlock(e.item);
        amount.setValue(e.amount);
    }

    @Override public Validator.ValidationResult getValidationStatus() {
        return item.getValidationStatus();
    }

    @Override public void setValidator(Validator validator) {
        item.setValidator(validator);
    }

    @Override public Validator getValidator() {
        return item.getValidator();
    }
}

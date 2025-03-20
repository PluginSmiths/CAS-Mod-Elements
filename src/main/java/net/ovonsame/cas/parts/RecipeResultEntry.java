package net.ovonsame.cas.parts;

import net.mcreator.element.parts.MItemBlock;
import net.mcreator.minecraft.MCItem;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.entries.JSimpleListEntry;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.MCItemHolder;
import net.mcreator.ui.validation.IValidable;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.Validator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecipeResultEntry extends JSimpleListEntry<RecipeResultEntry> implements IValidable {
    private final MCItemHolder item;
    private final JSpinner amount;
    private final JSpinner chance;
    private Validator validator;
    private final ValidationGroup validationGroup = new ValidationGroup();

    public RecipeResultEntry(JPanel parent, List<RecipeResultEntry> entryList,
                             MCreator mcreator, MCItem.ListProvider itemsWithTags) {
        super(parent, entryList);

        this.item = new MCItemHolder(mcreator, itemsWithTags, true);
        this.amount = new JSpinner(new SpinnerNumberModel(1, 1, 64, 1));
        this.chance = new JSpinner(new SpinnerNumberModel(1.0f, 0.0f, 1.0f, 0.01f));

        JPanel panel = new JPanel(new GridLayout(1, 3, 5, 5));

        panel.add(L10N.label("elementgui.modded_recipe.item"));
        panel.add(item);

        panel.add(L10N.label("elementgui.modded_recipe.amount"));
        panel.add(amount);

        panel.add(L10N.label("elementgui.modded_recipe.chance"));
        panel.add(chance);

        this.line.add(panel);
    }

    public void setEntry(MItemBlock item, int amount, float chance) {
        this.item.setBlock(item);
        this.amount.setValue(Math.max(1, Math.min(64, amount)));
        this.chance.setValue(Math.max(0.0f, Math.min(1.0f, chance)));
    }

    public MItemBlock getItemBlock() {
        return (MItemBlock) item.getBlock();
    }

    public int getAmount() {
        return (int) amount.getValue();
    }

    public float getChance() {
        return ((Number) chance.getValue()).floatValue();
    }

    @Override
    public Validator.ValidationResult getValidationStatus() {
        return validationGroup.validateIsErrorFree() ? Validator.ValidationResult.PASSED :
                new Validator.ValidationResult(Validator.ValidationResultType.ERROR, "Invalid recipe ingredient entry");
    }

    @Override
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    protected void setEntryEnabled(boolean enabled) {
        item.setEnabled(enabled);
        amount.setEnabled(enabled);
        chance.setEnabled(enabled);
    }

    @Override
    public RecipeResultEntry getEntry() {
        return this;
    }

    @Override
    public void setEntry(RecipeResultEntry entry) {
        if (entry != null) {
            setEntry(entry.getItemBlock(), entry.getAmount(), entry.getChance());
        }
    }
}

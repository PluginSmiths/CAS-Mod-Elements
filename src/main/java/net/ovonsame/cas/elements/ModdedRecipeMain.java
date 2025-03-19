package net.ovonsame.cas.elements;

import net.mcreator.element.parts.Sound;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.SoundSelector;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;
import net.ovonsame.cas.recipes.CuttingRecipe;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ModdedRecipeMain extends ModElementGUI<ModdedRecipe> {
    private final CardLayout card_layout = new CardLayout();
    private final JPanel recipe_container = new JPanel(card_layout);
    private final JComboBox<String> recipe_type = new JComboBox<>(ModdedRecipe.recipes);
    private ModdedRecipeBase recipe;

    private final Map<String, ModdedRecipeBase> recipes = new HashMap<>(
            Map.of("cutting", new CuttingRecipe(mcreator, modElement, isEditingMode()))
    );

    public ModdedRecipeMain(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        JPanel type_panel = new JPanel(new GridLayout(1, 1, 10, 2));
        JPanel recipe_panel = new JPanel(new GridLayout(1, 2, 10, 2));

        type_panel.add(L10N.label("elementgui." + this.recipe_type.getSelectedItem() + ".desc"));
        type_panel.add(recipe_type);

        recipe_container.add(type_panel);

        recipe_type.addActionListener(e -> {
            if ((String) recipe_type.getSelectedItem() != null) {
                recipe_panel.add(L10N.label("elementgui." + this.recipe_type.getSelectedItem() + ".desc"));
                recipe = recipes.get((String) recipe_type.getSelectedItem());
                recipe_panel.add(recipe);
                updateUI();
            }
        });
        recipe_type.setSelectedItem("cutting");

        recipe_container.add(recipe_panel);
        updateUI();
        addPage(recipe_container);
    }

    @Override
    public void openInEditingMode(ModdedRecipe recipe) {
        recipe_type.setSelectedItem(recipe.recipe_type);
        ModdedRecipeBase selected = recipes.get(recipe.recipe_type);

        selected.list.setSelectedItem(recipe.list);
        selected.experience.setValue(recipe.experience);
        selected.process_time.setValue(recipe.processing_time);

        if (selected.hasSound && selected.sound != null) selected.sound.setSound(recipe.sound);
    }

    @Override
    public ModdedRecipe getElementFromGUI() {
        ModdedRecipe recipe = new ModdedRecipe(modElement);
        recipe.recipe_type = (String) this.recipe_type.getSelectedItem();
        if(recipe_type.getSelectedItem() != null) {
            recipe.sound = (Sound) this.recipe.sound.getSound();
            recipe.experience = (int) this.recipe.experience.getValue();
            recipe.processing_time = (int) this.recipe.process_time.getValue();
            recipe.list = (String) this.recipe.list.getSelectedItem();
        }
        return recipe;
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult();
    }
}

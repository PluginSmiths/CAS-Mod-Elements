package net.ovonsame.cas.elements;

import net.mcreator.element.parts.Sound;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;
import net.ovonsame.cas.recipes.CuttingRecipe;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ModdedRecipeMain extends ModElementGUI<ModdedRecipe> {
    private final JPanel recipe_container = new JPanel(new CardLayout());
    private final JComboBox<String> recipe_type = new JComboBox<>(ModdedRecipe.recipes);

    private final Map<String, ModdedRecipeBase> recipes = new HashMap<>(
            Map.of("cutting", new CuttingRecipe(mcreator, modElement, isEditingMode()))
    );

    private ModdedRecipeBase recipe;

    public ModdedRecipeMain(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        setLayout(new BorderLayout());

        JPanel type_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel recipe_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        recipe_type.setBounds(20, 32, 32, 32);

        type_panel.add(new JLabel("Recipe Type:"));
        recipe_type.setVisible(true);
        type_panel.add(recipe_type);
        recipe_type.setSelectedItem("cutting");

        recipe = recipes.get((String) recipe_type.getSelectedItem());
        recipe_panel.add(recipe);

        recipe_type.addActionListener(e -> {
            recipe_container.removeAll();
            initGUI();
        });

        recipe_container.add(recipe_panel);
        recipe_container.add(type_panel);

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
        selected.sound.setSound(recipe.sound);
    }

    @Override
    public ModdedRecipe getElementFromGUI() {
        ModdedRecipe recipe = new ModdedRecipe(modElement);

        recipe.recipe_type = (String) this.recipe_type.getSelectedItem();
        recipe.sound = this.recipe.hasSound ? (Sound) this.recipe.sound.getSound() : new Sound(mcreator.getWorkspace(), "grass");
        recipe.experience = (this.recipe.hasExperience ? (int) this.recipe.experience.getValue() : 0);
        recipe.processing_time = this.recipe.hasProcessTime ?(int) this.recipe.process_time.getValue() : 0;
        recipe.list = this.recipe.hasList ? (String) this.recipe.list.getSelectedItem() : "";
        return recipe;
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult();
    }
}

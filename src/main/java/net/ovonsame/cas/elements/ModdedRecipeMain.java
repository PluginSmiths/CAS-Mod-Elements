package net.ovonsame.cas.elements;

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
    private final CardLayout card_layout = new CardLayout();
    private final JPanel recipe_container = new JPanel(card_layout);
    private final JComboBox<String> recipe_type = new JComboBox<>(ModdedRecipe.recipes);
    private final Map<String, ModdedRecipeBase> recipe_GUIs = new HashMap<>();

    public ModdedRecipeMain(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        JPanel type_panel = new JPanel(new GridLayout(1, 1, 10, 2));
        JPanel recipe_panel = new JPanel(new GridLayout(2, 1, 10, 2));

        type_panel.add(recipe_type);
        type_panel.add(L10N.label("elementgui." + this.recipe_type.getSelectedItem() + ".desc"));

        recipe_container.add(type_panel);

        recipe_GUIs.put("cutting", new CuttingRecipe(mcreator, modElement, isEditingMode()));

        String selectedType = (String) recipe_type.getSelectedItem();
        ModdedRecipeBase selectedGUI = recipe_GUIs.get(selectedType);
        if (selectedGUI != null) {
            recipe_panel.add(selectedGUI, selectedType);
        } else {
            System.out.println("Selected recipe type not found in recipe_GUIs: " + selectedType);
        }

        recipe_container.add(recipe_panel);
        addPage(recipe_container);

        recipe_type.addActionListener(e -> card_layout.show(recipe_container, (String) recipe_type.getSelectedItem()));
    }

    @Override
    public void openInEditingMode(ModdedRecipe recipe) {
        recipe_type.setSelectedItem(recipe.recipe_type);
        ModdedRecipeBase selectedGUI = recipe_GUIs.get(recipe.recipe_type);
        if (selectedGUI != null) {
            selectedGUI.openInEditingMode(recipe);
        }
    }

    @Override
    public ModdedRecipe getElementFromGUI() {
        String selectedType = (String) recipe_type.getSelectedItem();
        ModdedRecipeBase recipe_base = recipe_GUIs.get(selectedType);

        ModdedRecipe recipe = recipe_base.getElementFromGUI();
        recipe.recipe_type = selectedType;

        return recipe;
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult();
    }
}

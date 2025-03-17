package net.ovonsame.cas.elements;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;
import net.ovonsame.cas.recipes.CuttingRecipe;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ModdedRecipeMain extends ModElementGUI<ModdedRecipe> {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel recipeContainer = new JPanel(cardLayout);
    private final JComboBox<String> recipeType = new JComboBox<>(ModdedRecipe.recipes);
    private final Map<String, ModdedRecipeBase> recipeGUIs = new HashMap<>();

    public ModdedRecipeMain(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        JPanel selectionPage = new JPanel(new BorderLayout(10, 10));
        JPanel typePanel = new JPanel(new GridLayout(1, 2, 10, 2));
        typePanel.add(new JLabel("Select Recipe Type:"));
        typePanel.add(recipeType);
        selectionPage.add(typePanel, BorderLayout.CENTER);
        addPage(selectionPage);

        // Добавляем рецепты
        recipeGUIs.put("Cutting", new CuttingRecipe(mcreator, modElement, isEditingMode()));

        for (Map.Entry<String, ModdedRecipeBase> entry : recipeGUIs.entrySet()) {
            recipeContainer.add(entry.getValue(), entry.getKey());
        }

        addPage(recipeContainer);

        recipeType.addActionListener(e -> cardLayout.show(recipeContainer, (String) recipeType.getSelectedItem()));
    }

    @Override
    public void openInEditingMode(ModdedRecipe recipe) {
        recipeType.setSelectedItem(recipe.recipeType);
        recipeGUIs.get(recipe.recipeType).openInEditingMode(recipe);
    }

    @Override
    public ModdedRecipe getElementFromGUI() {
        String selectedType = (String) recipeType.getSelectedItem();
        return recipeGUIs.get(selectedType).getElementFromGUI();
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult();
    }

}

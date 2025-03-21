package net.ovonsame.cas.elements.ModdedRecipe;

import net.mcreator.element.parts.Sound;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;

import net.ovonsame.cas.parts.Ingredient.IngredientList;
import net.ovonsame.cas.parts.Result.ResultList;
import net.ovonsame.cas.recipes.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ModdedRecipeMain extends ModElementGUI<ModdedRecipe> {
    private final JPanel pane1 = new JPanel(new BorderLayout());
    private final JPanel pane2 = new JPanel(new BorderLayout());
    private final JPanel pane3 = new JPanel(new BorderLayout());

    private final JComboBox<String> recipe_type = new JComboBox<>(ModdedRecipe.recipes);
    private final ResultList result = new ResultList(mcreator, this);
    private final IngredientList ingredient = new IngredientList(mcreator, this);

    private final Map<String, ModdedRecipeBase> recipes = new HashMap<>(
            Map.ofEntries(
                    Map.entry("cutting", new CuttingRecipe(mcreator)),
                    Map.entry("cooking", new CookingRecipe(mcreator))
            ));

    private ModdedRecipeBase recipe;

    public ModdedRecipeMain(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel type_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel recipe_panel = new JPanel(new CardLayout());

        recipe_type.setSelectedItem("cutting");
        recipe = recipes.get((String) recipe_type.getSelectedItem());

        type_panel.add(L10N.label("elementgui.modded_recipe.recipe_type"));
        type_panel.add(recipe_type);

        for (Map.Entry<String, ModdedRecipeBase> entry : recipes.entrySet()) {
            recipe_panel.add(entry.getValue(), entry.getKey());
        }

        CardLayout cardLayout = (CardLayout) recipe_panel.getLayout();
        cardLayout.show(recipe_panel, (String) recipe_type.getSelectedItem());

        recipe_type.addActionListener(e -> {
            cardLayout.show(recipe_panel, (String) recipe_type.getSelectedItem());
            recipe = recipes.get((String) recipe_type.getSelectedItem());
            recipe_panel.revalidate();
            recipe_panel.repaint();
        });

        pane1.add(type_panel, BorderLayout.WEST);
        pane1.add(recipe_panel, BorderLayout.CENTER);

        pane2.add(ingredient, BorderLayout.CENTER);
        pane3.add(result, BorderLayout.CENTER);

        updateUI();
        addPage(L10N.t("elementgui.modded_recipe.main_page"), pane1);
        addPage(L10N.t("elementgui.modded_recipe.results_page"), pane2);
        addPage(L10N.t("elementgui.modded_recipe.ingredients_page"), pane3);
    }


    @Override
    public void openInEditingMode(ModdedRecipe recipe) {
        recipe_type.setSelectedItem(recipe.recipe_type);
        ModdedRecipeBase selected = recipes.get(recipe.recipe_type);

        selected.list.setSelectedItem(recipe.list);
        selected.experience.setValue(recipe.experience);
        selected.process_time.setValue(recipe.processing_time);
        selected.sound.setSound(recipe.sound);

        result.removeAll();
        result.setEntries(recipe.result);

        ingredient.removeAll();
        ingredient.setEntries(recipe.ingredient);
    }

    @Override
    public ModdedRecipe getElementFromGUI() {
        ModdedRecipe recipe = new ModdedRecipe(modElement);

        recipe.recipe_type = (String) this.recipe_type.getSelectedItem();
        recipe.sound = (Sound) this.recipe.sound.getSound();
        recipe.experience = (int) this.recipe.experience.getValue();
        recipe.processing_time = (int) this.recipe.process_time.getValue();
        recipe.list = (String) this.recipe.list.getSelectedItem();
        recipe.result = this.result.getEntries();
        recipe.ingredient = this.ingredient.getEntries();
        return recipe;
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult();
    }
}
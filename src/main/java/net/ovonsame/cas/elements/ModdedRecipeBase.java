package net.ovonsame.cas.elements;

import net.mcreator.element.parts.Sound;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.AdaptiveGridLayout;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.SoundSelector;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.workspace.elements.ModElement;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class ModdedRecipeBase extends ModElementGUI<ModdedRecipe> {
    private final ValidationGroup page1group = new ValidationGroup();
    private final ValidationGroup page2group = new ValidationGroup();
    private AggregatedValidationResult validresult;

    private final CardLayout recipes_panel_layout = new CardLayout();
    private final JPanel recipes_panel = new JPanel(recipes_panel_layout);
    private final JComboBox<String> recipeType = new JComboBox<>(ModdedRecipe.recipes);

    private final JPanel recipe_panel = new JPanel(new AdaptiveGridLayout(-1, 2, 10, 2));
    private final JSpinner experience = new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
    private final JSpinner process_time = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));
    private final SoundSelector sound;
    private final JComboBox<String> list = new JComboBox<>(new String[]{});

    public final boolean hasSound, hasExperience, hasProcessTime, hasList;
    public final String id;

    public ModdedRecipeBase(MCreator mcreator, ModElement modElement, boolean editingMode,
                            boolean hasSound, boolean hasExperience, boolean hasProcessTime, boolean hasList, String id) {
        super(mcreator, modElement, editingMode);

        this.hasSound = hasSound;
        this.hasExperience = hasExperience;
        this.hasProcessTime = hasProcessTime;
        this.hasList = hasList;
        this.id = id;

        this.sound = hasSound ? new SoundSelector(mcreator) : null;

        this.initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        JPanel selectionPage = new JPanel(new BorderLayout(10, 10));
        JPanel typePanel = new JPanel(new AdaptiveGridLayout(-1, 2, 10, 2));
        typePanel.add(L10N.label("elementgui." + this.id + ".type"));
        typePanel.add(this.recipeType);

        selectionPage.add(PanelUtils.totalCenterInPanel(typePanel), BorderLayout.CENTER);
        addPage(selectionPage);

        JPanel recipePage = new JPanel(new BorderLayout(10, 10));
        recipe_panel.setOpaque(false);

        if (hasProcessTime) {
            recipe_panel.add(L10N.label("elementgui." + this.id + ".process_time"));
            recipe_panel.add(this.process_time);
        }
        if (hasExperience) {
            recipe_panel.add(L10N.label("elementgui." + this.id + ".experience"));
            recipe_panel.add(this.experience);
        }
        if (hasList) {
            recipe_panel.add(L10N.label("elementgui." + this.id + ".list"));
            recipe_panel.add(this.list);
        }
        if (hasSound && sound != null) {
            recipe_panel.add(L10N.label("elementgui." + this.id + ".sound"));
            recipe_panel.add(this.sound);
        }

        recipePage.add(PanelUtils.totalCenterInPanel(recipe_panel), BorderLayout.CENTER);
        addPage(recipePage);

        recipeType.addActionListener(e -> updateUIFields());
        updateUIFields();
    }

    private void updateUIFields() {
        String recipeTypeValue = (String) recipeType.getSelectedItem();
        if (recipeTypeValue != null) {
            recipes_panel_layout.show(recipes_panel, recipeTypeValue.toLowerCase(Locale.ENGLISH));
        }
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return page == 1 ? new AggregatedValidationResult(new ValidationGroup[]{this.page1group}) : validresult;
    }

    @Override
    public void openInEditingMode(ModdedRecipe recipe) {
        recipeType.setSelectedItem(recipe.recipeType);

        if (recipe.recipeType.equals(this.id)) {
            list.setSelectedItem(recipe.list);
            experience.setValue(recipe.experience);
            process_time.setValue(recipe.processing_time);
            if (hasSound && sound != null) sound.setSound(recipe.sound);
        }
    }

    @Override
    public ModdedRecipe getElementFromGUI() {
        ModdedRecipe recipe = new ModdedRecipe(modElement);
        recipe.recipeType = (String) Objects.requireNonNull(recipeType.getSelectedItem());

        if (recipe.recipeType.equals(this.id)) {
            recipe.sound = (hasSound && sound != null) ? (Sound) sound.getSound() : null;
            recipe.experience = hasExperience ? (int) experience.getValue() : 0;
            recipe.processing_time = hasProcessTime ? (int) process_time.getValue() : 0;
            recipe.list = hasList ? (String) list.getSelectedItem() : null;
        }
        return recipe;
    }
}

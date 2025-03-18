package net.ovonsame.cas.elements;

import net.mcreator.element.parts.Sound;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.SoundSelector;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.workspace.elements.ModElement;

import javax.swing.*;
import java.awt.*;

public class ModdedRecipeBase extends ModElementGUI<ModdedRecipe> {
    private final ValidationGroup page = new ValidationGroup();

    private final CardLayout recipes_panel_layout = new CardLayout();
    private final JPanel main_panel = new JPanel(recipes_panel_layout);

    private final JSpinner experience = new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
    private final JSpinner process_time = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));
    private final SoundSelector sound = new SoundSelector(mcreator);

    public final String[] values;
    private JComboBox<String> list = new JComboBox<>(new String[]{});

    public final boolean hasSound, hasExperience, hasProcessTime, hasList;
    public final String id;

    public ModdedRecipeBase(MCreator mcreator, ModElement modElement, boolean editingMode,
                            boolean hasSound, boolean hasExperience, boolean hasProcessTime, boolean hasList, String id, String[] values) {
        super(mcreator, modElement, editingMode);

        this.hasSound = hasSound;
        this.hasExperience = hasExperience;
        this.hasProcessTime = hasProcessTime;
        this.hasList = hasList;
        this.id = id;
        this.values = values;

        this.initGUI();
        super.finalizeGUI();

    }

    @Override
    protected void initGUI() {
        list = new JComboBox<>(values);
        main_panel.add(L10N.label("elementgui." + this.id + ".recipe"));

        if (hasProcessTime) {
            main_panel.add(L10N.label("elementgui." + this.id + ".process_time"));
            main_panel.add(this.process_time);
        }else{process_time.setVisible(false);}

        if (hasExperience) {
            main_panel.add(L10N.label("elementgui." + this.id + ".experience"));
            main_panel.add(this.experience);
        }else{experience.setVisible(false);}

        if (hasList) {
            main_panel.add(L10N.label("elementgui." + this.id + ".list"));
            main_panel.add(this.list);
        }else{list.setVisible(false);}

        if (hasSound) {
            main_panel.add(L10N.label("elementgui." + this.id + ".sound"));
            main_panel.add(this.sound);
        }else{sound.setVisible(false);}

        updateUIFields();
        addPage(main_panel);
    }

    private void updateUIFields() {
        recipes_panel_layout.show(main_panel, this.id);
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        if(page==0) {
            return new AggregatedValidationResult(new ValidationGroup[]{this.page});
        }
        return new AggregatedValidationResult();
    }

    @Override
    public void openInEditingMode(ModdedRecipe recipe) {
        list.setSelectedItem(recipe.list);
        experience.setValue(recipe.experience);
        process_time.setValue(recipe.processing_time);
        if (hasSound && sound != null) sound.setSound(recipe.sound);
    }

    @Override
    public ModdedRecipe getElementFromGUI() {
        ModdedRecipe recipe = new ModdedRecipe(modElement);
        recipe.sound = (Sound) sound.getSound();
        recipe.experience = (int) experience.getValue();
        recipe.processing_time = (int) process_time.getValue();
        recipe.list = (String) list.getSelectedItem();
        return recipe;
    }
}

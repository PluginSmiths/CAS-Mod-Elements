package net.ovonsame.cas.elements;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.ImagePanel;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.init.UIRES;
import net.mcreator.ui.minecraft.SoundSelector;

import javax.swing.*;
import java.awt.*;

public class ModdedRecipeBase extends JPanel {
    public final JSpinner experience;
    public final JSpinner process_time;
    public final SoundSelector sound;

    public final String[] values;
    public final JComboBox<String> list;

    public final boolean hasSound, hasExperience, hasProcessTime, hasList;
    public final String id;

    public final ImagePanel image_panel;

    public ModdedRecipeBase(MCreator mcreator, boolean hasSound, boolean hasExperience, boolean hasProcessTime, boolean hasList, String id, String[] values) {

        this.hasSound = hasSound;
        this.hasExperience = hasExperience;
        this.hasProcessTime = hasProcessTime;
        this.hasList = hasList;
        this.id = id;
        this.values = values;

        this.process_time = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));
        this.sound = new SoundSelector(mcreator);
        this.experience = new JSpinner(new SpinnerNumberModel(1, 1, 500, 1));
        this.list = new JComboBox<>(values);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        if (hasProcessTime) {
            add(L10N.label("elementgui.modded_recipe." + id + ".process_time"), gbc);
            gbc.gridx = 1;
            add(process_time, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
        }else{process_time.setVisible(false);}

        if (hasExperience) {
            add(L10N.label("elementgui.modded_recipe." + id + ".experience"), gbc);
            gbc.gridx = 1;
            add(experience, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
        }else{experience.setVisible(false);}

        if (hasList) {
            add(L10N.label("elementgui.modded_recipe." + id + ".list"), gbc);
            gbc.gridx = 1;
            add(list, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
        }else{list.setVisible(false);}

        if (hasSound) {
            add(L10N.label("elementgui.modded_recipe." + id + ".sound"), gbc);
            gbc.gridx = 1;
            add(sound, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
        }else{sound.setVisible(false);}

        image_panel = new ImagePanel(UIRES.get("recipe." + this.id).getImage());
        image_panel.fitToImage();
        image_panel.setLayout(new GridBagLayout());

        add(image_panel, gbc);
        gbc.gridx = -5;
        gbc.gridy = 0;

        setPreferredSize(new Dimension(256, 256));
    }

    @Override public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        experience.setEnabled(enabled);
        process_time.setEnabled(enabled);
        list.setEnabled(enabled);
        sound.setEnabled(enabled);
    }
}

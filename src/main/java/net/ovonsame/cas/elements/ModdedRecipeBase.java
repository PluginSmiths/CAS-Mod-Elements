package net.ovonsame.cas.elements;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.minecraft.SoundSelector;
import net.mcreator.ui.validation.ValidationGroup;

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

        ValidationGroup page = new ValidationGroup();
        JPanel main_panel = new JPanel(new CardLayout());

        if (hasProcessTime) {
            main_panel.add(L10N.label("elementgui." + this.id + ".process_time"));
            main_panel.add(this.process_time);
            process_time.setBounds(48, 32, 32, 32);
            process_time.setVisible(true);
        }else{process_time.setVisible(false);}

        if (hasExperience) {
            experience.setBounds(48, 32+(32)+(8), 32, 32);
            experience.setVisible(true);
            main_panel.add(L10N.label("elementgui." + this.id + ".experience"));
            main_panel.add(this.experience);
        }else{experience.setVisible(false);}

        if (hasList) {
            main_panel.add(L10N.label("elementgui." + this.id + ".list"));
            main_panel.add(this.list);
            list.setBounds(48, 32+(32*2)+(8*2), 32, 32);
            list.setVisible(true);
        }else{list.setVisible(false);}

        if (hasSound) {
            main_panel.add(L10N.label("elementgui." + this.id + ".sound"));
            main_panel.add(this.sound);
            sound.setBounds(48, 32+(32*3)+(8*3), 32, 32);
            sound.setVisible(true);
        }else{sound.setVisible(false);}

        setPreferredSize(new Dimension(256, 256));
        this.add(main_panel);
    }

    @Override public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        experience.setEnabled(enabled);
        process_time.setEnabled(enabled);
        list.setEnabled(enabled);
        sound.setEnabled(enabled);
    }
}

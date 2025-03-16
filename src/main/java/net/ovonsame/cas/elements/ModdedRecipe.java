package net.ovonsame.cas.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.element.parts.Sound;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;

import javax.swing.*;
import java.lang.*;

public class ModdedRecipe extends GeneratableElement {
    public String recipeType;

    //common fields
    public int count;

    //cutting fields
    public MItemBlock cuttinginput;
    public MItemBlock cuttingoutput;
    public int cuttingchance;
    public String cuttingaction;
    public Sound cuttingsound;

    //cooking fields
    public MItemBlock cookingoutput;
    @ModElementReference public MItemBlock[] cookinginput;
    public int cookingtime;
    public int cookingxp;
    public String cookingbook;

    @ModElementReference public MItemBlock[] result;
    @ModElementReference public MItemBlock[] ingredients;

    public int amount_ingredients;
    public int amount_result;

    public float chance;
    public int processing_time;
    public float experience;
    @ModElementReference public Sound sound;

    @ModElementReference public JComboBox<String> list = new JComboBox<>(new String[]{});

    public ModdedRecipe(ModElement element) {
        super(element);
    }

    @Override public void setModElement(ModElement element) {
        super.setModElement(element);
    }

}
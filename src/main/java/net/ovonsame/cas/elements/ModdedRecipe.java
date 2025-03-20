package net.ovonsame.cas.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.Sound;
import net.mcreator.workspace.elements.ModElement;
import net.ovonsame.cas.parts.RecipeResultEntry;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class ModdedRecipe extends GeneratableElement {
    public static final String[] recipes = new String[]{"cutting", "cooking", "compacting", "crushing", "deploying", "emptying", "filling", "haunting", "item_application",
            "milling", "mixing", "pressing", "sandpaper_polishing", "splashing"};
    public String recipe_type;

    public int processing_time;
    public int experience;
    public Sound sound;

    public String list;

    public List<RecipeResultEntry> result = new ArrayList<>();

    public ModdedRecipe(ModElement element) {
        super(element);
    }

    @Override public void setModElement(ModElement element) {
        super.setModElement(element);
    }
}
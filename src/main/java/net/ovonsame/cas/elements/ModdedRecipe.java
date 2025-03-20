package net.ovonsame.cas.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.element.parts.Sound;
import net.mcreator.workspace.elements.ModElement;

import java.lang.*;

public class ModdedRecipe extends GeneratableElement {
    public static final String[] recipes = new String[]{"cutting", "cooking", "compacting", "crushing", "deploying", "emptying", "filling", "haunting", "item_application",
            "milling", "mixing", "pressing", "sandpaper_polishing", "splashing", "cutting", "cooking"};
    public String recipe_type;

    public int processing_time;
    public int experience;
    public Sound sound;

    public String list;

    public ModdedRecipe(ModElement element) {
        super(element);
    }

    @Override public void setModElement(ModElement element) {
        super.setModElement(element);
    }

    public static class RecipeResult {
        public MItemBlock item;
        public int amount;
        public float chance;

        public RecipeResult() {
            this.amount = 0;
            this.chance = 0f;
            this.item = null;
        }
    }
}
package net.ovonsame.cas.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.element.parts.Sound;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;

import java.lang.*;

public class ModdedRecipe extends GeneratableElement {
    public static final String[] recipes = new String[]{"cutting", "cooking", "compacting", "crushing", "deploying", "emptying", "filling", "haunting", "item_application",
            "milling", "mixing", "pressing", "sandpaper_polishing", "splashing", "cutting", "cooking"};
    public String recipeType;

    public int processing_time;
    public float experience;
    @ModElementReference public Sound sound;

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
            this.amount = 1;
            this.chance = 0.5f;
            this.item = null;
        }
    }

    public static class RecipeIngredient {
        public MItemBlock item;
        public int amount;

        public RecipeIngredient() {
            this.amount = 1;
            this.item = null;
        }
    }
}
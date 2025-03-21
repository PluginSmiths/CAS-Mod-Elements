package net.ovonsame.cas.elements.ModdedRecipe;

import jakarta.annotation.Nullable;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.IWorkspaceDependent;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.element.parts.Sound;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;

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

    public List<Result> result = new ArrayList<>();
    public List<Ingredient> ingredient = new ArrayList<>();

    public ModdedRecipe(ModElement element) {
        super(element);
    }

    @Override public void setModElement(ModElement element) {
        super.setModElement(element);
    }

    public static class Result implements IWorkspaceDependent {

        public float chance;
        public int amount;
        @ModElementReference public MItemBlock item;

        @Nullable
        transient Workspace workspace;

        @Override public void setWorkspace(@Nullable Workspace workspace) {
            this.workspace = workspace;
        }

        @Override public @Nullable Workspace getWorkspace() {
            return workspace;
        }
    }

    public static class Ingredient implements IWorkspaceDependent {

        public int amount;
        @ModElementReference public MItemBlock item;

        @Nullable
        transient Workspace workspace;

        @Override public void setWorkspace(@Nullable Workspace workspace) {
            this.workspace = workspace;
        }

        @Override public @Nullable Workspace getWorkspace() {
            return workspace;
        }

        public String toString() {
            return "{" +
                    "\"item=\"" + item +
                    ", item=" + item +
                    '}';
        }
    }
}
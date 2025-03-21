package net.ovonsame.cas.elements.Tip;

import jakarta.annotation.Nullable;
import net.mcreator.element.GeneratableElement;
import net.mcreator.workspace.elements.ModElement;

public class Tip extends GeneratableElement {
    public String text;
    @Nullable public Integer time = 5000;

    public Tip(ModElement element) {
        super(element);
    }

    @Override
    public void setModElement(ModElement element) {
        super.setModElement(element);
    }
}

package net.ovonsame.cas.elements.Tip;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;

import javax.swing.*;
import java.awt.*;

public class TipMain extends ModElementGUI<Tip> {
    private final JTextField text = new JTextField();
    private final JSpinner time = new JSpinner(new SpinnerNumberModel(5000, 0, 100000, 1000));
    private final JPanel pane = new JPanel(new BorderLayout(10, 10));

    public TipMain(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        initGUI();
        super.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel();

        panel.add(L10N.label("elementgui.tip.text"));
        panel.add(text);

        panel.add(L10N.label("elementgui.tip.time"));
        panel.add(time);

        pane.add(PanelUtils.totalCenterInPanel(panel));

        updateUI();
        addPage(pane);
    }

    @Override
    public void openInEditingMode(Tip tip) {
        text.setText(tip.text);
        time.setValue(tip.time);
    }

    @Override
    public Tip getElementFromGUI() {
        Tip tip = new Tip(modElement);
        tip.text = text.getText();
        tip.time = (Integer) time.getValue();
        return tip;
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult();
    }
}

package net.ovonsame.config.ui.modgui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.ovonsame.config.blockly.BlocklyToConfig;
import net.ovonsame.config.blockly.data.PluginToolboxType;
import net.ovonsame.config.element.types.Config;
import net.ovonsame.config.ui.blockly.PluginBlocklyEditorType;
import net.ovonsame.config.ui.validation.validators.ConfigFileNameValidator;
import net.mcreator.blockly.BlocklyCompileNote;
import net.mcreator.blockly.data.BlocklyLoader;
import net.mcreator.blockly.data.ToolboxBlock;
import net.mcreator.generator.blockly.BlocklyBlockCodeGenerator;
import net.mcreator.generator.template.TemplateGenerator;
import net.mcreator.generator.template.TemplateGeneratorException;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.blockly.BlocklyAggregatedValidationResult;
import net.mcreator.ui.blockly.BlocklyEditorToolbar;
import net.mcreator.ui.blockly.BlocklyPanel;
import net.mcreator.ui.blockly.CompileNotesPanel;
import net.mcreator.ui.component.util.ComponentUtils;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.modgui.IBlocklyPanelHolder;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.ValidationGroup;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.elements.VariableElement;

public class ConfigGUI extends ModElementGUI<Config> implements IBlocklyPanelHolder {
   private final CompileNotesPanel compileNotesPanel = new CompileNotesPanel();
   private final List<BlocklyChangedListener> blocklyChangedListeners = new ArrayList();
   private final VTextField configFileName = new VTextField(25);
   private final JComboBox<String> fileExtension = new JComboBox(new String[]{"Json", "Toml", "Yaml"});
   private final ValidationGroup validationGroup = new ValidationGroup();
   private BlocklyPanel blocklyPanel;
   private Map<String, ToolboxBlock> externalBlocks;

   public ConfigGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
      super(mcreator, modElement, editingMode);
      this.initGUI();
      super.finalizeGUI();
   }

   public void addBlocklyChangedListener(BlocklyChangedListener blocklyChangedListener) {
      this.blocklyChangedListeners.add(blocklyChangedListener);
   }

   private synchronized void regenerateConfigs() {
      BlocklyBlockCodeGenerator blocklyBlockCodeGenerator = new BlocklyBlockCodeGenerator(this.externalBlocks, this.mcreator.getGeneratorStats().getBlocklyBlocks(PluginBlocklyEditorType.CONFIG));

      BlocklyToConfig blocklyToJava;
      try {
         blocklyToJava = new BlocklyToConfig(this.mcreator.getWorkspace(), this.modElement, this.blocklyPanel.getXML(), (TemplateGenerator)null, blocklyBlockCodeGenerator);
      } catch (TemplateGeneratorException var4) {
         return;
      }

      List<BlocklyCompileNote> compileNotesArrayList = blocklyToJava.getCompileNotes();
      SwingUtilities.invokeLater(() -> {
         this.compileNotesPanel.updateCompileNotes(compileNotesArrayList);
         this.blocklyChangedListeners.forEach((listener) -> {
            listener.blocklyChanged(this.blocklyPanel);
         });
      });
   }

   protected void initGUI() {
      ComponentUtils.deriveFont(this.configFileName, 16.0F);
      ComponentUtils.deriveFont(this.fileExtension, 16.0F);
      JPanel topPanel = new JPanel(new GridLayout(2, 2, 2, 5));
      topPanel.setOpaque(false);
      topPanel.add(L10N.label("elementgui.config.config_file_name", new Object[0]));
      topPanel.add(this.configFileName);
      topPanel.add(L10N.label("elementgui.config.file_extension", new Object[0]));
      topPanel.add(this.fileExtension);
      topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.config.config_file.title", new Object[0]), 4, 0, this.getFont(), Theme.current().getForegroundColor()));
      this.externalBlocks = BlocklyLoader.INSTANCE.getBlockLoader(PluginBlocklyEditorType.CONFIG).getDefinedBlocks();
      this.blocklyPanel = new BlocklyPanel(this.mcreator, PluginBlocklyEditorType.CONFIG);
      this.blocklyPanel.addTaskToRunAfterLoaded(() -> {
         BlocklyLoader.INSTANCE.getBlockLoader(PluginBlocklyEditorType.CONFIG).loadBlocksAndCategoriesInPanel(this.blocklyPanel, PluginToolboxType.CONFIG);
         Iterator var1 = this.mcreator.getWorkspace().getVariableElements().iterator();

         while(var1.hasNext()) {
            VariableElement variable = (VariableElement)var1.next();
            this.blocklyPanel.addGlobalVariable(variable.getName(), variable.getType().getBlocklyVariableType());
         }

         this.blocklyPanel.getJSBridge().setJavaScriptEventListener(() -> {
            (new Thread(this::regenerateConfigs, "ConfigRegenerate")).start();
         });
         if (!this.isEditingMode()) {
            this.blocklyPanel.setXML("<xml xmlns=\"https://developers.google.com/blockly/xml\"><block type=\"config_start\" deletable=\"false\" x=\"40\" y=\"40\"></block></xml>");
         }

      });
      this.blocklyPanel.setPreferredSize(new Dimension(600, 440));
      JPanel args = (JPanel)PanelUtils.centerAndSouthElement(PanelUtils.northAndCenterElement(new BlocklyEditorToolbar(this.mcreator, PluginBlocklyEditorType.CONFIG, this.blocklyPanel), this.blocklyPanel), this.compileNotesPanel);
      args.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1), L10N.t("elementgui.config.config_builder.title", new Object[0]), 4, 0, this.getFont(), Theme.current().getForegroundColor()));
      args.setOpaque(false);
      this.configFileName.setValidator(new ConfigFileNameValidator(this.configFileName, this.configFileName.getName()));
      this.configFileName.enableRealtimeValidation();
      this.validationGroup.addValidationElement(this.configFileName);
      this.configFileName.setText(this.modElement.getWorkspaceSettings().getModID() + "_configs");
      this.addPage(PanelUtils.northAndCenterElement(topPanel, args));
   }

   protected AggregatedValidationResult validatePage(int page) {
      return new AggregatedValidationResult(new ValidationGroup[]{this.validationGroup, new BlocklyAggregatedValidationResult(this.compileNotesPanel.getCompileNotes())});
   }

   public void openInEditingMode(Config config) {
      this.configFileName.setText(config.configFileName);
      this.fileExtension.setSelectedItem(config.fileExtension);
      this.blocklyPanel.setXML(config.configsxml);
      this.blocklyPanel.addTaskToRunAfterLoaded(() -> {
         this.blocklyPanel.clearWorkspace();
         this.blocklyPanel.setXML(config.configsxml);
         this.regenerateConfigs();
      });
   }

   public Config getElementFromGUI() {
      Config config = new Config(this.modElement);
      config.configFileName = this.configFileName.getText();
      config.fileExtension = (String)this.fileExtension.getSelectedItem();
      config.configsxml = this.blocklyPanel.getXML();
      return config;
   }

   public Set<BlocklyPanel> getBlocklyPanels() {
      return Set.of(this.blocklyPanel);
   }
}

package net.ovonsame.config.element.types;

import java.util.Locale;
import net.ovonsame.config.blockly.BlocklyToConfig;
import net.ovonsame.config.ui.blockly.PluginBlocklyEditorType;
import net.mcreator.blockly.data.BlocklyLoader;
import net.mcreator.blockly.data.BlocklyXML;
import net.mcreator.element.GeneratableElement;
import net.mcreator.generator.blockly.BlocklyBlockCodeGenerator;
import net.mcreator.generator.template.IAdditionalTemplateDataProvider;
import net.mcreator.workspace.elements.ModElement;

public class Config extends GeneratableElement {
   @BlocklyXML("configs")
   public String configsxml;
   public String configFileName;
   public String fileExtension;

   public Config(ModElement element) {
      super(element);
   }

   @jakarta.annotation.Nullable
   public IAdditionalTemplateDataProvider getAdditionalTemplateData() {
      return (additionalData) -> {
         BlocklyBlockCodeGenerator blocklyBlockCodeGenerator = (new BlocklyBlockCodeGenerator(BlocklyLoader.INSTANCE.getBlockLoader(PluginBlocklyEditorType.CONFIG).getDefinedBlocks(), this.getModElement().getGenerator().getGeneratorStats().getBlocklyBlocks(PluginBlocklyEditorType.CONFIG), this.getModElement().getGenerator().getTemplateGeneratorFromName(PluginBlocklyEditorType.CONFIG.registryName()), additionalData)).setTemplateExtension(this.getModElement().getGeneratorConfiguration().getGeneratorFlavor().getBaseLanguage().name().toLowerCase(Locale.ENGLISH));
         BlocklyToConfig blocklyToJava = new BlocklyToConfig(this.getModElement().getWorkspace(), this.getModElement(), this.configsxml, this.getModElement().getGenerator().getTemplateGeneratorFromName(PluginBlocklyEditorType.CONFIG.registryName()), blocklyBlockCodeGenerator);
         additionalData.put("code", blocklyToJava.getGeneratedCode());
      };
   }

   public boolean hasJsonExtension() {
      return this.fileExtension.equals("Json");
   }

   public boolean hasTomlExtension() {
      return this.fileExtension.equals("Toml");
   }

   public boolean hasYamlExtension() {
      return this.fileExtension.equals("Yaml");
   }
}

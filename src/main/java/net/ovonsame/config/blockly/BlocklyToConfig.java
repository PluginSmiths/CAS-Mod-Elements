package net.ovonsame.config.blockly;

import net.ovonsame.config.blockly.blocks.ComponentFromLocalizedTextBlock;
import net.ovonsame.config.blockly.blocks.ComponentFromTextBlock;
import net.ovonsame.config.ui.blockly.PluginBlocklyEditorType;
import net.mcreator.blockly.IBlockGenerator;
import net.mcreator.blockly.java.BlocklyToJava;
import net.mcreator.generator.blockly.BlocklyBlockCodeGenerator;
import net.mcreator.generator.blockly.OutputBlockCodeGenerator;
import net.mcreator.generator.blockly.ProceduralBlockCodeGenerator;
import net.mcreator.generator.template.TemplateGenerator;
import net.mcreator.generator.template.TemplateGeneratorException;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.elements.ModElement;

public class BlocklyToConfig extends BlocklyToJava {
   public BlocklyToConfig(Workspace workspace, ModElement parent, String sourceXML, TemplateGenerator templateGenerator, BlocklyBlockCodeGenerator blocklyBlockCodeGenerator) throws TemplateGeneratorException {
      super(workspace, parent, PluginBlocklyEditorType.CONFIG, sourceXML, templateGenerator, new IBlockGenerator[]{new ProceduralBlockCodeGenerator(blocklyBlockCodeGenerator), new OutputBlockCodeGenerator(blocklyBlockCodeGenerator), new ComponentFromTextBlock(), new ComponentFromLocalizedTextBlock()});
   }
}

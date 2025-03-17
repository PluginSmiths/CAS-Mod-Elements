package net.ovonsame.cas.config.blockly.blocks;

import net.mcreator.blockly.BlocklyCompileNote;
import net.mcreator.blockly.BlocklyToCode;
import net.mcreator.blockly.IBlockGenerator;
import net.mcreator.blockly.BlocklyCompileNote.Type;
import net.mcreator.blockly.IBlockGenerator.BlockType;
import net.mcreator.generator.template.TemplateGeneratorException;
import net.mcreator.ui.init.L10N;
import net.mcreator.util.XMLUtil;
import org.w3c.dom.Element;

public class ComponentFromTextBlock implements IBlockGenerator {
   public void generateBlock(BlocklyToCode master, Element block) throws TemplateGeneratorException {
      Element text = XMLUtil.getFirstChildrenWithName(block, new String[]{"value"});
      if (text != null) {
         master.append("Component.literal(");
         master.processOutputBlock(text);
         master.append(")");
      } else {
         master.addCompileNote(new BlocklyCompileNote(Type.ERROR, L10N.t("blockly.errors.empty_text_component", new Object[0])));
      }

   }

   public String[] getSupportedBlocks() {
      return new String[]{"component_from_text"};
   }

   public BlockType getBlockType() {
      return BlockType.OUTPUT;
   }
}

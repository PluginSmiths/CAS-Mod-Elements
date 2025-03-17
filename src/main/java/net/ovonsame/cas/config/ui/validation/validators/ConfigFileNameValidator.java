package net.ovonsame.cas.config.ui.validation.validators;

import java.util.List;
import javax.swing.JTextField;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.validation.Validator;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.util.StringUtils;

public class ConfigFileNameValidator implements Validator {
   protected final String name;
   private final JTextField holder;

   public ConfigFileNameValidator(VTextField holder, String name) {
      this.name = name;
      this.holder = holder;
   }

   public ValidationResult validate() {
      boolean allowEmpty = false;
      List<Character> validChars = List.of('_', '-', '/');
      String text = this.holder.getText();
      if (text.isEmpty() && !allowEmpty) {
         return new ValidationResult(ValidationResultType.ERROR, L10N.t("validators.config.file_name.empty", new Object[]{this.name}));
      } else {
         char[] chars = text.toCharArray();
         boolean valid = true;
         int id = 0;
         char[] var7 = chars;
         int var8 = chars.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            char c = var7[var9];
            if (id == 0 && (c >= '0' && c <= '9' || validChars.contains(c))) {
               valid = false;
               break;
            }

            if (!isLCLetterOrDigit(c) && !validChars.contains(c)) {
               valid = false;
               break;
            }

            ++id;
         }

         return !valid ? new ValidationResult(ValidationResultType.ERROR, L10N.t("validators.config.file_name.invalid", new Object[]{this.name, validChars.toString()})) : ValidationResult.PASSED;
      }
   }

   public static boolean isLCLetterOrDigit(char c) {
      return StringUtils.isLowercaseLetter(c) || StringUtils.isUppercaseLetter(c) || c >= '0' && c <= '9';
   }
}

package VendingApplication;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextFieldSkin;

public class PasswordFieldSkin extends TextFieldSkin {

    public PasswordFieldSkin(TextField textField) {
        super(textField);
    }

    public String maskText(String text) {
        if (getSkinnable() instanceof PasswordField) {
            int len = text.length();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < len; i++) {
                //builder.append((char)0x2731);//heavy/bold asterisk

                builder.append((char)0x2217);//medium asterisk
                //builder.append("*");
            }
            return builder.toString();
        } else {
            return text;
        }
    }


}



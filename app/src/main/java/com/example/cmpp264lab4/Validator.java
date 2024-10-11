package com.example.cmpp264lab4;

import android.widget.EditText;

// validator class with custom methods to validate entry fields.
public class Validator {
    public static boolean validateTextNonEmpty(EditText editText){
        String value = String.valueOf(editText.getText());
        return !value.trim().isEmpty();
    }
    public static boolean validateNonEmptyPositiveInteger(EditText editText){
        String value = String.valueOf(editText.getText());
        try {
            int intValue = Integer.parseInt(value);
            return intValue > 0;
        } catch (Exception e){
           return false;
        }
    }
}

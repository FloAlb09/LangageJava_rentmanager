package com.epf.rentmanager.validator;

import javax.swing.*;

public class Validator {

    public static int showMessageDialog(Object message) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Information");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        dialog.dispose();
        Object selectedValue = pane.getValue();
        if (selectedValue == null)
            return JOptionPane.CLOSED_OPTION;

        if (selectedValue instanceof Integer)
            return ((Integer) selectedValue).intValue();
        return JOptionPane.CLOSED_OPTION;
    }
}

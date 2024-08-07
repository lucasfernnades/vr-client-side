package org.vr.software;

import org.vr.software.ui.MenuFrame;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuFrame menuFrame = new MenuFrame();
            menuFrame.setVisible(true);
        });
    }
}
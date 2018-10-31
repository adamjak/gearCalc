/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.adamjak.tomas.gearcalc.gui;

import java.awt.HeadlessException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;

/**
 *
 * @author tadamjak
 */
public class MainWindow extends JFrame {
    
    private ResourceBundle messages;

    public MainWindow() throws HeadlessException {
        this("en", "EN");
    }

    public MainWindow(String language, String country) {
        Locale currentLocale;

        currentLocale = new Locale(language, country);

        this.messages = ResourceBundle.getBundle("locale/MessagesBundle", currentLocale);
        this.init();
    }
    
    public void init() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setTitle(this.messages.getString("mainWindow.title"));
    }
    
}

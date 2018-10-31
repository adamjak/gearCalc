/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.adamjak.tomas.gearcalc;

import java.util.Locale;
import java.util.ResourceBundle;
import net.adamjak.tomas.gearcalc.gui.MainWindow;

/**
 *
 * @author tadamjak
 */
public class Main {
    public static void main(String[] args) {
        
        String language;
        String country;

        if (args.length != 2) {
            language = new String("en");
            country = new String("US");
        } else {
            language = new String(args[0]);
            country = new String(args[1]);
        }
        
        MainWindow mw = new MainWindow(language, country);
        mw.setVisible(true);
    }
}

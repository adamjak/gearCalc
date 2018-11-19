/*
 * Copyright (c) 2018, Tomas Adamjak <thomas@adamjak.net>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     - Neither the name of Tomas Adamjak <thomas@adamjak.net>,  nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.adamjak.tomas.gearcalc.gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.adamjak.tomas.gearcalc.Langs;

/**
 *
 * @author tadamjak
 */
public class MainWindow extends JFrame {

    private static final int DEFAULT_WINDOW_HEIGHT = 600;
    private static final int DEFAULT_WINDOW_WIDTH = 1000;
    private ResourceBundle messages;

    public MainWindow() throws HeadlessException {
        this(Langs.EN_US);
    }

    public MainWindow(Langs lang) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        Locale currentLocale;

        currentLocale = new Locale(lang.getLang(), lang.getCountry());

        this.messages = ResourceBundle.getBundle("locale/MessagesBundle", currentLocale);
        this.init();
    }

    public final void init() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        this.setTitle(this.messages.getString("mainWindow.title"));

        this.add(this.createMainPanel());
    }

    public JPanel createMainPanel() {

        JPanel mainPanel = new JPanel();
        BoxLayout bl = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);

        mainPanel.setLayout(bl);

        GearsGraph gearsGraph = new GearsGraph();

        mainPanel.add(gearsGraph);

        mainPanel.add(this.bottomMenu());

        return mainPanel;
    }

    public JPanel bottomMenu() {
        JPanel bottomMenu = new JPanel();
        BoxLayout bl = new BoxLayout(bottomMenu, BoxLayout.X_AXIS);
        bottomMenu.setLayout(bl);

        JButton addGear = new JButton(this.messages.getString("mainWindow.bottomMenu.addGear"));
        addGear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddGearDialog();
            }
        });
        bottomMenu.add(addGear);

        JButton removeGear = new JButton(this.messages.getString("mainWindow.bottomMenu.removeGear"));
        bottomMenu.add(removeGear);

        return bottomMenu;
    }

    private void showAddGearDialog() {
        String inputString = JOptionPane.showInputDialog(
                this,
                this.messages.getString("dialog.addGear.message"),
                this.messages.getString("dialog.addGear.title"),
                JOptionPane.QUESTION_MESSAGE);

        if (inputString == null || inputString.isEmpty()) {
            return;
        }

        try {
            Integer gear = Integer.valueOf(inputString);
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getStackTrace());
            JOptionPane.showMessageDialog(this, this.messages.getString("dialog.addGear.error"), null, JOptionPane.ERROR_MESSAGE);
        }
    }

}

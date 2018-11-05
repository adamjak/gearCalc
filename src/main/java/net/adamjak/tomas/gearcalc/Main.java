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
package net.adamjak.tomas.gearcalc;

import java.awt.GridLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.adamjak.tomas.gearcalc.gui.MainWindow;

/**
 *
 * @author tadamjak
 */
public class Main {

    private static final String USER_HOME = System.getProperty("user.home");
    private static final String APP_NAME = "GearCalc";
    private static final String CONF_DIR = USER_HOME + File.separator + ".config" + File.separator + APP_NAME;
    private static final String LANG_FILE = CONF_DIR + File.separator + "lang";
    private static final Langs DEFAULT_LANG = Langs.EN_US;

    public static void main(String[] args) {

        Langs lang = null;

        if (isLangFile()) {
            Optional<Langs> fileLang = getLang();
            if (fileLang.isPresent()) {
                lang = fileLang.get();
            }
        }

        if (lang == null) {
            GridLayout gridLayout = new GridLayout(0, 2);
            JPanel panel = new JPanel(gridLayout);
            panel.add(new JLabel("Select language: "));
            JComboBox<String> jcbLangSelector = new JComboBox<>(Langs.getAllLangNames());
            panel.add(jcbLangSelector);
            panel.add(new JLabel("Don't ask me next time: "));
            JCheckBox jcbDontAskNextTime = new JCheckBox();
            panel.add(jcbDontAskNextTime);

            int selectLangDialog = JOptionPane.showConfirmDialog(null, panel, "Language", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (selectLangDialog == JOptionPane.OK_OPTION) {
                Optional<Langs> langByLangName = Langs.getLangByLangName((String) jcbLangSelector.getSelectedItem());
                if (langByLangName.isPresent()) {
                    lang = langByLangName.get();
                    if (jcbDontAskNextTime.isSelected()) {
                        setLangFile(lang);
                    }
                } else {
                    lang = DEFAULT_LANG;
                }
            } else {
                System.exit(0);
            }
        }

        MainWindow mw = new MainWindow(lang);
        mw.setVisible(true);
    }

    private static boolean isLangFile() {
        File f = new File(LANG_FILE);
        return (f.exists() && f.canRead() && f.isFile());
    }

    private static Optional<Langs> getLang() {
        File fileToRead = new File(LANG_FILE);
        List<String> fileLines;
        try {
            fileLines = Files.readAllLines(fileToRead.toPath());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return Optional.empty();
        }
        if (fileLines.size() == 2) {
            for (Langs lang : Langs.values()) {
                if (lang.getLang().equals(fileLines.get(0).trim()) && lang.getCountry().equals(fileLines.get(1).trim())) {
                    return Optional.of(lang);
                }
            }
        }

        return Optional.empty();
    }

    private static void setLangFile(Langs lang) {
        File confDir = new File(CONF_DIR);
        if (confDir.exists() == false) {
            confDir.mkdir();
        }

        File langFile = new File(LANG_FILE);
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(langFile))) {
                String content = lang.getLang() + System.lineSeparator() + lang.getCountry();
                bw.write(content);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "File can not be created.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

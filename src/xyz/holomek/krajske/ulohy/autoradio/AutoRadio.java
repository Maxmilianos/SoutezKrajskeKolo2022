package xyz.holomek.krajske.ulohy.autoradio;

import javax.swing.*;
import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;

public class AutoRadio {

    // jedna se o list, ve kterem uchovavam hudbu a slozky
    public ArrayList<Music> musics = new ArrayList<>();

    // zahajovaci metoda, vyuzita k startu teto ulohy
    public void start() {
        // prislo me lepsi napad to udelat pres vyber souboru, preci jen to je lepsi..
        System.out.println("Vyberte prosím složku:");
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(new JFrame());
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("Nezvolena slozka");
            return;
        }

        // kdyby nahodou tam je kontrola directory, prestoze mam directoriesonly.
        File selectedFile = fileChooser.getSelectedFile();
        if (!selectedFile.isDirectory()) {
            System.out.println("Zvolil jste soubor, ne slozku..");
            return;
        }
        System.out.println("Slozka: " + selectedFile.getAbsolutePath());
        System.out.println("Vybiram vsechny udaje z ni");
        addAllFiles(selectedFile);
        System.out.println("Hudba:");
        for (Music music : musics) {
            System.out.println(music.oldName + " -> " + music.getNewName());
        }
    }

    // metoda na vyber vsech files a dani je do listu
    public void addAllFiles(File directory) {
        if (directory == null) System.out.println("Nebyla zvolena slozka.");
        if (!directory.isDirectory()) System.out.println("Nebyla zvolena slozka. Nejspis jste zvolil soubor.");
        for (File sub : directory.listFiles()) {
            if (sub.isDirectory()) {
                addAllFiles(sub);
            }
            // jinak else protoze i directory chteji prejmenovat
            musics.add(new Music(sub.getAbsolutePath(), sub.getName()));
        }
    }

    // trida pro uchovani dulezitych udaju
    public class Music {

        // klasicke promenne, ktere vyuzivam k uchovani dat
        public String oldName, oldFileName;

        public Music(String oldName, String oldFileName) {
            this.oldName = oldName;
            this.oldFileName = oldFileName;
        }

        // metoda pro ziskani noveho jmena
        public String getNewName() {
            String name = oldFileName;
            // nevim zda tam chteji vsechno, ale nevadi
            /*if (name.contains("\"")) {
                String[] split = name.split("\"");
                name = split[split.length - 1];
                System.out.println("Name je : " + name);
            }*/
            return Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }
    }

}

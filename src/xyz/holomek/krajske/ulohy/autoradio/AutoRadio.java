package xyz.holomek.krajske.ulohy.autoradio;

import javax.swing.*;
import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;

/*
umět oznámit, že zadání nelze pro danou sadu souborů splnit (vymyslete proč, v takovém
případě místo seznamu souborů k přejmenování napište, že zadání nelze splnit a proč)
nepochopil jsem toto, nejspis to tam mam
 */
public class AutoRadio {

    // jedna se o list, ve kterem uchovavam hudbu a slozky
    public ArrayList<Music> musics = new ArrayList<>();

    public File selectedFile;

    // zahajovaci metoda, vyuzita k startu teto ulohy
    public void start(File directory) {
        if (directory == null) {
            // prislo me lepsi napad to udelat pres vyber souboru, preci jen to je lepsi..
            System.out.println("Vyberte prosím složku:");
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(new JFrame());
            if (result != JFileChooser.APPROVE_OPTION) {
                System.out.println("Nezvolena slozka");
                return;
            }
            selectedFile = fileChooser.getSelectedFile();
        } else {
            selectedFile = directory;
        }

        if (!selectedFile.exists()) {
            System.out.println("Tato slozka neexistuje. Zkuste ji napsat spravne.");
            return;
        }

        // kdyby nahodou tam je kontrola directory, prestoze mam directoriesonly.
        if (!selectedFile.isDirectory()) {
            System.out.println("Zvolil jste soubor, ne slozku.");
            return;
        }
        System.out.println("Slozka: " + selectedFile.getAbsolutePath());
        System.out.println("Vybiram vsechny udaje z ni");
        addAllFiles(selectedFile);
        System.out.println("Hudba:");
        for (Music music : musics) {
            System.out.println(music.oldName + " -> " + music.getNewName());
        }
        System.exit(0);
    }

    // metoda na vyber vsech files a dani je do listu
    public void addAllFiles(File directory) {
        // kontrolni podminky
        if (directory == null) System.out.println("Nebyla zvolena slozka.");
        if (!directory.isDirectory()) System.out.println("Nebyla zvolena slozka. Nejspis jste zvolil soubor.");

        for (File sub : directory.listFiles()) {
            if (sub.isDirectory()) {
                addAllFiles(sub);
            }
            // jinak else protoze i directory chteji prejmenovat
            // vyuzil jsem replace z duvodu ze tam nechceme cely path - tak jsem to pochopil ze zadani
            musics.add(new Music(sub.getAbsolutePath().replace(selectedFile.getAbsolutePath(), ""), sub.getName()));
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
            return Normalizer.normalize(oldFileName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }
    }

}

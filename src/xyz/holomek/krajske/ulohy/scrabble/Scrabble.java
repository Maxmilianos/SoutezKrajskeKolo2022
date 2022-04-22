package xyz.holomek.krajske.ulohy.scrabble;

import xyz.holomek.utils.UtilFiles;
import xyz.holomek.utils.UtilValue;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Scrabble {

    public ArrayList<Slovo> slova = new ArrayList<>();

    // metoda na zavolani pro zahajeni
    public void start() {
        System.out.println("Vyberte prosím slovnikovy soubor:");
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // teoreticky nemusi byt, je to v zakladu
        int result = fileChooser.showOpenDialog(new JFrame());
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("Nezvolena slozka");
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();
        System.out.println("Slovnik: " + selectedFile.getAbsolutePath());
        for (String line : UtilFiles.readFromFile(selectedFile)) {
            slova.add(new Slovo(line));
            //System.out.println("Registruju slovo: '" + line + "'");
        }
        System.out.println("Vsechny slova byla zaregistrovana.");
        System.out.println("Napiste prosim pismena:");
        try {
            // klasicky reader na konzolu
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                if (row.length() == 0 || row.length() > 10) {
                    System.out.println("Muzete zadat 1 az 10 pismen");
                } else {
                    Slovo slovo = ziskejSlovo(row);
                    if (slovo == null) {
                        System.out.println("Na tyto pismena nic ve slovniku nemate.");
                    } else {
                        System.out.println("Napsal jste: '" + row + "' nejdelsi slovo: '" + slovo.celeSlovo + "'");
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    // metoda na ziskani nejdelsiho slova
    public Slovo ziskejSlovo(String radek) {
        HashMap<String, Integer> pismena = new HashMap<String, Integer>();
        for (int i = 0; i < radek.length(); i ++) {
            String pismeno = radek.charAt(i) + "";
            if (!pismena.containsKey(pismeno))
                pismena.put(pismeno, 0);
            pismena.put(pismeno, pismena.get(pismeno) + 1);
        }

        HashMap<Object, Integer> moznaSlova = new HashMap<>();
        for (Slovo slovo : slova) {
            if (slovo.muzeToBytToSlovo(pismena)) {
                //System.out.println("Slovo: " + slovo.celeSlovo + " - muze byt - '" + radek + "'");
                moznaSlova.put(slovo, slovo.celeSlovo.length());
            }
        }

        // zkontroluje zda mapa neni prazdna, kdyby byla vrati to na null
        if (moznaSlova.size() != 0) {
            // predela to od nejmensiho po nejvetsi
            return (Slovo) UtilValue.convertToHighestLowest(moznaSlova).get(0);
        }
        return null;
    }


    public class Slovo {

        public String celeSlovo;

        // hashmap pouzit z duvodu lepsiho zpracovani poctu pismen
        public HashMap<String, Integer> pismena = new HashMap<String, Integer>();

        public Slovo(String celeSlovo) {
            this.celeSlovo = celeSlovo;
            for (int i = 0; i < celeSlovo.length(); i ++) {
                String pismeno = celeSlovo.charAt(i) + "";
                addPismeno(pismeno);
            }
        }

        // pridava pismeno jen do hashmapu
        private void addPismeno(String pismeno) {
            if (!pismena.containsKey(pismeno))
                pismena.put(pismeno, 0);
            pismena.put(pismeno, pismena.get(pismeno) + 1);
        }

        // slouzici jako boolean, zjisti to zda to slovo to muze byt, uz se mi to nechtelo psat anglicky
        public boolean muzeToBytToSlovo(HashMap<String, Integer> chars) {
            /*for (String pismeno : chars.keySet()) {
                int kolik = chars.get(pismeno);
                if (pismena.containsKey(pismeno)) {
                    int kolikMame = pismena.get(pismeno);
                    if (kolik > kolikMame)
                        return false;
                } else
                    return false;
            }*/
            for (String pismeno : pismena.keySet()) {
                int kolikPotrebujeme = pismena.get(pismeno);
                if (chars.containsKey(pismeno)) {
                    int kolikMame = chars.get(pismeno);
                    if (kolikPotrebujeme > kolikMame)
                        return false;
                } else
                    return false;
            }
            return true;
        }

    }

}

package xyz.holomek.okresni.ulohy.prevodCifer;

import xyz.holomek.utils.UtilFiles;
import xyz.holomek.utils.UtilMath;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class Prevod {

    // metoda na volani pres main, psani primo kodu
    public void startFromConsoleDe() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String row = null;
        System.out.println("Napiste kod ktery chcete rozlustit:");
        while ((row = bufferedReader.readLine()) != null) {
            System.out.println(de(row));
        }
    }
    // metoda na volani pres main, psani primo cisla
    public void startFromConsoleSe() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String row = null;
        System.out.println("Napiste cislo ktere chcete sifrovat:");
        while ((row = bufferedReader.readLine()) != null) {
            if (UtilMath.isInteger(row)) {
                int number = Integer.parseInt(row);
                if (number > 0) {
                    System.out.println(se(number));
                } else {
                    System.out.println("Spatne cislo.");
                }
            } else {
                System.out.println("Spatne cislo.");
            }
        }
    }

    // metoda na volani pres main, pouze pro vyber souboru
    public void startFromFile() {

        /*
        System.out.println("Number 1021005: " + de("x   xx x     xxxxx")); //cislo 1021005
        System.out.println("Number 35211: " + de("xxx xxxxx xx x x")); //cislo 35211
        */

        /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String row = null;
        /*System.out.println("Moznosti (napiste cislo):");
        System.out.println(" 1  -  Ukoly 1-6");
        System.out.println(" 2  -  Ukoly 7-8");
        while ((row = bufferedReader.readLine()) != null) {
            if (row.equalsIgnoreCase("1")) {
                ukoly16();
            } else if (row.equalsIgnoreCase("2")) {
                ukoly18();
            } else {
                System.out.println("Neplatne.");
            }
        }*/

        /*
        Pak odkom
         */
        JFileChooser fileChooser = new JFileChooser(".");
        int result = fileChooser.showOpenDialog(new JFrame());
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("Nezvolen subor");
            return;
        }
        File selectedFile = fileChooser.getSelectedFile();
        System.out.println("Subor: " + selectedFile.getAbsolutePath());

        ArrayList<String> lines = UtilFiles.readFromFile(selectedFile);
        System.out.println("Prvnich pet:");
        for (String line : ((ArrayList<String>) lines.clone()).subList(0,5)) {
            System.out.println(de(line));
        }
        System.out.println("");
        System.out.println("");
        System.out.println("Nekoncici nulou:");
        for (String line : ((ArrayList<String>) lines.clone())) {
            if (!line.endsWith(" "))
                System.out.println(de(line));
        }

        ArrayList<String> vysledkyDes = new ArrayList<String>();

        for (String line : ((ArrayList<String>) lines.clone())) {
            vysledkyDes.add(de(line));
        }

        try {
            UtilFiles.appendToFile("./src/vysledky-sifer.txt", String.join("\n", vysledkyDes));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String se(int number) {
        String ret = "";
        String s = "" + number;
        for (int i = 0; i < s.length(); i++) {
            String numb = "" + s.charAt(i);
            int n = Integer.parseInt(numb);
            if (n == 0) {
                ret += " ";
            } else {
                for (int c = 0; c < n; c++) {
                    ret += "x";
                }
            }
            ret += " ";
        }
        return ret;
    }

    //neumi to jeste ty nuly na konci, potom to dodelaj
    private String de(String s) {
        String ret = "";
        if (s.startsWith(" ")) {
            return "Kod nemuze zacinat mezerou. - '" + s + "'";
        }
        String[] split = s.split(" ");
        int mez = 0;
        for (String spl : split) {
            //System.out.println("New split - "+ spl);
            if (!spl.contains("x")) {
                mez += 1;
            } else {
                mez = 0;
                /*
                Nejak tak to udelaj
                System.out.println("Mezery" + mez);
                if (mez == 2) {
                    // xx  xxx xx
                    System.out.println("Spatny kod");
                }*/
                int xs = 0;
                for (int i = 0; i < spl.length(); i++) {
                    String act = "" + spl.charAt(i);
                    //System.out.println("char " + i + " - '" + act + "'");
                    if (act.equalsIgnoreCase("x")) {
                        //System.out.println("Contains " + i + "-" + xs);
                        xs += 1;
                    }
                }
                if (xs > 9) {
                    return "Spatny kod - '" + s + "'";
                }
                ret += xs;
            }
            //System.out.println("mezer: " + mez);
            if (mez == 2) {
                ret += "0";
                mez = 0;
            }
        }
        return ret;
    }

}

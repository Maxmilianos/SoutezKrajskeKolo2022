package xyz.holomek;

import xyz.holomek.krajske.ulohy.autoradio.AutoRadio;
import xyz.holomek.krajske.ulohy.bumbac.Bumbac;
import xyz.holomek.krajske.ulohy.piskvorky.Piskvorky;
import xyz.holomek.krajske.ulohy.piskvorky.PiskvorkySelect;
import xyz.holomek.krajske.ulohy.scrabble.Scrabble;

public class Test {

    public static void main(String[] args) {

        /*
        Prvni uloha, splnena cela, funkcni
         *//*
        Bumbac bumbac = new Bumbac();
        bumbac.start();

        /*
        Druha uloha, splnena, az na tu oznameni o chybe, nevim jaka chyba by se mohla vyskytnut
        V pripade ze chceme zapinat pres parametr, musíme napsat
        autoRadio.start(new File(args[0]));
         *//*
        AutoRadio autoRadio = new AutoRadio();
        autoRadio.start(null);

        /*
        Treti uloha, splnena, funkcni, splnena cela myslim
         *//*
        PiskvorkySelect pexesoSelect = new PiskvorkySelect();
        pexesoSelect.initialize();
        /*Piskvorky test = new Piskvorky(7, "First", "Second");
        test.start();*/

        /*
        Ctvrta uloha, splnena cela
         *//*
        Scrabble scrabble = new Scrabble();
        scrabble.start();*/

    }

}

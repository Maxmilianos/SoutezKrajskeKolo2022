package xyz.holomek.krajske.ulohy.piskvorky;

import javax.swing.*;

public class Field {

    protected int rowX, rowY;

    // typ policka, zda neni obsazeno = NONE
    protected Type type = Type.NONE;

    protected JLabel label;

    //
    public Field(int x, int y) {
        this.rowX = x;
        this.rowY = y;
    }

    // nastaveni typu, zaroven to zmeni i text v labelu
    public void setType(Type type) {
        this.type = type;
        label.setText(type.ch);
    }

}
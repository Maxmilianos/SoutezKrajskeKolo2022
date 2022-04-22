package xyz.holomek.krajske.ulohy.piskvorky;

import javax.swing.*;

public class Field {

    protected int rowX, rowY;

    protected Type type = Type.NONE;

    protected JLabel label;

    public Field(int x, int y) {
        this.rowX = x;
        this.rowY = y;
    }

    public void setType(Type type) {

    }

}
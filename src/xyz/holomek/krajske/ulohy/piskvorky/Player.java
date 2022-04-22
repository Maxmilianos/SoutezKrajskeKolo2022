package xyz.holomek.krajske.ulohy.piskvorky;

import javax.swing.*;

public class Player {

    public String name;

    public Type type;

    public int winning = 0;

    public JLabel label;

    public Player(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public int getWinning() {
        return winning;
    }
}

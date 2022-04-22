package xyz.holomek.krajske.ulohy.piskvorky;

public enum Type {
    X("X"),
    O("O"),
    NONE("");

    String ch;

    Type(String c) {
        ch = c;
    }

    @Override
    public String toString() {
        return ch;
    }


}
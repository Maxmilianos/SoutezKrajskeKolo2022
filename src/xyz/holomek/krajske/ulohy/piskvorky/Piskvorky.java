package xyz.holomek.krajske.ulohy.piskvorky;

import xyz.holomek.utils.UtilMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Piskvorky {

    private JFrame frame;

    private int sizeX, sizeY, fieldsPerSide = 7, actualPlaying = 0, pocetHer;

    private Player playerOne, playerTwo, winner;

    private long startedTime = 0;

    private ArrayList<Field> fields = new ArrayList<Field>();

    // pro upresneni vsech pozadavku
    public Piskvorky(int fieldsPerSide, int pocetHer, Player playerOne, Player playerTwo) {
        this.sizeX = 600;
        this.sizeY = 600;
        this.fieldsPerSide = fieldsPerSide;
        this.pocetHer = pocetHer;
        this.playerOne = playerOne;
        this.playerOne.type = Type.X;
        this.playerTwo = playerTwo;
        this.playerTwo.type = Type.O;
    }

    // uvodni startovaci metoda
    public void start() {
        frame = new JFrame();
        frame.setTitle("Piskvorky");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // +25 u vysky protoze toolkit, u linuxu je jiny; +150 kvuli hracum
        frame.setBounds(screenSize.width / 2 - sizeX / 2, screenSize.height / 2 - sizeY / 2, sizeX + 150, sizeY + 25);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        // zapiseme fields
        for (int x = 0; x < fieldsPerSide; x++) {
            for (int y = 0; y < fieldsPerSide; y++) {
                fields.add(new Field(x, y));
            }
        }

        // zjistime X, Y velikost pro kolonku
        int sizePerFieldX = sizeX / fieldsPerSide, sizePerFieldY = sizeY / fieldsPerSide;

        // pro vsechny kolonky
        for (Field field : fields) {
            // vypocitani x, y na zaklade poctu poli a velikosti hraciho pole
            int rowX = field.rowX, rowY = field.rowY;
            int x = rowX * sizePerFieldX, y = rowY * sizePerFieldY;

            JLabel fieldLabel = new JLabel();
            // font size, musime upravovat podle velikosti pole
            int size = 64;
            if (fieldsPerSide > 7)
                size = 32;
            else if (fieldsPerSide > 10)
                size = 16;
            else if (fieldsPerSide > 15)
                size = 12;
            fieldLabel.setFont(new Font("Serif", Font.BOLD, size)); // idealni font pro to me prislo
            fieldLabel.setBounds(x, y, sizePerFieldX, sizePerFieldY);

            // misto zdlouhaveho vykreslovani poli, muzeme udelat toto, je to rychlejsi o par procent nez kdybych to mel vykreslovat
            fieldLabel.setForeground(Color.BLACK);
            fieldLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            fieldLabel.setHorizontalAlignment(JLabel.CENTER);

            // pridaj listener na poslouchani klikani // pridano
            fieldLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onClick(field);
                }

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });

            field.label = fieldLabel;
            frame.getContentPane().add(fieldLabel);
        }

        JLabel playerOne = new JLabel(this.playerOne.getName() + " (Hraje)");
        playerOne.setBounds(sizeX + 1, 10, 149, 30);
        playerOne.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(playerOne);

        JLabel playerTwo = new JLabel(this.playerTwo.getName());
        playerTwo.setBounds(sizeX + 1, sizeY - 50, 149, 30);
        playerTwo.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(playerTwo);

        //dodatek pro hrace, pocer vyher, nemusime nikde ukladat, po kazde inicializaci se to udela samo =)
        JLabel playerOneWinnings = new JLabel("Wins: " + this.playerOne.winning);
        playerOneWinnings.setBounds(sizeX + 1, 40, 149, 30);
        playerOneWinnings.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(playerOneWinnings);

        JLabel playerTwoWinnings = new JLabel("Wins: " + this.playerTwo.winning);
        playerTwoWinnings.setBounds(sizeX + 1, sizeY - 90, 149, 30);
        playerTwoWinnings.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(playerTwoWinnings);

        this.playerOne.label = playerOne;
        this.playerTwo.label = playerTwo;

        // napadlo mna pridelat aj cas
        JLabel timeCheck = new JLabel("Cas: 0 sekund");
        timeCheck.setBounds(sizeX + 1, (sizeY - 30) / 2, 149, 30);
        timeCheck.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(timeCheck);

        // cas zahajen, zahaji se hned jak se spusti a vykresli appka
        startedTime = System.currentTimeMillis();
        new Thread(() -> {
            // jakmile se najde vitez, casovac se zastavi
            while (winner == null) {
                double time = UtilMath.trim(1, (double) (System.currentTimeMillis() - startedTime) / 1000);
                timeCheck.setText("Cas: " + time + " sekund");
            }
        }).start();

        frame.setVisible(true);
    }

    public void onClick(Field field) {
        if (winner != null) {
            System.out.println("Jiz je nekdo vitez.");
            return;
        }
        if (field.type != Type.NONE) {
            System.out.println("Hrac nemuze klikat na obsazene pole.");
            return;
        }
        System.out.println("Zaregistrovano");
        Player playing = getActualPlayingPlayer();
        field.setType(playing.type);
        nextPlayer();
        Player winner = checkWinner();
        if (winner != null) {
            System.out.println("Mame viteze hrace: " + winner.getName() + " typ: " + winner.type.ch);
            this.winner = winner;
            this.winner.winning += 1;

            int pocetZbyvajicichHer = playerOne.winning + playerTwo.winning - pocetHer;

            if (pocetZbyvajicichHer == 0) {
                Player gameWinner = playerOne.winning > playerTwo.winning ? playerOne : (playerOne.winning < playerTwo.winning ? playerTwo : null);
                int result = 0;
                if (gameWinner == null)
                    result = JOptionPane.showConfirmDialog(frame,
                            "Gratuluji! Remízovali jste na plné čáře!\nChcete hrát další sérii her?"
                        );
                else
                    result = JOptionPane.showConfirmDialog(frame,
                            "Gratuluji! Celou hru vyhrál hráč " + winner.getName() + "!\nChcete hrát další sérii her?"
                    );
                if (result == 0) {
                    playerOne.winning = 0;
                    playerTwo.winning = 0;
                    new Piskvorky(fieldsPerSide, pocetHer, playerTwo, playerOne).start();
                    frame.dispose();
                } else {
                    System.exit(0);
                }
            } else {
                new Piskvorky(fieldsPerSide, pocetHer, playerTwo, playerOne).start();
                frame.dispose();
            }
        }
    }

    // metoda na vyhru, checkovani
    private Player checkWinner() {
        // mapa na ukladani listu poli (pr X = [[x,y]], apod.)
        HashMap<Type, ArrayList<Field>> map = new HashMap<Type, ArrayList<Field>>();

        int vyherPoleMusiMit = 4;
        // muzeme si zapinat a vypinat ruzne kontroly, vertikalni, horizontalni, do krize, apod..
        boolean horizontal = true, vertical = true, a = true, b = true;

        for (Type t : Type.values())
            if (t != Type.NONE)
                map.put(t, new ArrayList<Field>());

        //horizontalni kontrola
        if (horizontal) {
            for (int y = 0; y < fieldsPerSide; y++) {
                for (int x = 0; x < fieldsPerSide; x++) {
                    Field f = getField(x, y);
                    if (f != null) {
                        if (f.type == Type.NONE)
                            for (Type t : map.keySet())
                                map.get(t).clear();
                        else {
                            map.get(f.type).add(f);
                            for (Type t : map.keySet())
                                if (t != f.type)
                                    map.get(t).clear();
                            if (map.get(f.type).size() >= vyherPoleMusiMit) {
                                return getPlayerWhich(f.type);
                            }
                        }
                    }
                }
                for (Type t : map.keySet())
                    map.get(t).clear();
            }
        }

        for (Type t : map.keySet())
            map.get(t).clear();

        //vertikalni kontrola
        if (vertical) {
            for (int x = 0; x < fieldsPerSide; x++) {
                for (int y = 0; y < fieldsPerSide; y++) {
                    Field f = getField(x, y);
                    if (f != null) {
                        if (f.type == Type.NONE)
                            for (Type t : map.keySet())
                                map.get(t).clear();
                        else {
                            map.get(f.type).add(f);
                            for (Type t : map.keySet())
                                if (t != f.type)
                                    map.get(t).clear();
                            if (map.get(f.type).size() >= vyherPoleMusiMit) {
                                return getPlayerWhich(f.type);
                            }
                        }
                    }
                }
                for (Type t : map.keySet())
                    map.get(t).clear();
            }
        }

        for (Type t : map.keySet())
            map.get(t).clear();

        // a - proste na X (od jedne care k druhe care)
        if (a) {
            for (int y = 0; y < fieldsPerSide; y++) {
                for (int x = 0; x < fieldsPerSide; x++) {
                    Field f = getField(x, y);
                    if (f != null) {
                        if (f.type != Type.NONE) {
                            boolean check = true;
                            for (int i = 1; i <= vyherPoleMusiMit-1; i++) {
                                Field newField = getField(x + i, y + i);
                                if (newField != null) {
                                    if (newField.type != f.type) {
                                        check = false;
                                    } else if (i == vyherPoleMusiMit-1 && check) {
                                        return getPlayerWhich(f.type);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Type t : map.keySet())
            map.get(t).clear();

        // b - proste na X (od jedne care k druhe care)
        if (b) {
            for (int y = 0; y < fieldsPerSide; y++) {
                for (int x = 0; x < fieldsPerSide; x++) {
                    Field f = getField(x, y);
                    if (f != null) {
                        if (f.type != Type.NONE) {
                            boolean check = true;
                            for (int i = 1; i <= vyherPoleMusiMit-1; i++) {
                                Field newField = getField(x - i, y + i);
                                if (newField != null) {
                                    if (newField.type != f.type) {
                                        check = false;
                                    } else if (i == vyherPoleMusiMit-1 && check) {
                                        return getPlayerWhich(f.type);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    // metoda na ziskani policka podle souradnic
    public Field getField(int rowX, int rowY) {
        for (Field field : fields)
            if (field.rowY == rowY && field.rowX == rowX)
                return field;
        return null;
    }

    // metoda na ziskani hrace s danym typem, vyuzivame jen u checkWin
    public Player getPlayerWhich(Type type) {
        if (type == Type.NONE)
            return null;
        return playerOne.type == type ? playerOne : playerTwo;
    }

    // metoda na ziskani hrace ktery prave hraje
    public Player getActualPlayingPlayer() {
        return actualPlaying == 0 ? playerOne : playerTwo;
    }

    // metoda na ziskani hrace ktery hraje po nem
    public Player getNextPlayingPlayer() {
        return actualPlaying == 1 ? playerOne : playerTwo;
    }

    // metoda, ktera nam nastavi dalsiho hrace
    public void nextPlayer() {
        actualPlaying = actualPlaying == 0 ? 1 : 0;
        Player actualPlayer = getActualPlayingPlayer(), nextPlayer = getNextPlayingPlayer();
        actualPlayer.label.setText(actualPlayer.getName() + " (Hraje)");
        nextPlayer.label.setText(nextPlayer.getName());
    }
}

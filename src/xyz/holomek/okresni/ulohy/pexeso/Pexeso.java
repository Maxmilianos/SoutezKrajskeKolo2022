package xyz.holomek.okresni.ulohy.pexeso;

import xyz.holomek.utils.UtilFiles;
import xyz.holomek.utils.UtilImage;
import xyz.holomek.utils.UtilMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Pexeso {


    private JFrame frame;

    private int sizeX, sizeY, tahy = 0, winnedTime;

    private Card opened = null;

    // nemusi to tam byt, predelane
    //private ArrayList<File> files = new ArrayList<File>();

    private ArrayList<Card> cards = new ArrayList<Card>();

    private long timeClicked = 0, started;

    private boolean can = false;

    public Pexeso() {
        this.sizeX = 600;
        this.sizeY = 600;

        /* nemusi to tam byt, predelal jsem to jinak
        for (File file : new File("./resources/").listFiles()) {
            if (file.getName().endsWith(".jpg")) {
                files.add(file);
                System.out.println("Pridavam obrazek '" + file.getName() + "' do listu.");
            }
        }*/
    }

    public void initializace() {
        frame = new JFrame();
        frame.setTitle("Pexeso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // +50 u vysky protoze sledovani casu hry
        frame.setBounds(screenSize.width / 2 - sizeX / 2, screenSize.height / 2 - sizeY / 2, sizeX, sizeY + 50);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        /*
        Line - 0-3

         */
        int line = 0;

        int sizeOneX = sizeX / 4, sizeOneY = sizeY / 4;

        ArrayList<Field> fields = new ArrayList<Field>();

        for (int x = 0; x < 4; x ++) {
            for (int y = 0; y < 4; y ++) {
                Field field = new Field(x, y, x * sizeOneX, y * sizeOneY);
                //System.out.println("x: " + x + " y: " + y + " sX: " + field.startX +" sY:" + field.startY);
                fields.add(field);
            }
        }

        // zamicha nam list
        Collections.shuffle(fields);


        for (int pocet = 0; pocet < 8; pocet ++) {

            File file = new File("./resources/", (pocet + 1) + ".jpg");

            if (!file.exists()) {
                System.out.println("Nebyl nalezen dulezity soubor. Vypinam");
                return;
            }

            ImageIcon imageIcon = UtilImage.getScaledImage(new ImageIcon(file.getAbsolutePath()), sizeOneX, sizeOneY);

            Field mainField = fields.get(UtilMath.random(fields.size()));
            fields.remove(mainField);
            Field coopField = fields.get(UtilMath.random(fields.size()));
            fields.remove(coopField);

            JLabel mainLabel = new JLabel(),
                    coopLabel = new JLabel();
            mainLabel.setText("");
            coopLabel.setText("");
            mainLabel.setIcon(null);
            coopLabel.setIcon(null);

            // vypada to blbe s tim
            /*mainLabel.setBackground(Color.CYAN);
            mainLabel.setOpaque(true);
            coopLabel.setBackground(Color.CYAN);
            coopLabel.setOpaque(true);*/

            // rozhodne lepsi nez predtim
            mainLabel.setForeground(Color.BLACK);
            mainLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            coopLabel.setForeground(Color.BLACK);
            coopLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            // lepsi design a obrazek je centered
            mainLabel.setFont(mainLabel.getFont().deriveFont(Font.ITALIC));
            coopLabel.setFont(mainLabel.getFont().deriveFont(Font.ITALIC));
            mainLabel.setHorizontalAlignment(JLabel.CENTER);
            coopLabel.setHorizontalAlignment(JLabel.CENTER);

            mainLabel.setBounds(mainField.startX, mainField.startY, sizeOneX, sizeOneY);
            coopLabel.setBounds(coopField.startX, coopField.startY, sizeOneX, sizeOneY);

            frame.getContentPane().add(mainLabel);
            frame.getContentPane().add(coopLabel);

            Card mainCard = new Card(imageIcon, mainLabel),
                    coopCard = new Card(imageIcon, coopLabel);

            // nastavime coop
            mainCard.setCoop(coopCard);
            coopCard.setCoop(mainCard);

            // pridelaj tam potom pres interface akce
            mainLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    click(mainCard);
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
            coopLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    click(coopCard);
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

            cards.add(mainCard);
            cards.add(coopCard);
        }

        System.out.println("Vygenerovane");


        JLabel timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setBounds(0, sizeY, 600, 50);
        timeLabel.setText("Cas zatim je:");
        frame.getContentPane().add(timeLabel);

        frame.setVisible(true);

        // thread na sledovani veci
        new Thread(() -> {
            boolean cont = true;
            while (cont) {
                if (started != 0) // cas na start je spusten az po prvnim kliknuti
                    timeLabel.setText("Cas zatim je: " + ((System.currentTimeMillis() - started) / 1000));

                if (timeClicked + 1000 < System.currentTimeMillis() && can) {
                    for (Card card : cards) {
                        if (card.isShowed() && !card.isFinded()) {
                            card.close();
                        }
                    }
                    can = false;
                }

                // checking win
                boolean winned = true;
                for (Card card : cards) {
                    if (!card.isFinded())
                        winned = false;
                }

                // pro testovaci ucely
                //winned = true;

                if (winned) {
                    cont = false;
                    winnedTime = (int) ((System.currentTimeMillis() - started) / 1000);
                    if (winnedTime > 1008718256) // pro testovaci ucely, jelikoz to spustilo hned
                        winnedTime = 50;
                    System.out.println("Vyhra s " + tahy + " tahy a casem " + winnedTime + " sekund");

                    int result = JOptionPane.showConfirmDialog(frame,
                            "Vyhra s " + tahy + " tahy a casem " + winnedTime + " sekund. " +
                                    "\nChcete zapsat svoje jmeno?" +
                                    "\nZda ano, kliknete na ne. Pro dalsi hru dejte ne." +
                                    "\nPro ukonceni cancel");

                    if (result == 0) { // ano chce
                        String name = JOptionPane.showInputDialog("Zadejte svoje jmeno pro ulozeni");
                        if (name.length() != 0) {
                            try {
                                UtilFiles.appendToFile(new File("./src/pexeso-stats.txt"), name + ";" + tahy + ";" + winnedTime + "\n");
                            } catch (IOException e) {
                                System.out.println("Nastala chyba pri zapisovani.");
                                e.printStackTrace();
                            }
                        } else {
                            System.exit(0);
                        }
                    } else if (result == 1) { // dalsi hra
                        new Pexeso().initializace();
                        frame.dispose();
                    } else { // nechce hrat
                        System.exit(0);
                    }

                }

            }
        }).start();
        System.out.println("Aplikace se spustila");
    }

    public void click(Card card) {
        if (card.isFinded() || card.isShowed() || can)
            return;

        if (started == 0)
            started = System.currentTimeMillis();

        System.out.println("Opening");

        card.show();

        if (opened != null) {
            if (opened.getCoop().equals(card)) { // ma prvni par
                card.setFinded(true);
                opened.setFinded(true);
            } else {
                can = true;
            }
            opened = null;
            tahy += 1;
        } else {
            opened = card;
        }
        timeClicked = System.currentTimeMillis();
    }

    public class Card {

        private Card coop;

        private ImageIcon image;

        private JLabel label;

        private boolean showed = false, finded = false;

        public boolean isShowed() {
            return showed;
        }

        public boolean isFinded() {
            return finded;
        }

        public void setShowed(boolean showed) {
            this.showed = showed;
        }

        public void setFinded(boolean finded) {
            this.finded = finded;
        }

        public Card(ImageIcon image, JLabel label) {
            this.image = image;
            this.label = label;
        }

        public ImageIcon getImage() {
            return image;
        }

        public Card getCoop() {
            return coop;
        }

        public void setCoop(Card coop) {
            this.coop = coop;
        }

        public JLabel getLabel() {
            return label;
        }

        public void show() {
            showed = true;
            getLabel().setIcon(getImage());
        }

        public void close() {
            showed = false;
            getLabel().setIcon(null);
        }

    }

    public class Field {

        public int x, y, startX, startY;

        public Field(int x, int y, int startX, int startY) {
            this.x = x;
            this.y = y;
            this.startX = startX;
            this.startY = startY;
        }
    }

}

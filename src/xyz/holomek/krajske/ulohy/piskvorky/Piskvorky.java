package xyz.holomek.krajske.ulohy.piskvorky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Piskvorky {

    private JFrame frame;

    private int sizeX, sizeY, fieldsPerSide = 7, actualPlaying = 0;

    private Player playerOne, playerTwo;

    private ArrayList<Field> fields = new ArrayList<Field>();

    public Piskvorky(int fieldsPerSide, String playerOne, String playerTwo) {
        this.sizeX = 600;
        this.sizeY = 600;
        this.fieldsPerSide = fieldsPerSide;
        this.playerOne = new Player(playerOne, Type.X);
        this.playerTwo = new Player(playerTwo, Type.O);
    }

    public void start() {
        frame = new JFrame();
        frame.setTitle("Piskvorky");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // +24 u vysky protoze toolkit, u linuxu je jiny
        frame.setBounds(screenSize.width / 2 - sizeX / 2, screenSize.height / 2 - sizeY / 2, sizeX + 150, sizeY + 25);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        for (int x = 0; x < fieldsPerSide; x++) {
            for (int y = 0; y < fieldsPerSide; y++) {
                fields.add(new Field(x, y));
            }
        }

        int sizePerFieldX = sizeX / fieldsPerSide, sizePerFieldY = sizeY / fieldsPerSide;

        for (Field field : fields) {
            int rowX = field.rowX, rowY = field.rowY;
            int x = rowX * sizePerFieldX, y = rowY * sizePerFieldY;

            JLabel fieldLabel = new JLabel();
            fieldLabel.setFont(new Font("Serif", Font.BOLD, 64));
            fieldLabel.setBounds(x, y, sizePerFieldX, sizePerFieldY);

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

        this.playerOne.label = playerOne;
        this.playerTwo.label = playerTwo;

        frame.setVisible(true);
    }

    public void onClick(Field field) {
        if (field.type != Type.NONE) {
            System.out.println("Hrac nemuze klikat na obsazene pole.");
            return;
        }
        System.out.println("Zaregistrovano");
        Player playing = getActualPlayingPlayer();
        field.setType(playing.type);
        nextPlayer();
    }

    public Field getField(int rowX, int rowY) {
        for (Field field : fields)
            if (field.rowY == rowY && field.rowX == rowX)
                return field;
        return null;
    }

    public Player getActualPlayingPlayer() {
        return actualPlaying == 0 ? playerOne : playerTwo;
    }

    public Player getNextPlayingPlayer() {
        return actualPlaying == 1 ? playerOne : playerTwo;
    }

    public void nextPlayer() {
        actualPlaying = actualPlaying == 0 ? 1 : 0;
        Player actualPlayer = getActualPlayingPlayer(), nextPlayer = getNextPlayingPlayer();
        actualPlayer.label.setText(actualPlayer.getName() + " (Hraje)");
        nextPlayer.label.setText(nextPlayer.getName());
    }
}

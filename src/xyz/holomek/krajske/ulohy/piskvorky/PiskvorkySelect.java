package xyz.holomek.krajske.ulohy.piskvorky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PiskvorkySelect {

    private JFrame frame;

    public PiskvorkySelect() {
    }

    public void initialize() {
        frame = new JFrame();
        frame.setTitle("Piskvorky - nastaveni");
        frame.setBounds(650, 250, 600, 608);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel playerOne = new JLabel("Hráč 1");
        playerOne.setFont(new Font("Dialog", Font.BOLD, 14));
        playerOne.setBounds(150, 100, 300, 20);
        frame.getContentPane().add(playerOne);

        JTextField playerOneText = new JTextField("Test");
        playerOne.setLabelFor(playerOneText);
        playerOneText.setBounds(250, 125, 100, 20);
        frame.getContentPane().add(playerOneText);

        JLabel playerTwo = new JLabel("Hráč 2");
        playerTwo.setFont(new Font("Dialog", Font.BOLD, 14));
        playerTwo.setBounds(150, 175, 300, 20);
        frame.getContentPane().add(playerTwo);

        JTextField playerTwoText = new JTextField("Test");
        playerTwo.setLabelFor(playerTwoText);
        playerTwoText.setBounds(250, 200, 100, 20);
        frame.getContentPane().add(playerTwoText);

        JLabel fields = new JLabel("Hrací pole (napsat jen 7, pro 7x7)");
        fields.setFont(new Font("Dialog", Font.BOLD, 14));
        fields.setBounds(150, 300, 300, 20);
        frame.getContentPane().add(fields);

        JTextField fieldsText = new JTextField("7");
        fields.setLabelFor(fieldsText);
        fieldsText.setBounds(250, 325, 100, 20);
        fieldsText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                try {
                    Integer.parseInt(event.getKeyChar() + "");
                } catch (NumberFormatException e) {
                    event.consume();
                }
            }
        });
        frame.getContentPane().add(fieldsText);

        JLabel attack = new JLabel("Souboj? (Napiste kolik her chcete hrat)");
        attack.setFont(new Font("Dialog", Font.BOLD, 14));
        attack.setBounds(150, 350, 300, 20);
        frame.getContentPane().add(attack);

        JTextField attackText = new JTextField("1");
        attack.setLabelFor(attackText);
        attackText.setBounds(250, 375, 100, 20);
        attackText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                try {
                    Integer.parseInt(event.getKeyChar() + "");
                } catch (NumberFormatException e) {
                    event.consume();
                }
            }
        });
        frame.getContentPane().add(attackText);

        JButton button = new JButton("Začít hru");
        button.setFont(new Font("Dialog", Font.BOLD, 18));
        button.setBounds(215, 428, 120, 35);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fieldsText.getText().length() == 0) {
                    return;
                }
                try {
                    new Piskvorky(
                            Integer.parseInt(fieldsText.getText()),
                            Integer.parseInt(attackText.getText()),
                            new Player(playerOneText.getText(), Type.X),
                            new Player(playerTwoText.getText(), Type.O)
                        ).start();
                    frame.dispose();
                } catch (NumberFormatException ex) {

                }
            }
        });
        frame.getContentPane().add(button);
        frame.setVisible(true);
    }
}

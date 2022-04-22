package xyz.holomek.krajske.ulohy.piskvorky;

import xyz.holomek.Test;

import javax.annotation.Resources;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

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

        JLabel logo = new JLabel(new ImageIcon("resources/tictactoe.png"));
        logo.setBounds(0, 0, 600, 150);
        frame.getContentPane().add(logo);

        // kolonky pro hrace
        JLabel playerOne = new JLabel("Hráč 1");
        playerOne.setFont(new Font("Dialog", Font.BOLD, 14));
        playerOne.setBounds(150, 180, 300, 20);
        playerOne.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(playerOne);

        JTextField playerOneText = new JTextField("Test");
        playerOne.setLabelFor(playerOneText);
        playerOneText.setBounds(240, 200, 120, 20);
        frame.getContentPane().add(playerOneText);

        JLabel playerTwo = new JLabel("Hráč 2");
        playerTwo.setFont(new Font("Dialog", Font.BOLD, 14));
        playerTwo.setBounds(150, 230, 300, 20);
        playerTwo.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(playerTwo);

        JTextField playerTwoText = new JTextField("Test");
        playerTwo.setLabelFor(playerTwoText);
        playerTwoText.setBounds(240, 250, 120, 20);
        frame.getContentPane().add(playerTwoText);

        // nastavujeme hraci pole
        JLabel fields = new JLabel("Hrací pole (napsat jen 7, pro 7x7)");
        fields.setFont(new Font("Dialog", Font.BOLD, 14));
        fields.setBounds(150, 305, 300, 20);
        fields.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(fields);

        JTextField fieldsText = new JTextField("7");
        fields.setLabelFor(fieldsText);
        fieldsText.setBounds(270, 325, 60, 20);
        fieldsText.setHorizontalAlignment(SwingConstants.CENTER);
        fieldsText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                // kontrola zda to je cislo
                try {
                    Integer.parseInt(event.getKeyChar() + "");
                } catch (NumberFormatException e) {
                    event.consume();
                }
            }
        });
        frame.getContentPane().add(fieldsText);

        // nastavujeme souboj
        JLabel attack = new JLabel("Souboj? (Napiste kolik her chcete hrat)");
        attack.setFont(new Font("Dialog", Font.BOLD, 14));
        attack.setBounds(150, 355, 300, 20);
        attack.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(attack);

        JTextField attackText = new JTextField("1");
        attack.setLabelFor(attackText);
        attackText.setBounds(270, 375, 60, 20);
        attackText.setHorizontalAlignment(SwingConstants.CENTER);
        attackText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                // kontrola zda to je cislo
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
        button.setBounds(240, 428, 120, 35);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fieldsText.getText().length() == 0) {
                    return;
                }
                try {
                    // vytvarime instanci piskvorky
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

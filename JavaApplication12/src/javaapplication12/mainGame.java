/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

/**
 *
 * @author kikas
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainGame extends javax.swing.JFrame {
    Sudoku sudoku = new Sudoku();
    private int index;

    public mainGame() {
        initComponents();
        
        final long startingGame = System.currentTimeMillis();
        EndGame end = new EndGame();
        System.out.println(startingGame);
        JButton [][] Cases = new JButton[9][9];
        Grid.setLayout(new GridLayout(9,9));
        
        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                final int valueIndex = this.sudoku.getNumber(i * 9 + j);
                Cases[i][j] = new JButton();
                if(valueIndex != 0) Cases[i][j].setText("" + valueIndex);
                Cases[i][j].setFont(new Font("Calibri", Font.PLAIN, 30));
                Cases[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(int i = 0 ; i < 9 ; i++) {
                            for(int j = 0 ; j < 9 ; j++) {
                                if(e.getSource() == Cases[i][j]) {
                                    index = i * 9 + j;

                                    int newValue;
                                    if(Cases[i][j].getText() == "") newValue = 1;
                                    else newValue = (Integer.parseInt(Cases[i][j].getText()) + 1) % 10;

                                    if(!sudoku.deleteNumber(index)) return;
                                    sudoku.addNumber(index, newValue);
                                    
                                    if(newValue != 0) Cases[i][j].setText("" + newValue);
                                    else Cases[i][j].setText("");
                                    Cases[i][j].setForeground(Color.BLUE);
                                    
                                    if(sudoku.checkWin()) {
                                        setVisible(false);
                                        dispose();
                                        final long endGame = System.currentTimeMillis();
                                        end.launch((endGame - startingGame) / 1000);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                });
                Grid.add(Cases[i][j]);
            }
        }
    
        resetGrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudoku.resetGrid();
                for(int i = 0 ; i < 9 ; i++) {
                    for(int j = 0 ; j < 9 ; j++) {
                        int number = sudoku.getNumberFromScratch(i, j);
                        if(number == 0) Cases[i][j].setText("");
                        else Cases[i][j].setText("" + number);
                    }
                }
            }
        });

        giveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"You lose !");
                homePage frame2 = new homePage();
                frame2.show();
                dispose();
            }
        });
    }


    private void initComponents() {
        jMenu3 = new javax.swing.JMenu();
        Grid = new javax.swing.JPanel();
        resetGrid = new javax.swing.JButton();
        giveUp = new javax.swing.JButton("Give up");

        jMenu3.setText("jMenu3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Grid.setBackground(new java.awt.Color(76, 128, 0));
        Grid.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Grid.setForeground(java.awt.Color.darkGray);
        Grid.setPreferredSize(new java.awt.Dimension(900, 900));
        Grid.setLayout(new java.awt.GridLayout());

        resetGrid.setText("Reset grid");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Grid, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(resetGrid, 110, 110, 110)
                .addGap(10, 10, 10)
                .addComponent(giveUp, 110, 110, 110)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(resetGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(giveUp, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(Grid, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setTitle("Sudoku");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    private javax.swing.JPanel Grid;
    private javax.swing.JButton resetGrid, giveUp;
    private javax.swing.JMenu jMenu3;
}

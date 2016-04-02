package UI;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.Semaphore;

import DB.DBLib;

/**
 * Created by onotole on 02.04.16.
 */
public class ChatWindow extends JFrame {
    String name = null;
    private DBLib db = new DBLib();
    private int myLastMessageID = 0;
    Semaphore semaphore = new Semaphore(1);

    private void setMyLastMessageID(int ID) {
        this.myLastMessageID = ID;
    }

    public String getName() {
        return name;
    }

    public ChatWindow(String name) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.name = name;
        db.createDB();
//        db.cleanDB();
        db.addMessage("####", name + " joined us!");

        setSize(400,400);
        setLocation(700,200);
        setTitle("Chat for  " + name + "!");

        JTextArea chatArea = new JTextArea();
        chatArea.setBackground(Color.lightGray);
        add(chatArea, BorderLayout.NORTH);



        JPanel southPanel  = new JPanel();
        southPanel.setLayout(new BorderLayout());
        add(southPanel, BorderLayout.SOUTH);

        JButton jSendButton = new JButton("SEND");
        southPanel.add(jSendButton, BorderLayout.EAST);

        JTextField inputTextField = new JTextField();
        southPanel.add(inputTextField, BorderLayout.CENTER);

        jSendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.addMessage(getName(), inputTextField.getText());
                inputTextField.setText("");
            }
        });
        setVisible(true);

        while (true) {
            try {
                semaphore.acquire();
            } catch(InterruptedException ie) {
                System.out.println(ie.getStackTrace());
            }

            System.out.println("cycle iteration");
            if (myLastMessageID != db.getLastMessageID()) {
                List<Message> list = db.getMessageAfterID(myLastMessageID);
                for (Message message: list) {
                    chatArea.append(message.getNickname() + ":> " + message.getMessage() + "\n");
                }
                myLastMessageID = db.getLastMessageID();
            }
            db.closeConnection();
            semaphore.release();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println(e.getStackTrace());
            }


        }

    }




}

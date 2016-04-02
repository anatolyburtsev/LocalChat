package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by onotole on 02.04.16.
 */
public class EntranceWindow extends JFrame {

    public EntranceWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200, 100);
        setLocation(700, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        JTextField jtf = new JTextField();
        jtf.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        panel.add(jtf);

        JButton jb = new JButton("ENTER YOUR NICKNAME");
        jb.setSize(100, 50);
        panel.add(jb, BorderLayout.SOUTH);

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatWindow w = new ChatWindow(jtf.getText());
                w.show();
                setVisible(false);
            }
        });
    }

}

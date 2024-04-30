package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;//Date in java is ambigous

public class FastCash extends JFrame implements ActionListener{
    JLabel l1;
    JButton b1,b2,b3,b4,b5,b6,b7;
    String pin;

    FastCash(String pin) {
        setLayout(null);
        this.pin = pin;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(800, 740, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l2 = new JLabel(i3);
        l2.setBounds(0, 0, 800, 680);
        add(l2);

        l1 = new JLabel("Select Withdrawl Amount");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(180,210,700,35);
        l2.add(l1);

        b1 = new JButton("Rs- 100");
        b1.setBounds(150,310,110,25);
        l2.add(b1);

        b2 = new JButton("Rs 500");
        b3 = new JButton("Rs 1000");
        b4 = new JButton("Rs 2000");
        b5 = new JButton("Rs 5000");
        b6 = new JButton("Rs 10000");
        b7 = new JButton("BACK");

        b2.setBounds(300,310,150,25);
        l2.add(b2);

        b3.setBounds(150,340,110,25);
        l2.add(b3);

        b4.setBounds(300,340,150,25);
        l2.add(b4);

        b5.setBounds(150,370,110,25);
        l2.add(b5);

        b6.setBounds(300,370,150,25);
        l2.add(b6);

        b7.setBounds(300,398,150,25);
        l2.add(b7);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        setSize(800,680);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String buttonText = ((JButton)ae.getSource()).getText();
            String amount = buttonText.substring(3).replaceAll(",", "").trim(); // Remove commas
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pin+"'");
            int balance = 0;
            while (rs.next()) {
                if (rs.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
            if (ae.getSource() != b7 && balance < Integer.parseInt(amount)) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                return;
            }

            if (ae.getSource() == b7) {
                this.setVisible(false);
                new Transactions(pin).setVisible(true);
            } else {
                Date date = new Date();
                c.s.executeUpdate("insert into bank values('"+pin+"', '"+date+"', 'Withdrawal', '"+amount+"')");
                JOptionPane.showMessageDialog(null, "Rs. "+amount+" Withdrawal Successfully");

                setVisible(false);
                new Transactions(pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args){
        new FastCash("");
    }
}
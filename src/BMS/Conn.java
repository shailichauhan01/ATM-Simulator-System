package bank.management.system;

import java.sql.*;

//JDBC connectivity has 5 steps
//1-Register the Driver
//2-Create Connection
//3-Create Statement
//4-Execute Query
//5-Close Connection
//mysql is a sequential entity

public class Conn{
    Connection c;
    Statement s;
    public Conn(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c =DriverManager.getConnection("jdbc:mysql:///bankmanagementsystem","root","shaili0505");
            s =c.createStatement();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
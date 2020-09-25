/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/* CREATED BY RIDHO IRYA Email : Ridho.898@Gmail.com */

public class Main {
        static private Statement stt;
        static private ResultSet rss;

    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);
               
    }
    
}

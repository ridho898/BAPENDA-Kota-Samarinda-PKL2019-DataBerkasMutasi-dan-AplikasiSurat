/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;


public class JavaApplication5 {
    private Koneksi konek = new Koneksi();
    private Connection con = konek.KoneksiDB();
    public static void main(String[] args) {
    Login a = new Login();
    a.setVisible(true);
    }
}

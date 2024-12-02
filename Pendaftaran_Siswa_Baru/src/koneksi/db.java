/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import java.sql.*;
import javax.swing.*;
/**
 *
 * @author user
 */
public class db {
    private static Connection conn;
    public static Connection getKoneksi(){
        String host = "jdbc:mysql://localhost/pendaftaran_siswa",
                user = "Bu_Estri",
                pass = "bu_estri123";
        try {
            conn = (Connection) DriverManager.getConnection(host, user, pass);
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        return conn;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.controller;

import com.view.form_mhs;
import java.sql.*;

/**
 *
 * @author user
 */
public interface controller_mhs {
    public void Simpan(form_mhs mhs) throws SQLException;
    public void Ubah(form_mhs mhs) throws SQLException;
    public void Hapus(form_mhs mhs) throws SQLException;
    public void Tampil(form_mhs mhs) throws SQLException;
    public void Baru(form_mhs mhs) throws SQLException;
    public void KlikTabel(form_mhs mhs) throws SQLException;
}

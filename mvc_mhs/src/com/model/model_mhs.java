/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.model;

import com.controller.controller_mhs;
import com.koneksi.koneksi;
import com.view.form_mhs;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author user
 */
public class model_mhs implements controller_mhs {
    String jk;
    @Override
    public void Simpan(form_mhs mhs) throws SQLException {
        if (mhs.rbLaki.isSelected()){
            jk = "Laki - Laki";
        } else {
            jk = "Perempuan";
        }
        
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "Insert data_mhs values(?,?,?,?)";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, mhs.txtNim.getText());
            prepare.setString(2, mhs.txtNama.getText());
            prepare.setString(3, jk);
            prepare.setString(4, (String) mhs.cbJurusan.getSelectedItem());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data berhasil di Simpan");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(mhs);
            mhs.setLebarKolom();
        }
    }

    @Override
    public void Ubah(form_mhs mhs) throws SQLException {
        if (mhs.rbLaki.isSelected()){
            jk = "Laki - Laki";
        } else {
            jk = "Perempuan";
        }
        
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "Update data_mhs set nama= ?, jenis_kelamin= ?, jurusan= ? where nim= ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(4, mhs.txtNim.getText());
            prepare.setString(1, mhs.txtNama.getText());
            prepare.setString(2, jk);
            prepare.setString(3, (String) mhs.cbJurusan.getSelectedItem());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data berhasil di Ubah");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(mhs);
            mhs.setLebarKolom();
            Baru(mhs);
        }
    }

    @Override
    public void Hapus(form_mhs mhs) throws SQLException {
        try {
            Connection con = koneksi.getKoneksi();
            String sql = "delete from data_mhs where nim= ?";
            PreparedStatement prepare = con.prepareStatement(sql);
            
            prepare.setString(1, mhs.txtNim.getText());
            prepare.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Data berhasil di Hapus");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(mhs);
            mhs.setLebarKolom();
            Baru(mhs);
        }
    }

    @Override
    public void Tampil(form_mhs mhs) throws SQLException {
        mhs.tblmodel.getDataVector().removeAllElements();
        mhs.tblmodel.fireTableDataChanged();
        try {
            Connection con = koneksi.getKoneksi();
            Statement stt = con.createStatement();
            String sql = "select * from data_mhs order by nim asc";
            ResultSet res = stt.executeQuery(sql);
            while(res.next()){
                Object[] ob = new Object[4];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(3);
                ob[3] = res.getString(4);
                mhs.tblmodel.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void Baru(form_mhs mhs) throws SQLException {
        mhs.txtNim.setText("");
        mhs.txtNama.setText("");
        mhs.jk.clearSelection();
        mhs.cbJurusan.setSelectedIndex(0);
    }

    @Override
    public void KlikTabel(form_mhs mhs) throws SQLException {
        try {
            int pilih = mhs.table.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            mhs.txtNim.setText(mhs.tblmodel.getValueAt(pilih, 0).toString());
            mhs.txtNama.setText(mhs.tblmodel.getValueAt(pilih, 1).toString());
            mhs.cbJurusan.setSelectedItem(mhs.tblmodel.getValueAt(pilih, 3).toString());
            jk = String.valueOf(mhs.tblmodel.getValueAt(pilih, 2));
        } catch (Exception e) {
            System.out.println(e);
        }
        if (mhs.rbLaki.getText().equals(jk)) {
            mhs.rbLaki.setSelected(true);
        } else {
            mhs.rbPerempuan.setSelected(true);
        }
    }
    
}

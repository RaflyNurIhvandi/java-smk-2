/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Karyawan;

import Koneksi.Db_Koneksi;
import com.mysql.jdbc.PreparedStatement;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.*;

/**
 *
 * @author user
 */
public class Form_Gaji extends javax.swing.JFrame {

    /**
     * Creates new form Form_Gaji
     */
    private DefaultTableModel model;
    String nip, nama, jabatan;
    int gapok, transport, gaber;
    
    public Form_Gaji() {
        initComponents();
        
        model = new DefaultTableModel();
        tblGaji.setModel(model);
        model.addColumn("NIP");
        model.addColumn("Nama");
        model.addColumn("Jabatan");
        model.addColumn("Gaji Pokok");
        model.addColumn("Transport");
        model.addColumn("Gaji Bersih");
        
        getData();
    }
    
    public void getData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try {
            Statement start = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "Select * from tbl_gaji";
            ResultSet res = start.executeQuery(sql);
            
            while(res.next()){
                Object[] obj = new Object[6];
                obj[0] = res.getString("Nip");
                obj[1] = res.getString("Nama");
                obj[2] = res.getString("Jabatan");
                obj[3] = res.getString("Gapok");
                obj[4] = res.getString("Transport");
                obj[5] = res.getString("Gaber");
                
                model.addRow(obj);
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void loadData(){
        nip = txtNip.getText();
        nama = txtNama.getText();
        jabatan = (String) comboxJabatan.getSelectedItem();
        gapok = Integer.parseInt(txtGapok.getText());
        transport = Integer.parseInt(txtTransport.getText());
        gaber = Integer.parseInt(txtGaber.getText());
    }
    
    public void loadGaji(){
        jabatan = ""+comboxJabatan.getSelectedItem();
        switch(jabatan){
            case "Manager":
                gapok = 5000000;
                break;
            case "Asisten Manager":
                gapok = 4500000;
                break;
            case "Kepala HRD":
                gapok = 4000000;
                break;
            case "Staf Keuangan":
                gapok = 3500000;
                break;
            case "Karyawan":
                gapok = 3000000;
                break;
            case "Office Boy":
                gapok = 2500000;
                break;
        }
        transport = (int) (gapok * 0.1);
        gaber = gapok + transport;
        txtGapok.setText(""+gapok);
        txtTransport.setText(""+transport);
        txtGaber.setText(""+gaber);
    }
    
//    save data
    public void saveData(){
        loadData();
        try {
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "insert into tbl_gaji (Nip,Nama,Jabatan,Gapok,Transport,Gaber) values ('"+nip+"','"+nama+"','"+jabatan+"','"+gapok+"','"+transport+"','"+gaber+"')";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            getData();
            Reset();
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

//  reset data
    public void Reset(){
        nip = "";
        nama = "";
        jabatan = "";
        gapok = 0;
        transport = 0;
        transport = 0;
        txtNip.setText(nip);
        txtNama.setText(nama);
        txtGapok.setText("");
        txtTransport.setText("");
        txtGaber.setText("");
    }
    
//    data select
    public void dataSelect(){
        int i = tblGaji.getSelectedRow();
        if (i == -1){
            return;
        }
        txtNip.setText(""+model.getValueAt(i, 0));
        txtNama.setText(""+model.getValueAt(i, 1));
        comboxJabatan.setSelectedItem(""+model.getValueAt(i, 2));
        txtGapok.setText(""+model.getValueAt(i, 3));
        txtTransport.setText(""+model.getValueAt(i, 4));
        txtGaber.setText(""+model.getValueAt(i, 5));
    }
    
//    update
    public void updateData(){
        loadData();
        try {
            Statement start = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "UPDATE tbl_gaji SET Nama = '"+nama+"',"
                                            + "Jabatan = '"+jabatan+"',"
                                            + "Gapok = '"+gapok+"',"
                                            + "Transport = '"+transport+"',"
                                            + "Gaber = '"+gaber+"' "
                                            + "WHERE Nip = '"+nip+"'";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            getData();
            Reset();
            JOptionPane.showMessageDialog(null, "Update Berhasil...");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
//    delete
    public void deleteData(){
        loadData();
        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin menghapus data "+nip+"?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == JOptionPane.OK_OPTION){
            try {
                Statement start = (Statement) Db_Koneksi.getKoneksi().createStatement();
                String sql = "DELETE FROM tbl_gaji WHERE Nip = '"+nip+"'";
                PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                getData();
                Reset();
                JOptionPane.showMessageDialog(null, "Delete Berhasil...");
            } catch (SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
        }
    }
    
    public void FilterHuruf(KeyEvent a){
        if(Character.isDigit(a.getKeyChar())){
            a.consume();
            JOptionPane.showMessageDialog(null, "Masukan Hanya Huruf", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void FilterAngka(KeyEvent b){
        if(Character.isAlphabetic(b.getKeyChar())){
            b.consume();
            JOptionPane.showMessageDialog(null, "Masukan Hanya Angka", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblGaji = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNip = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        comboxJabatan = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtGapok = new javax.swing.JTextField();
        txtGaber = new javax.swing.JTextField();
        txtTransport = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblGaji.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", null},
                {"", null, null, null},
                {"", null, null, null},
                {null, null, "", null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblGaji.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGajiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGaji);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Data Gaji Karyawan");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("NIP");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Nama");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Jabatan");

        txtNip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNipActionPerformed(evt);
            }
        });
        txtNip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNipKeyTyped(evt);
            }
        });

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });
        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNamaKeyTyped(evt);
            }
        });

        comboxJabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manager", "Asisten Manager", "Kepala HRD", "Staf Keuangan", "Karyawan", "Office Boy" }));
        comboxJabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboxJabatanActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Gaji Pokok");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setText("Transport");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Gaji Bersih");

        txtGapok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGapokActionPerformed(evt);
            }
        });

        txtGaber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGaberActionPerformed(evt);
            }
        });

        txtTransport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTransportActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(0, 255, 102));
        btnSave.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 255, 0));
        btnReset.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnReset.setForeground(new java.awt.Color(153, 153, 153));
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(204, 0, 204));
        btnUpdate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 0, 0));
        btnDelete.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnKeluar.setBackground(new java.awt.Color(102, 102, 102));
        btnKeluar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnKeluar.setForeground(new java.awt.Color(255, 255, 255));
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel8.setText("Copyright © 2024 Rafly Nur Ihvandi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(60, 60, 60)
                                        .addComponent(txtNip, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                            .addComponent(comboxJabatan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtGaber, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGapok, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTransport, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnKeluar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtGapok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTransport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(comboxJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtGaber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnReset)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnKeluar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNipActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtGapokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGapokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGapokActionPerformed

    private void txtGaberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGaberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGaberActionPerformed

    private void txtTransportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTransportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransportActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveData();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        Reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateData();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteData();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void comboxJabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxJabatanActionPerformed
        loadGaji();
    }//GEN-LAST:event_comboxJabatanActionPerformed

    private void tblGajiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGajiMouseClicked
        dataSelect();
    }//GEN-LAST:event_tblGajiMouseClicked

    private void txtNipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNipKeyTyped
        FilterAngka(evt);
    }//GEN-LAST:event_txtNipKeyTyped

    private void txtNamaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyTyped
        FilterHuruf(evt);
    }//GEN-LAST:event_txtNamaKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Gaji.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Gaji().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> comboxJabatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGaji;
    private javax.swing.JTextField txtGaber;
    private javax.swing.JTextField txtGapok;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNip;
    private javax.swing.JTextField txtTransport;
    // End of variables declaration//GEN-END:variables
}

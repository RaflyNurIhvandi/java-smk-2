/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package siswa;

import koneksi.db;
import com.mysql.jdbc.PreparedStatement;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.*;
/**
 *
 * @author user
 */
public class form_pendaftaran extends javax.swing.JFrame {
    private DefaultTableModel model, tbl_hid;
    String nama, jenis_kelamin, no_hp, asal_sekolah, tempat_lahir, tanggal_lahir, no_akta, kwn, agama, alamat;
    String jenis_kelamin_hidden, tanggal_lahir_hidden, id_data;
    String laki = "Laki - Laki", cw = "Perempuan";

    /**
     * Creates new form form_pendaftaran
     */
    public form_pendaftaran() {
        initComponents();
        model = new DefaultTableModel();
        tbl_dtsiswa.setModel(model);
        model.addColumn("Id");
        model.addColumn("Nama");
        model.addColumn("JK");
        model.addColumn("No. HP");
        model.addColumn("Tempat, Tanggal Lahir");
        model.addColumn("KWN");
        model.addColumn("Alamat");
        
        tbl_hid = new DefaultTableModel();
        tbl_hidden.setModel(tbl_hid);
        tbl_hid.addColumn("Id");
        tbl_hid.addColumn("Nama");
        tbl_hid.addColumn("Hp");
        tbl_hid.addColumn("Asal Sekolah");
        tbl_hid.addColumn("Tempat Lahir");
        tbl_hid.addColumn("No Akta");
        tbl_hid.addColumn("KWN");
        tbl_hid.addColumn("Agama");
        tbl_hid.addColumn("Alamat");
        tbl_hid.addColumn("Jenis Kelamin");
        tbl_hid.addColumn("Tanggal Lahir");
        
        getData();
        getDataHid();
    }
    
//    get data
    public void getData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try {
            Statement start = (Statement) db.getKoneksi().createStatement();
            String sql = "Select * from data_siswa";
            ResultSet res = start.executeQuery(sql);
            
            while(res.next()){
                Object[] obj = new Object[7];
                obj[0] = res.getString("id");
                obj[1] = res.getString("nama");
                obj[2] = res.getString("jenis_kelamin");
                obj[3] = res.getString("no_hp");
                obj[4] = res.getString("tempat_lahir") + ", "+ res.getString("tanggal_lahir");
                obj[5] = res.getString("kewarganegaraan");
                obj[6] = res.getString("alamat");
                
                model.addRow(obj);
            }
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    public void getDataHid(){
        tbl_hid.getDataVector().removeAllElements();
        tbl_hid.fireTableDataChanged();
        
        try {
            Statement start = (Statement) db.getKoneksi().createStatement();
            String sql = "Select * from data_siswa";
            ResultSet res = start.executeQuery(sql);
            
            while(res.next()){
                Object[] obj = new Object[12];
                obj[0] = res.getString("nama");
                obj[1] = res.getString("no_hp");
                obj[2] = res.getString("asal_sekolah");
                obj[3] = res.getString("tempat_lahir");
                obj[4] = res.getString("no_akta_kelahiran");
                obj[5] = res.getString("kewarganegaraan");
                obj[6] = res.getString("agama");
                obj[7] = res.getString("alamat");
                obj[8] = res.getString("jenis_kelamin");
                obj[9] = res.getString("tanggal_lahir");
                obj[10] = res.getString("id");
                
                tbl_hid.addRow(obj);
            }
//            tbl_hid.setVisible(true);
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
//    load data
    public void loadData(){
        nama = txtNama.getText();
        jenis_kelamin = String.valueOf(jenisKelamin);
        no_hp = txtHP.getText();
        asal_sekolah = txtAsal.getText();
        tempat_lahir = txtTempatLahir.getText();
        tanggal_lahir = (String) txtBulan.getSelectedItem()+"/"+ txtTanggal.getSelectedItem()+"/"+ txtTahun.getSelectedItem();
        no_akta = txtAkta.getText();
        kwn = txtKWN.getText();
        agama = txtAgama.getText();
        alamat = txtAlamat.getText();
        if (LakiLaki.isSelected()){
            jenis_kelamin = "Laki - Laki";
        } else if (Perempuan.isSelected()){
            jenis_kelamin = "Perempuan";
        } else {
            jenis_kelamin = "Kosong";
        }
    }
    
//    save
    public void saveData(){
        loadData();
        try {
            Statement stat = (Statement) db.getKoneksi().createStatement();
            String sql = "insert into data_siswa (nama,jenis_kelamin,no_hp,asal_sekolah,tempat_lahir,tanggal_lahir,no_akta_kelahiran,kewarganegaraan,agama,alamat) values ('"+nama+"','"+jenis_kelamin+"','"+no_hp+"','"+asal_sekolah+"','"+tempat_lahir+"','"+tanggal_lahir+"','"+no_akta+"','"+kwn+"','"+agama+"','"+alamat+"')";
            PreparedStatement p = (PreparedStatement) db.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            getData();
            Reset();
            JOptionPane.showMessageDialog(null, "Data siswa berhasil disimpan");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

//    reset
    public void Reset(){
        id_data = "";
        nama = "";
        jenis_kelamin = "";
        no_hp = "";
        asal_sekolah = "";
        tempat_lahir = "";
        tanggal_lahir = "";
        no_akta = "";
        kwn = "";
        agama = "";
        alamat = "";
        txtNama.setText("");
        jenisKelamin.clearSelection();
        txtHP.setText("");
        txtAsal.setText("");
        txtTempatLahir.setText("");
        txtBulan.setSelectedIndex(0);
        txtTanggal.setSelectedIndex(0);
        txtTahun.setSelectedIndex(0);
        txtAkta.setText("");
        txtKWN.setText("");
        txtAgama.setText("");
        txtAlamat.setText("");
    }
    
//    data select
    public void dataSelect(){
        int i = tbl_dtsiswa.getSelectedRow();
        if (i == -1){
            return;
        }
        id_data = (String) tbl_hid.getValueAt(i, 10);
        txtNama.setText(""+tbl_hid.getValueAt(i, 0));
        txtHP.setText(""+tbl_hid.getValueAt(i, 1));
        txtAsal.setText(""+tbl_hid.getValueAt(i, 2));
        txtTempatLahir.setText(""+tbl_hid.getValueAt(i, 3));
        txtAkta.setText(""+tbl_hid.getValueAt(i, 4));
        txtKWN.setText(""+tbl_hid.getValueAt(i, 5));
        txtAgama.setText(""+tbl_hid.getValueAt(i, 6));
        txtAlamat.setText(""+tbl_hid.getValueAt(i, 7));
        jenis_kelamin_hidden = (String) tbl_hid.getValueAt(i, 8);
        if (jenis_kelamin_hidden.equals("Laki - Laki")){
            LakiLaki.setSelected(true);
        } else if (jenis_kelamin_hidden.equals("Perempuan")){
            Perempuan.setSelected(true);
        }
        tanggal_lahir_hidden = (String) tbl_hid.getValueAt(i, 9);
        String[] tgl = tanggal_lahir_hidden.split("/");
        txtBulan.setSelectedItem(tgl[0]);
        txtTanggal.setSelectedItem(tgl[1]);
        txtTahun.setSelectedItem(tgl[2]);
    }
    
//    update
    public void updateData(){
        loadData();
        try {
            Statement start = (Statement) db.getKoneksi().createStatement();
            String sql = "UPDATE data_siswa SET nama = '"+nama+"',"
                                            + "jenis_kelamin = '"+jenis_kelamin+"',"
                                            + "no_hp = '"+no_hp+"',"
                                            + "asal_sekolah = '"+asal_sekolah+"',"
                                            + "tempat_lahir = '"+tempat_lahir+"',"
                                            + "tanggal_lahir = '"+tanggal_lahir+"',"
                                            + "no_akta_kelahiran = '"+no_akta+"',"
                                            + "kewarganegaraan = '"+kwn+"',"
                                            + "agama = '"+agama+"',"
                                            + "alamat = '"+alamat+"' "
                                            + "WHERE id = '"+id_data+"'";
            PreparedStatement p = (PreparedStatement) db.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            getData();
            Reset();
            JOptionPane.showMessageDialog(null, "Data siswa berhasil diupdate");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
//    delete
    public void deleteData(){
        loadData();
        int pesan = JOptionPane.showConfirmDialog(null, "Anda yakin menghapus data siswa atas nama "+nama+"?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == JOptionPane.OK_OPTION){
            try {
                Statement start = (Statement) db.getKoneksi().createStatement();
                String sql = "DELETE FROM data_siswa WHERE id = '"+id_data+"'";
                PreparedStatement p = (PreparedStatement) db.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                getData();
                Reset();
                JOptionPane.showMessageDialog(null, "Data siswa berhasil dihapus");
            } catch (SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
        }
    }
    
    public void JumlahKarakter(KeyEvent e){
        if(txtHP.getText().length() == 15){
            e.consume();
            JOptionPane.showMessageDialog(null, "Maksimal yang dimasukan Hanya 15 Karakter", "Peringatan", JOptionPane.WARNING_MESSAGE);
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

        jenisKelamin = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtHP = new javax.swing.JTextField();
        txtAsal = new javax.swing.JTextField();
        txtTempatLahir = new javax.swing.JTextField();
        LakiLaki = new javax.swing.JRadioButton();
        Perempuan = new javax.swing.JRadioButton();
        txtBulan = new javax.swing.JComboBox<>();
        txtTanggal = new javax.swing.JComboBox<>();
        txtTahun = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtAkta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtKWN = new javax.swing.JTextField();
        txtAgama = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_dtsiswa = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_hidden = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 209, 227));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(93, 53, 135));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Pendaftaran Siswa Baru");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(93, 53, 135));
        jLabel2.setText("Nama");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(93, 53, 135));
        jLabel3.setText("Jenis Kelamin");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(93, 53, 135));
        jLabel4.setText("No HP");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(93, 53, 135));
        jLabel5.setText("Asal Sekolah");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(93, 53, 135));
        jLabel6.setText("Tempat Lahir");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(93, 53, 135));
        jLabel7.setText("Tanggal Lahir");

        txtNama.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtNama.setForeground(new java.awt.Color(163, 103, 177));
        txtNama.setCaretColor(new java.awt.Color(163, 103, 177));
        txtNama.setSelectedTextColor(new java.awt.Color(163, 103, 177));
        txtNama.setSelectionColor(new java.awt.Color(163, 103, 177));
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

        txtHP.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtHP.setForeground(new java.awt.Color(163, 103, 177));
        txtHP.setCaretColor(new java.awt.Color(163, 103, 177));
        txtHP.setSelectedTextColor(new java.awt.Color(163, 103, 177));
        txtHP.setSelectionColor(new java.awt.Color(163, 103, 177));
        txtHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHPActionPerformed(evt);
            }
        });
        txtHP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHPKeyTyped(evt);
            }
        });

        txtAsal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtAsal.setForeground(new java.awt.Color(163, 103, 177));
        txtAsal.setCaretColor(new java.awt.Color(163, 103, 177));
        txtAsal.setSelectedTextColor(new java.awt.Color(163, 103, 177));
        txtAsal.setSelectionColor(new java.awt.Color(163, 103, 177));
        txtAsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAsalActionPerformed(evt);
            }
        });
        txtAsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAsalKeyTyped(evt);
            }
        });

        txtTempatLahir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTempatLahir.setForeground(new java.awt.Color(163, 103, 177));
        txtTempatLahir.setCaretColor(new java.awt.Color(163, 103, 177));
        txtTempatLahir.setSelectedTextColor(new java.awt.Color(163, 103, 177));
        txtTempatLahir.setSelectionColor(new java.awt.Color(163, 103, 177));
        txtTempatLahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTempatLahirActionPerformed(evt);
            }
        });
        txtTempatLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTempatLahirKeyTyped(evt);
            }
        });

        jenisKelamin.add(LakiLaki);
        LakiLaki.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        LakiLaki.setForeground(new java.awt.Color(163, 103, 177));
        LakiLaki.setText("Laki - Laki");
        LakiLaki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LakiLakiActionPerformed(evt);
            }
        });

        jenisKelamin.add(Perempuan);
        Perempuan.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Perempuan.setForeground(new java.awt.Color(163, 103, 177));
        Perempuan.setText("Perempuan");
        Perempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerempuanActionPerformed(evt);
            }
        });

        txtBulan.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtBulan.setForeground(new java.awt.Color(163, 103, 177));
        txtBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MM", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        txtBulan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtTanggal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTanggal.setForeground(new java.awt.Color(163, 103, 177));
        txtTanggal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        txtTahun.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTahun.setForeground(new java.awt.Color(163, 103, 177));
        txtTahun.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "YYYY", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012" }));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(93, 53, 135));
        jLabel8.setText("No. Akta");

        txtAkta.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtAkta.setForeground(new java.awt.Color(163, 103, 177));
        txtAkta.setCaretColor(new java.awt.Color(163, 103, 177));
        txtAkta.setSelectedTextColor(new java.awt.Color(163, 103, 177));
        txtAkta.setSelectionColor(new java.awt.Color(163, 103, 177));
        txtAkta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAktaActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(93, 53, 135));
        jLabel9.setText("Kewarganegaraan");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(93, 53, 135));
        jLabel10.setText("Agama");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(93, 53, 135));
        jLabel11.setText("Alamat");

        txtKWN.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtKWN.setForeground(new java.awt.Color(163, 103, 177));
        txtKWN.setCaretColor(new java.awt.Color(163, 103, 177));
        txtKWN.setSelectedTextColor(new java.awt.Color(163, 103, 177));
        txtKWN.setSelectionColor(new java.awt.Color(163, 103, 177));
        txtKWN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKWNActionPerformed(evt);
            }
        });

        txtAgama.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtAgama.setForeground(new java.awt.Color(163, 103, 177));
        txtAgama.setCaretColor(new java.awt.Color(163, 103, 177));
        txtAgama.setSelectedTextColor(new java.awt.Color(163, 103, 177));
        txtAgama.setSelectionColor(new java.awt.Color(163, 103, 177));
        txtAgama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAgamaActionPerformed(evt);
            }
        });

        txtAlamat.setColumns(10);
        txtAlamat.setForeground(new java.awt.Color(163, 103, 177));
        txtAlamat.setLineWrap(true);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        tbl_dtsiswa.setAutoCreateRowSorter(true);
        tbl_dtsiswa.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tbl_dtsiswa.setForeground(new java.awt.Color(163, 103, 177));
        tbl_dtsiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_dtsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_dtsiswaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_dtsiswa);

        jButton1.setBackground(new java.awt.Color(51, 255, 0));
        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 51));
        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 0, 204));
        jButton3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Update");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 51, 51));
        jButton4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(204, 204, 204));
        jButton5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(51, 51, 51));
        jButton5.setText("Keluar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tbl_hidden.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
            }
        ));
        jScrollPane3.setViewportView(tbl_hidden);

        jLabel12.setText("Copyright © 2024 Rafly Nur Ihvandi");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNama)
                    .addComponent(txtHP)
                    .addComponent(txtAsal)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(LakiLaki)
                                .addGap(18, 18, 18)
                                .addComponent(Perempuan)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTempatLahir))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtAgama)
                    .addComponent(txtKWN, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAkta, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(29, 29, 29)
                                .addComponent(jButton2)
                                .addGap(31, 31, 31)
                                .addComponent(jButton3)
                                .addGap(26, 26, 26)
                                .addComponent(jButton4)
                                .addGap(27, 27, 27)
                                .addComponent(jButton5)))
                        .addGap(123, 123, 123))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(251, 251, 251))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtAkta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtKWN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtAgama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(LakiLaki)
                            .addComponent(Perempuan))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtAsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtHPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHPActionPerformed

    private void txtAsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAsalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAsalActionPerformed

    private void txtTempatLahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTempatLahirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTempatLahirActionPerformed

    private void LakiLakiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LakiLakiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LakiLakiActionPerformed

    private void PerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerempuanActionPerformed

    private void txtAktaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAktaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAktaActionPerformed

    private void txtKWNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKWNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKWNActionPerformed

    private void txtAgamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAgamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAgamaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        saveData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Reset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        updateData();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        deleteData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tbl_dtsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_dtsiswaMouseClicked
        dataSelect();
    }//GEN-LAST:event_tbl_dtsiswaMouseClicked

    private void txtHPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHPKeyTyped
        JumlahKarakter(evt);
    }//GEN-LAST:event_txtHPKeyTyped

    private void txtNamaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyTyped
        FilterHuruf(evt);
    }//GEN-LAST:event_txtNamaKeyTyped

    private void txtAsalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAsalKeyTyped
        FilterHuruf(evt);
    }//GEN-LAST:event_txtAsalKeyTyped

    private void txtTempatLahirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTempatLahirKeyTyped
        FilterHuruf(evt);
    }//GEN-LAST:event_txtTempatLahirKeyTyped

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
            java.util.logging.Logger.getLogger(form_pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_pendaftaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_pendaftaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton LakiLaki;
    private javax.swing.JRadioButton Perempuan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.ButtonGroup jenisKelamin;
    private javax.swing.JTable tbl_dtsiswa;
    private javax.swing.JTable tbl_hidden;
    private javax.swing.JTextField txtAgama;
    private javax.swing.JTextField txtAkta;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtAsal;
    private javax.swing.JComboBox<String> txtBulan;
    private javax.swing.JTextField txtHP;
    private javax.swing.JTextField txtKWN;
    private javax.swing.JTextField txtNama;
    private javax.swing.JComboBox<String> txtTahun;
    private javax.swing.JComboBox<String> txtTanggal;
    private javax.swing.JTextField txtTempatLahir;
    // End of variables declaration//GEN-END:variables
}

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class ProjectBarang1 extends Frame {
    public JFrame frame = new JFrame();

    //  Buat Objek Label
    public JLabel lblKode = new JLabel("Kode");
    public JLabel lblNama = new JLabel("Nama");
    public JLabel lblStok = new JLabel("Stok");
    public JLabel lblSatuan = new JLabel("Satuan");
    public JLabel lblHarga = new JLabel("Harga");

//  Buat Objek TextField
    public JTextField txtKode;
    public JTextField txtNama;
    public JTextField txtStok;
    public JTextField txtSatuan;
    public JTextField txtHarga;

//  Buat Button simpan
    JButton btnSimpan = new JButton("Simpan");
    JButton btnUbah = new JButton("Ubah");
    JButton btnDelete = new JButton("Hapus");
    JButton btnNew = new JButton("Baru");
    JButton btnPDF = new JButton("Report PDF");

    //step table 1
    public JTable table = new JTable();
    public JScrollPane scroll = new JScrollPane(table);
    public String[] columnNames = {"kode", "nama", "stok", "satuan", "harga"};
    public DefaultTableModel model = new DefaultTableModel();
    // langkah  1
    public Connection koneksi = Koneksi.getKoneksi();

    // step 1
    public static void main(String[] args) {
        ProjectBarang1 frame = new ProjectBarang1(); //buat objek
        // Show frame
        frame.setVisible(true);
    }

    // step 2
    public ProjectBarang1() {
        createForm();
        listBarang();
        buttonKlik();
    }

// step 3
    public final void createForm() {
        txtKode = new JTextField();
        txtNama = new JTextField();
        txtStok = new JTextField();
        txtSatuan = new JTextField();
        txtHarga = new JTextField();
//  Set judul frame
        frame.setTitle("Data Barang"); //barang itu dari nama table databasenya
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//  Set ukuran frame
        frame.setSize(700, 400);// 700 lebar 400 tinggi

//  Set Posisi frame berada di tengah layar
        frame.setLocationRelativeTo(null);

//  [Optional] Set tombol maximize menjadi disabled
        frame.setResizable(false);

//  Set program agar program berhenti ketika tombol close di klik di frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//  Set Visible frame  agar Frame muncul ketika program di running
        frame.setVisible(true);

//  Set Layout Frame
        frame.setLayout(null);

//  Memasukkan komponen Label, TextField dan Button ke dalam Frame
        frame.add(lblKode);
        frame.add(lblNama);
        frame.add(lblStok);
        frame.add(lblSatuan);
        frame.add(lblHarga);

        frame.add(txtKode);
        frame.add(txtNama);
        frame.add(txtStok);
        frame.add(txtSatuan);
        frame.add(txtHarga);

        frame.add(btnSimpan);
        frame.add(btnUbah);
        frame.add(btnDelete);
        frame.add(btnNew);
        frame.add(btnPDF);

//  Menentukan posisi komponen Label, TextField dan Button di dalam Frame menggunakan koordinat X dan Y
        lblKode.setBounds(20, 20, 100, 20);//setting koordinat 
        lblNama.setBounds(20, 50, 100, 20);
        lblStok.setBounds(20, 80, 100, 20);
        lblSatuan.setBounds(20, 110, 100, 20);
        lblHarga.setBounds(20, 140, 100, 20);

        btnSimpan.setBounds(20, 180, 100, 20);
        btnUbah.setBounds(20, 210, 100, 20);
        btnDelete.setBounds(20, 240, 100, 20);
        btnNew.setBounds(20, 270, 100, 20);
        btnPDF.setBounds(20, 300, 100, 20);

        txtKode.setBounds(100, 20, 130, 20);
        txtNama.setBounds(100, 50, 130, 20);
        txtStok.setBounds(100, 80, 130, 20);
        txtSatuan.setBounds(100, 110, 130, 20);
        txtHarga.setBounds(100, 140, 130, 20);

        // step 2
        frame.add(scroll);
        table.setModel(model);
        scroll.setBounds(300, 20, 300, 300);

    }

    // step 2
    public final  void listBarang() {
        // untuk set row 0 default,jika di call oleh method simpan , refresh data
        model.setNumRows(0);
// step 1 : query
        String sql = "select kode, nama, stok, satuan, harga from barang";
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        try {

            // set header tabel 
            model.setColumnIdentifiers(columnNames);

            Statement st = koneksi.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //object dari resultset menampung hasilnya
            ResultSet rs = st.executeQuery(sql);

            int i = 0;
            //selama masih ada data (next)
            // step 2: get data query
            while (rs.next()) {
                String kode = rs.getString("kode");
                String nama = rs.getString("nama");
                int stok = rs.getInt("stok");
                String satuan = rs.getString("satuan");
                int harga = rs.getInt("harga");
                // step 3: tampilkan pada objek tabel
                model.addRow(new Object[]{kode, nama, stok, satuan, harga});
                i++;
            }

            if (i < 1) {
                JOptionPane.showMessageDialog(null, "No Record Found", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (i == 1) {
                System.out.println(i + " Record Found");
            } else {
                System.out.println(i + " Records Found");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Eror get table=" + ex, "Pesan", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }

    }

    public void simpanBarang() {
        try {

            String query = "INSERT INTO barang(kode,nama,stok,satuan,harga) VALUES(?,?,?,?,?)";
            try (PreparedStatement prepare = koneksi.prepareStatement(query)) {
                prepare.setString(1, txtKode.getText());
                prepare.setString(2, txtNama.getText());
                prepare.setInt(3, Integer.parseInt(txtStok.getText()));
                prepare.setString(4, txtSatuan.getText());
                prepare.setString(5, txtHarga.getText());
                
                // execute query :
                prepare.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Pesan", JOptionPane.INFORMATION_MESSAGE);
            }

            //panggil table / refresh
            listBarang();
            kosongkanTextfield();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan=" + ex, "Pesan", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }

    }

    public void kosongkanTextfield() {
        txtKode.setText("");
        txtNama.setText("");
        txtStok.setText("");
        txtSatuan.setText("");
        txtHarga.setText("");
        txtKode.setEnabled(true);

    }

    public final void buttonKlik() {
        btnSimpan.addActionListener((ActionEvent act) -> {
            if (chekValidasi()) {
                if (chekDUplikatKode()) {
                    simpanBarang();
                } else {
                    JOptionPane.showMessageDialog(null, "Kode " + txtKode.getText() + " sudah ada. Silahkan isi kode lain.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Silahkan Lengkapi data", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    public boolean chekValidasi() {
        return !(txtKode.getText().equals("")
                || txtNama.getText().equals("")
                || txtStok.getText().equals("")
                || txtSatuan.getText().equals("")
                || txtHarga.getText().equals(""));
    }

    public boolean chekDUplikatKode() {
        try {
            String sql = "select kode, nama, stok, satuan, harga from barang where kode ='" + txtKode.getText() + "'";
            Statement st = koneksi.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            if (rs.first()) {
                // gagal insert
                System.out.println("gagal insert");
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "chek kode eror=" + ex, "Pesan", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
        return true;
    }

}



import java.sql.Connection;
import java.sql.DriverManager;


public class Koneksi {

    private static Connection koneksi;
    public static void main(String[] args) {
      getKoneksi();
    }
    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                //sesuaikan dengan port yang digunakan dan database yang dibuat
                String url = "jdbc:mysql://localhost:3306/kmmi";//kmmi itu sesuai dengan nama database
                //sesuaikan dengan user mysql
                String user = "root";
                 //sesuaikan dengan password mysql
                String password = "";

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = (Connection) DriverManager.getConnection(url, user, password);
                System.out.println("koneksi sukses");
            } catch (Exception ex) {
                System.out.println("koneksi eror="+ex);
            }
        }
        return koneksi;
    }
}

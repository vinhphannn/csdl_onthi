/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package on_thi_csdl2;
import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.*;

//zinhhhh
public class connect_mysql {
    public Connection getConnection(String dbURL, String userName, String password)

        {

            Connection conn = null;

            try {

                Class.forName("com.mysql.jdbc.Driver");

                conn = DriverManager.getConnection(dbURL, userName, password);

                System.out.println("Connect Successfully!");

                } catch (Exception ex) {

                    System.out.println("Connect Failure!");

                    ex.printStackTrace();

                  }

        return conn;

    }
}
// -- Bảng thể loại sách
//CREATE TABLE the_loai (
//    MaTheLoai INT PRIMARY KEY,
//    TenTheLoai VARCHAR(150),
//    MoTa VARCHAR(500)
//);
//
//-- Bảng sách
//CREATE TABLE sach (
//    MaSach INT PRIMARY KEY,
//    TenSach VARCHAR(150),
//    MaTheLoai INT,
//    Gia DOUBLE,
//    SoLuong INT,
//    MoTa VARCHAR(500),
//    HinhAnh VARCHAR(200),
//    FOREIGN KEY (MaTheLoai) REFERENCES the_loai(MaTheLoai)
//);
//
//-- Dữ liệu ví dụ cho bảng the_loai
//INSERT INTO the_loai (MaTheLoai, TenTheLoai, MoTa) VALUES
//(1, 'Văn học', 'Sách văn học từ cổ điển đến hiện đại'),
//(2, 'Khoa học', 'Sách về khoa học tự nhiên, công nghệ và đời sống'),
//(3, 'Thiếu nhi', 'Sách dành cho trẻ em, truyện tranh và truyện cổ tích');
//
//-- Dữ liệu ví dụ cho bảng sach
//INSERT INTO sach (MaSach, TenSach, MaTheLoai, Gia, SoLuong, MoTa, HinhAnh) VALUES
//(1, 'Truyện Kiều', 1, 120000, 50, 'Tác phẩm nổi tiếng của Nguyễn Du', 'truyen_kieu.jpg'),
//(2, 'Vật lý cơ bản', 2, 90000, 30, 'Sách giáo khoa vật lý cho học sinh trung học', 'vat_ly_co_ban.jpg'),
//(3, 'Truyện cổ tích Việt Nam', 3, 50000, 100, 'Tổng hợp truyện cổ tích nổi tiếng của Việt Nam', 'truyen_co_tich.jpg'),
//(4, 'Thép đã tôi thế đấy', 1, 70000, 200, 'Tác phẩm kinh điển của văn học Xô Viết', 'thep_da_toi.jpg'),
//(5, 'Khám phá vũ trụ', 2, 150000, 80, 'Sách về những khám phá mới nhất về vũ trụ', 'kham_pha_vu_tru.jpg');
//           
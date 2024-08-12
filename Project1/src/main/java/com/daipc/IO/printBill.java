/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.IO;

import com.daipc.model.ChiTietSP;
import com.daipc.model.HoaDon;
import com.daipc.model.HoaDonModel;
import com.daipc.model.SPCT;
import com.daipc.model.SanPham;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.pdfa.PdfADocument;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class printBill {
    
    public void printBill(HoaDonModel hoaDon, List<ChiTietSP> chiTietSPList) throws FileNotFoundException{
        try {
            String desktopPath = System.getProperty("user.home") + "/Desktop/print.pdf";
//            String path = "print.pdf";
            PdfWriter pdfWriter = new PdfWriter(desktopPath);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            float width = 120 * 2.8346f; 
            float height = PageSize.A4.getHeight();

            pdfDocument.setDefaultPageSize(new PageSize(width, height));
            Document document = new Document(pdfDocument);
            PdfFont font = PdfFontFactory.createFont("C:\\Users\\acer\\Downloads\\DuAn_1-main\\Project1\\forn\\timesbd.ttf", "Identity-H", true);
            
            Paragraph title = new Paragraph("Adidas")
                .setFontSize(24)
                .setFont(font)
                .setTextAlignment(TextAlignment.CENTER);
                document.add(title);

            Paragraph Duongke = new Paragraph("-------------------------------------------------------------------")
                .setTextAlignment(TextAlignment.CENTER);
                document.add(Duongke);

            // lấy file forn tiếng việt
           
            Paragraph DiaChi = new Paragraph("FPT Polytechnic, Kiều Mai, Từ Liêm, TP Hà Nội")
                .setFontSize(8)
                .setFont(font)
                .setTextAlignment(TextAlignment.CENTER);
                document.add(DiaChi);
                
            Paragraph title2 = new Paragraph("HÓA ĐƠN BÁN HÀNG")
                .setFontSize(12)
                .setFont(font)
                .setTextAlignment(TextAlignment.CENTER);
                document.add(title2);
            Paragraph maHD = new Paragraph("Mã hóa đơn: " + hoaDon.getMaHD())
                .setFontSize(10)
                .setFont(font)
                .setTextAlignment(TextAlignment.LEFT);
            document.add(maHD);

            Paragraph tenKH = new Paragraph("Khách hàng: " + hoaDon.getTenKH())
                .setFontSize(10)
                .setFont(font)
                .setTextAlignment(TextAlignment.LEFT);
            document.add(tenKH);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String ngayTao = sdf.format(hoaDon.getNgayTao());
            Paragraph date = new Paragraph("Ngày tạo: " + ngayTao)
                .setFontSize(10)
                .setFont(font)
                .setTextAlignment(TextAlignment.LEFT);
            document.add(date);

            Paragraph tenNguoiTao = new Paragraph("Nhân viên: " + hoaDon.getTenNguoiTao())
                .setFontSize(10)
                .setFont(font)
                .setTextAlignment(TextAlignment.LEFT);
            document.add(tenNguoiTao);

            // Tạo bảng với 4 cột
            float[] columnWidths = {4, 1, 3}; // Điều chỉnh kích thước cột
            Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
            
            // Thêm tiêu đề cột
            table.addHeaderCell(new Cell().add(new Paragraph("Mặt hàng"))
                    .setFont(font)
                    .setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("SL"))
                    .setFont(font)
                    .setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("T.Tiền"))
                    .setFont(font)
                    .setTextAlignment(TextAlignment.CENTER));

            // Thêm dữ liệu vào bảng
            for (ChiTietSP spct : chiTietSPList) {
                String tenSP = spct.getTenSP() ;
                String soLuong = String.valueOf(spct.getSoLuong());
                String giaBan =  String.valueOf(spct.getGiaBan());

                table.addCell(new Cell().add(new Paragraph(tenSP)));
                table.addCell(new Cell().add(new Paragraph(soLuong)).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(giaBan)).setTextAlignment(TextAlignment.CENTER));
            }
            document.add(table);
            Paragraph tongGiaTri = new Paragraph("Tổng giá trị: " + hoaDon.getDonGia())
                .setFontSize(14)
                .setFont(font)
                .setTextAlignment(TextAlignment.CENTER);
            document.add(tongGiaTri);
            
            Paragraph DuongkeDuoi = new Paragraph("-------------------------------------------------------------------")
                .setTextAlignment(TextAlignment.CENTER);
                document.add(DuongkeDuoi);
            
            Paragraph CamOn = new Paragraph("CẢM ƠN QUÝ KHÁCH VÀ HẸN GẶP LẠI")
                .setFont(font)
                .setTextAlignment(TextAlignment.CENTER);
                document.add(CamOn);
            document.close();
            System.out.println("Taọ PDF thành công.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

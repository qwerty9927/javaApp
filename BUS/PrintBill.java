
package task1.BUS;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import task1.DTO.CTHDonDTO;
import task1.DTO.HoaDonDTO;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PrintBill {
    private String file = "GUI/BillsReport";
    private ArrayList<CTHDonDTO> dsct = new ArrayList<CTHDonDTO>();
    private HoaDonDTO hd;
    private BaseFont font;
    private NumberFormat VND = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));

    public PrintBill(HoaDonDTO hd,ArrayList<CTHDonDTO> dsct)
    {
        this.hd = hd;
        file = "GUI/BillsReport/bill"+hd.getMaHD()+".pdf";
        this.dsct = dsct;
    }
    public void print()
    {
        String uderline = "-";
        try {
            String[] cellHeader = {"Mã SP","Tên SP","SL","Đơn Giá (VNĐ)"};
            //Tạo Font
            font = BaseFont.createFont("task1/fonts/times.ttf",BaseFont.IDENTITY_H,BaseFont.EMBEDDED);

            // Tạo tài liệu
            Document bill = new Document(PageSize.A4, 15, 15, 10, 10);

            String line = "-";
            for(int i = 0 ; i < bill.getPageSize().getWidth()/5; i++)
            {
                line += uderline;
            }
            //Tạo đối tượng writter
            PdfWriter.getInstance(bill, new FileOutputStream(file));

            //Mở document
            bill.open();

            Paragraph header = new Paragraph("Quán thức ăn nhanh Mác-đô-nan\n" +
                    "Phiếu thanh toán",new Font(font,35));
            header.setAlignment(1);
            bill.add(header);

            Paragraph wifi = new Paragraph("Mật khẩu wifi: 12345678@z",new Font(font, 15));
            wifi.setAlignment(1);
            bill.add(wifi);

            Paragraph info1 = new Paragraph("Mã hóa dơn : "+hd.getMaHD()+"        Mã khách hàng: "+hd.getMaKH(),new Font(font,15));
            info1.setAlignment(1);
            bill.add(info1);

            Paragraph info2 = new Paragraph("\nNgày : "+hd.getNgayHD(),new Font(font,15));
            info2.setAlignment(0);
            bill.add(info2);


            Paragraph l = new Paragraph(line);
            l.setAlignment(1);
            bill.add(l);


            PdfPTable t = new PdfPTable(cellHeader.length);
            t.setSpacingAfter(10);
            t.setSpacingBefore(10);
            int[] relativeWidths = {20,80,10,40};
            t.setWidths(relativeWidths);

            for(String s : cellHeader)
            {
                t.addCell(createCell(s, new Font(font,13)));
            }
            for(CTHDonDTO ct : dsct)
            {
                t.addCell(createCell(ct.getMaSP()) );
                t.addCell(createCell(ct.getTenSP()) );
                t.addCell(createCell(String.valueOf(ct.getSoLuong())) );
                t.addCell(createCell(String.valueOf(ct.getGia())) );
            }
            bill.add(t);

            bill.add(l);

            Paragraph sum = new Paragraph("Tồng tiền : "+ VND.format(hd.getTongTien()),new Font(font,20));
            sum.setAlignment(Element.ALIGN_RIGHT);
            bill.add(sum);

            bill.close();

        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(PrintBill.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PrintBill.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public PdfPCell createCell(String s)
    {
        PdfPCell cell = new PdfPCell(new Phrase(s,new Font(font,13)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(10);

        return cell;
    }
    public PdfPCell createCell(String s,Font font)
    {
        PdfPCell cell = new PdfPCell(new Phrase(s,font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPaddingBottom(10);
        return cell;
    }
}

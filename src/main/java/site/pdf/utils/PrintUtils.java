package site.pdf.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;

public class PrintUtils {
    /**
     * 货物单打印
     *
     * @param document
     */
    public static void printCargo(Document document) throws DocumentException, IOException {
        //表
        PdfPTable table;
        PdfPCell cell;
        table = new PdfPTable(6);
        //设置列宽
        float[] columnWidths = {1.5f, 1f, 1f, 1f, 1f, 1.5f};
        table.setWidths(columnWidths);
//        cell.setUseAscender(true);
        Font fontHead = getFont(22);
        cell = new PdfPCell(new Paragraph("货物单", fontHead));
        cell.setColspan(6);
        cell.disableBorderSide(15);
        setMiddle(cell);
        table.addCell(cell);
        cell = getBlankCell(cell);
        table.addCell(cell);
        Font font = getFont(0);
        //======
        String str0 = "";
        String str1 = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";
        String str5 = "";
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                str0 = "车号";
                str1 = "1106";
                str2 = "到货地点";
                str3 = "北京";
                str4 = "计费标准";
                str5 = "4.2元/每公里";
            } else if (i == 1) {
                str0 = "车型";
                str1 = "";
                str2 = "到货地点";
                str3 = "西安";
                str4 = "计费里程";
                str5 = "1252";
            } else if (i == 2) {
                str0 = "承运人";
                str1 = "";
                str2 = "产品重量";
                str3 = "0.84";
                str4 = "发货数量";
                str5 = "1000";
            } else if (i == 3) {
                str0 = "司机联系电话";
                str1 = "1106";
                str2 = "";
                str3 = "";
                str4 = "";
                str5 = "";
            } else if (i == 4) {
                str0 = "总运费";
                str1 = "5258.4";
                str2 = "蒙牛承担";
                str3 = "2389.06";
                str4 = "客户承担";
                str5 = "2869.34";
            }
            cell = new PdfPCell(new Paragraph(str0, font));
            setMiddle(cell);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(str1, font));
            setMiddle(cell);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(str2, font));
            setMiddle(cell);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(str3, font));
            setMiddle(cell);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(str4, font));
            setMiddle(cell);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(str5, font));
            setMiddle(cell);
            table.addCell(cell);
        }
        cell = new PdfPCell(new Paragraph("发货时间", font));
        setMiddle(cell);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("", font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("到达时间", font));
        setMiddle(cell);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("", font));
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("发货明细", font));
        cell.setRowspan(2);
        setMiddle(cell);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("提货单号45454565645   总数量 : 0.42   总重量 : 420", font));
        cell.setColspan(5);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("提货单号45454565646   总数量 : 0.42   总重量 : 420", font));
        cell.setColspan(5);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("客户签收(盖章)", font));
        setMiddle(cell);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("(请注意:您的确认表示货物和物料都已经收到)", font));
        cell.setColspan(5);
        setMiddle(cell);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("派车人签字:", font));
        cell.setColspan(6);
        setMiddle(cell);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("备注", font));
        setMiddle(cell);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph("", font));
        cell.setColspan(5);
        setMiddle(cell);
        table.addCell(cell);
        //文档加入表格
        document.add(table);
    }

    /**
     * 单元格居中设置
     */
    public static void setMiddle(PdfPCell cell) {
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    }

    /**
     * 获取 空白行
     *
     * @param cell
     */
    public static PdfPCell getBlankCell(PdfPCell cell) {
        //获取空白行 相当于换行操作
        cell = new PdfPCell(new Paragraph(" "));
        cell.setColspan(6);
        cell.disableBorderSide(15);
        return cell;
    }

    /**
     * 获取字体
     *
     * @param size 目前size动态  只传这一个值   font为0 默认值
     * @return
     */
    public static Font getFont(int size) throws IOException, DocumentException {
        //中文字体,解决中文不能显示问题
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //字体
        Font font = new Font(bfChinese);
        if (0 != size) {
            font.setSize(size);
        }
        return font;
    }
}

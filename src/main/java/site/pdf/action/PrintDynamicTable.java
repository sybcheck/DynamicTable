package site.pdf.action;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import site.pdf.utils.PdfReportM1HeaderFooter;
import site.pdf.utils.PrintUtils;
import site.pdf.vo.Contract;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author syb
 * @version v1.0
 * @Description: 创建table动态表格
 */
@RestController
@RequestMapping("/action/")
public class PrintDynamicTable {
    //是否每页带头部    <合同评审单>
    private static boolean isOfHead = true;
    //每页显示多少行   <合同评审单>
    private static int lineNum = 10;
    //打印类型   目前 cargo:货物单   contract:合同评审单    完成
    private static String printType = "cargo";
    //分页参数
    private static int page;
    //合并行用到    <合同评审单>
    private static String split = "<brb>";
    //上下合并那一列
    private static List<Integer> columns = new ArrayList<>();

    private static PrintUtils printUtils;

    /**
     * 打印pdf 返回流到前段页面 前段页面解析 调用浏览器打印。
     *
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping(value = "/print")
    @ResponseBody
    public void print(HttpServletResponse response) throws IOException, DocumentException {
        System.out.println(printType + "类型为");
        //
        page = 0;
        // 1.新建document对象
        Document document = new Document(PageSize.A4);

        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, bos);

        //显示页码
        setFooter(writer);
        // 3.打开文档
        document.open();
        if (printType.equals("cargo")) {
            //打印货物单
            printUtils.printCargo(document);
        } else if (printType.equals("contract")) {
            //合同评审单打印
            //获取合同评审单数据
            List<Contract> list = getContracts();
            //对象转数组
            List<String[]> listStrs = objectToArray(list);
            int time = (int) Math.ceil((float) listStrs.size() / lineNum);
            for (int i = 0; i < time; i++) {
                printContract(document, listStrs);
                //最后一页不newPage  要不多出一空页
                if (i != (time - 1)) {
                    document.newPage();
                }
            }
        }

        // 5.关闭文档  各种流
        document.close();
        //关闭二进制数组流
        bos.flush();
        bos.close();
        //书写器 关闭
        writer.close();

        //调用打印机
        //  new PdfPrint().print(bos);
        try {
            byte[] bytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
//        String data = encoder.encode(bytes);
            response.reset();
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "; filename=" + "table.pdf");
            outputStream.write(bytes, 0, bytes.length);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        byte[] bytes = bos.toByteArray();
//
//        return bytes;
    }


    /**
     * 合同评审单打印
     *
     * @param document
     * @param listStrs
     */
    public static void printContract(Document document, List<String[]> listStrs) throws IOException, DocumentException {
        //创建表
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100); // 宽度100%填充
        table.setSpacingBefore(10f); // 前间距
        table.setSpacingAfter(10f); // 后间距
        //分页好像得设置这个
        table.setSplitLate(false);
        table.setSplitRows(true);
        //设置列宽
        float[] columnWidths = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 2f};
        table.setWidths(columnWidths);
        //设置表格头部
        setHeadTable(table, document);
        //获取表格字体
        Font font = getFont(0);
        //加表格列头
        addTitle(table, font);
        //加表格内容
        addContent(listStrs, table, font);
        //文档加入表格
        document.add(table);
    }

    /**
     * 天加表格内容
     *
     * @param listStrs
     * @param table
     * @param font
     */
    private static void addContent(List<String[]> listStrs, PdfPTable table, Font font) {
        //复制要合并的列
        List<List<String>> merList = new ArrayList<>();
        List<String> ListIn0 = new ArrayList<>();
        List<String> ListIn1 = new ArrayList<>();
        List<String> ListIn2 = new ArrayList<>();
        List<String> ListIn3 = new ArrayList<>();
        List<String> ListIn4 = new ArrayList<>();
        List<String> ListIn5 = new ArrayList<>();
        List<String> ListIn6 = new ArrayList<>();
        List<String> ListIn7 = new ArrayList<>();
        List<String> ListIn8 = new ArrayList<>();
        columns.add(1);
        columns.add(2);
        columns.add(3);
        columns.add(4);
//        columns.add(5);
//        columns.add(6);
//        columns.add(7);
//        columns.add(8);
        int i = page * lineNum;
        for (; i < listStrs.size(); i++) {
            if (columns.contains(0)) {
                ListIn0.add(listStrs.get(i)[0]);
            }
            if (columns.contains(1)) {
                ListIn1.add(listStrs.get(i)[1]);
            }
            if (columns.contains(2)) {
                ListIn2.add(listStrs.get(i)[2]);
            }
            if (columns.contains(3)) {
                ListIn3.add(listStrs.get(i)[3]);
            }
            if (columns.contains(4)) {
                ListIn4.add(listStrs.get(i)[4]);
            }
            if (columns.contains(5)) {
                ListIn5.add(listStrs.get(i)[5]);
            }
            if (columns.contains(6)) {
                ListIn6.add(listStrs.get(i)[6]);
            }
            if (columns.contains(7)) {
                ListIn7.add(listStrs.get(i)[7]);
            }
            if (columns.contains(8)) {
                ListIn8.add(listStrs.get(i)[8]);
            }
            //到了每页显示的行数  执行break终止此方法
            if (((i + 1) % lineNum == 0 && (i + 1) != page * lineNum && i != 0) || (i + 1) % lineNum != 0 && (i + 1) == listStrs.size()) {
                if (ListIn0.size() > 0) {
                    merList.add(ListIn0);
                }
                if (ListIn1.size() > 0) {
                    merList.add(ListIn1);
                }
                if (ListIn2.size() > 0) {
                    merList.add(ListIn2);
                }
                if (ListIn3.size() > 0) {
                    merList.add(ListIn3);
                }
                if (ListIn4.size() > 0) {
                    merList.add(ListIn4);
                }
                if (ListIn5.size() > 0) {
                    merList.add(ListIn5);
                }
                if (ListIn6.size() > 0) {
                    merList.add(ListIn6);
                }
                if (ListIn7.size() > 0) {
                    merList.add(ListIn7);
                }
                if (ListIn8.size() > 0) {
                    merList.add(ListIn8);
                }
                for (int num = 0; num < merList.size(); num++) {
                    //根据算法改变合并列的值
                    List<String> changeList = changeList(merList.get(num));
                    //改变该列后把它复制到原来的list
                    for (int j = 0; j < changeList.size(); j++) {
                        String[] arr = new String[changeList.size()];
                        if ((i + 1) == listStrs.size()) {
                            arr = listStrs.get(page * lineNum + j);
                        } else {
                            arr = listStrs.get(((i + 1) - lineNum) + j);
                        }
                        arr[columns.get(num)] = changeList.get(j);
                        listStrs.set(j, arr);
                    }
                }
                if ((i + 1) == listStrs.size()) {
                    listStrs = listStrs.subList(page * lineNum, listStrs.size());
                } else {
                    listStrs = listStrs.subList((i + 1) - lineNum, (i + 1));
                }
                //打印内容
                addContent1(table, listStrs, font);
                //==
                page++;
                break;
            }
        }
    }


    /**
     * 对象几个转为数组集合
     *
     * @param list
     * @return
     */
    private static List<String[]> objectToArray(List<Contract> list) {
        List<String[]> listStrs = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String[] strings = new String[9];
            strings[0] = list.get(i).getSeq() + "";
            strings[1] = list.get(i).getSampleName();
            strings[2] = list.get(i).getBatchNum();
            strings[3] = list.get(i).getStandard();
            strings[4] = list.get(i).getSampleNum();
            strings[5] = list.get(i).getBatch();
            strings[6] = list.get(i).getSampleAmout();
            strings[7] = list.get(i).getTestType();
            strings[8] = list.get(i).getSampleInf();
            listStrs.add(strings);
        }
        return listStrs;
    }

    /**
     * 设置表格头部
     *
     * @param table
     * @param document
     */
    private static void setHeadTable(PdfPTable table, Document document) throws IOException, DocumentException {
        Font fontHead = getFont(22);
        PdfPCell cell;
        //头表格
        PdfPTable headTable = getHead("合同评审单", fontHead, 9);
        if (!isOfHead) {
            if (page == 0) {
                cell = new PdfPCell(headTable);
                cell.setColspan(9);
                cell.disableBorderSide(15);
                table.setHeaderRows(2);
                table.addCell(cell);
            }
        } else {
            document.add(headTable);
            table.setHeaderRows(1);
        }
    }

    /**
     * 获取合同评审单打印数据
     *
     * @return
     */
    private static List<Contract> getContracts() {
        List<Contract> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Contract contract = new Contract();
            contract.setSeq(i + 1);
            contract.setSampleName("杀菌水");
            contract.setBatch("22");
            if (i > 12 && i < 26) {
                contract.setStandard("1");
                contract.setSampleNum("1袋");
            } else {
                contract.setStandard("2");
                contract.setSampleNum("2袋");
            }
            contract.setBatchNum("10卷");
            contract.setSampleAmout("12121212");
            if (i > 4 && i < 15) {
                contract.setTestType("测试");
            } else if (i == 29) {
                contract.setTestType("委托检验");
            } else {
                contract.setTestType("出厂检验");
            }
            contract.setSampleInf("比容，色泽，净含量，水分");
            list.add(contract);
        }
        return list;
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

    /**
     * 分页
     *
     * @param writer
     */
    private static void setFooter(PdfWriter writer) throws DocumentException, IOException {
        //更改事件，瞬间变身 第几页/共几页 模式。
        PdfReportM1HeaderFooter headerFooter = new PdfReportM1HeaderFooter();
        writer.setBoxSize("art", PageSize.A4);
        writer.setPageEvent(headerFooter);
    }

    /**
     * 获取头部
     */
    public static PdfPTable getHead(String title, Font font, int i) {
        PdfPTable headTable = new PdfPTable(1);
        PdfPCell cell;
        cell = new PdfPCell(new Paragraph(title, font));
        cell.disableBorderSide(15);
        setMiddle(cell);
        headTable.addCell(cell);
        cell = getBlankCell(cell);
        cell.setColspan(i);
        headTable.addCell(cell);
        return headTable;
    }

    /**
     * 设置列头部
     *
     * @param table
     * @param font
     */
    public static void addTitle(PdfPTable table, Font font) {//表格头部自定义
        String[] title = {"序号", "样品名称", "批号", "规格", "样品量", "批量", "样品编号", "检验类型", "样品信息"};
        for (String t : title) {
            Paragraph p = new Paragraph(t, font);
            PdfPCell cell = new PdfPCell();
            p.setAlignment(1);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);//然并卵
            cell.setPaddingTop(-2f);//把字垂直居中
            cell.setPaddingBottom(8f);//把字垂直居中
            cell.addElement(p);
            table.addCell(cell);
        }
    }

    /**
     * 改变合并列的值
     *
     * @param drugList 内容支持 'A,A,B,C,D,D,D,E' 支持 'A,A,B,C,A,D,D,D,E'
     * @return
     */
    private static List<String> changeList(List<String> drugList) {
        List<String> drugListCopy = new ArrayList<>();
        drugListCopy.addAll(drugList);

        int nullNum = 0;
        for (int i = 0; i < drugList.size(); i++) {
            if (i > 0) {
                if (drugList.get(i).equals(drugList.get(i - 1))) {
                    drugListCopy.set(i, null);
                    nullNum++;
                } else {
                    if (nullNum > 0) {
                        int start = i - nullNum - 1;
                        drugListCopy.set(start, drugList.get(start) + split + (nullNum + 1));
                        nullNum = 0;
                    }
                }

                // 处理某一列值都相同的情况
                if (nullNum > 0) {
                    int start = i - nullNum;
                    drugListCopy.set(start, drugList.get(start) + split + (nullNum + 1));
                }
            }
        }
        return drugListCopy;
    }

    /**
     * 添加内容
     *
     * @param table
     * @param list
     * @param textFont
     */
    private static void addContent1(PdfPTable table, List<String[]> list, Font textFont) {
        //表格数据内容
        for (String[] str : list) {
            for (int j = 0; j < str.length; j++) {
                String value = str[j];
                if (value != null) {
                    Paragraph paragraph01;
                    int spanNum = 1;
                    if (value.contains(split)) {
                        spanNum = Integer.parseInt(value.split(split)[1]);
                        paragraph01 = new Paragraph(value.split(split)[0], textFont);
                    } else {
                        paragraph01 = new Paragraph(value, textFont);
                    }
                    paragraph01.setAlignment(1);
                    PdfPCell cell = new PdfPCell();
                    cell.setPaddingTop(-2f);//把字垂直居中
                    cell.setPaddingBottom(8f);//把字垂直居中
                    setMiddle(cell);
                    cell.addElement(paragraph01);
                    cell.setRowspan(spanNum);
                    table.addCell(cell);
                }
            }
        }
    }

}
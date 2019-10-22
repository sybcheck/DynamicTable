package site.duanzy.pdf.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javax.print.PrintService;
import java.awt.print.*;
import java.io.*;

public class PdfPrint {
    public void print(ByteArrayOutputStream bos) {

        byte[] bytes = bos.toByteArray();

        // 使用打印机的名称
        String printName = "Win32 Printer : Microsoft Print to PDF";

        // 读取流
        PDDocument document = null;
        try {
            document = PDDocument.load(bytes);
            // 创建打印任务
            PrinterJob job = PrinterJob.getPrinterJob();
            //当前任务名称
            //job.setJobName("printing");
            // 遍历所有打印机的名称
            String psName = "";
            //==
            for (PrintService ps : PrinterJob.lookupPrintServices()) {
                psName = ps.toString();
                System.out.println("遍历所有的打印机-  " + psName);
            }
            System.out.println("------");
            System.out.println("使用的打印机名称为 " + printName);
            //匹配指定打印机
            System.out.println("匹配打印机...");
            for (PrintService ps : PrinterJob.lookupPrintServices()) {
                psName = ps.toString();
                // 选用指定打印机
                if (psName.equals(printName)) {
                    System.out.println("打印机匹配成功...");
                    // isChoose = true;
                    job.setPrintService(ps);
                    break;
                }
            }

            //使用默认的打印机
//            /System.out.println("默认打印机...");
//            PrintService defaultPrintService= PrintServiceLookup.lookupDefaultPrintService();
//            System.out.println(defaultPrintService.getName()+"默认打印机的名字");
//            job.setPrintService(defaultPrintService);

            job.setPageable(new PDFPageable(document));
            //Paper paper = new Paper();
            // 设置打印纸张大小
            //paper.setSize(598, 842);
            // 1/72 inch
            // 设置打印位置 坐标
            //paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
            // no margins
            // custom page format
            //PageFormat pageFormat = new PageFormat();
            //pageFormat.setPaper(paper);
            // override the page format
//            Book book = new Book();
//            // append all pages 设置一些属性 是否缩放 打印张数等
//            book.append(new PDFPrintable(document, Scaling.ACTUAL_SIZE), pageFormat, 1);
//            job.setPageable(book);
            // 开始打印
            // System.out.println("文档名为 " + "document");
            System.out.println("开始打印...");
            job.print();
            System.out.println("打印完毕...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

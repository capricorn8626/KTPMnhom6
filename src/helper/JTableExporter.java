package helper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BUS.HeDieuHanhBUS;
import BUS.ThuongHieuBUS;
import BUS.XuatXuBUS;
import DTO.SanPhamDTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.io.FileOutputStream;
import java.io.IOException;

public class JTableExporter {

    public static void exportJTableToExcel(JTable table) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn đường dẫn lưu file Excel");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX files", "xlsx");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userChoice = fileChooser.showSaveDialog(null);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            TableModel model = table.getModel();
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(model.getColumnName(i));
            }

            // Create data rows
            for (int i = 0; i < model.getRowCount(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Cell dataCell = dataRow.createCell(j);
                    Object value = model.getValueAt(i, j);
                    if (value != null) {
                        dataCell.setCellValue(value.toString());
                    }
                }
            }

            // Resize all columns to fit the content size
            for (int i = 0; i < model.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            workbook.close();
        }
    }

    public static void exportKhuVucKho(String sheetName, java.util.ArrayList<SanPhamDTO> dssp) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn đường dẫn lưu file Excel");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XLSX files", "xlsx");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        ThuongHieuBUS thuongHieuBUS = new ThuongHieuBUS();
        HeDieuHanhBUS heDieuHanhBUS = new HeDieuHanhBUS();
        XuatXuBUS xuatXuBUS = new XuatXuBUS();

        int userChoice = fileChooser.showSaveDialog(null);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);

            // Create header row
            Row headerRow = sheet.createRow(0);
            String header[] = {"Mã SP", "Tên sản phẩm", "Số lượng tồn","Thương hiệu", "Hệ điều hành", "Kích thước màn","Chip xử lý","Dung lượng pin","Xuất xứ"};
            for (int i = 0; i < header.length; i++) {
                Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(header[i]);
            }

            // Create data rows
            for (int i = 0; i < dssp.size(); i++) {
                SanPhamDTO sp = dssp.get(i);
                Row dataRow = sheet.createRow(i + 1);
                Cell dataCell = dataRow.createCell(0);
                dataCell.setCellValue(sp.getMasp());
                dataCell = dataRow.createCell(1);
                dataCell.setCellValue(sp.getTensp());
                dataCell = dataRow.createCell(2);
                dataCell.setCellValue(sp.getSoluongton());
                dataCell = dataRow.createCell(3);
                dataCell.setCellValue(thuongHieuBUS.getTenThuongHieu(sp.getThuonghieu()));
                dataCell = dataRow.createCell(4);
                dataCell.setCellValue(heDieuHanhBUS.getTenHdh(sp.getHedieuhanh()));
                dataCell = dataRow.createCell(5);
                dataCell.setCellValue(sp.getKichthuocman());
                dataCell = dataRow.createCell(6);
                dataCell.setCellValue(sp.getChipxuly());
                dataCell = dataRow.createCell(7);
                dataCell.setCellValue(sp.getDungluongpin());
                dataCell = dataRow.createCell(8);
                dataCell.setCellValue(xuatXuBUS.getTenXuatXu(sp.getXuatxu()));
            }

            // Resize all columns to fit the content size
            for (int i = 0; i < header.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
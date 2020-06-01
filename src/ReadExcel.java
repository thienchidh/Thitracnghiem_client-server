import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ReadExcel {
    public static List<Question> lstQuestion = new LinkedList<Question>();

    public static void main(String[] args) {
        R();
        insertDB();
    }

    public static void R() {
        try {
            //File excelFile = new File("data/3.xlsx");
            File excelFile = new File("data/NL1.xlsx");
            FileInputStream fis = new FileInputStream(excelFile);

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIt = sheet.iterator();
            rowIt.next();
            String content = "", dap_an_a = "", dap_an_b = "", dap_an_c = "", dap_an_d = "", dap_an_e = "",
                    dap_an_f = "", rate = "1", dap_an_dung = "A", thuoc_chuong = "CK";

            while (rowIt.hasNext()) {
                Row row = rowIt.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> lstTemp = new LinkedList<String>();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    lstTemp.add(cell.toString());
                }
                if (lstTemp.size() < 6)
                    continue;

                content = lstTemp.get(1);
                dap_an_a = lstTemp.get(2);
                dap_an_b = lstTemp.get(3);
                dap_an_c = lstTemp.get(4);
                dap_an_d = lstTemp.get(5);

                if (lstTemp.get(2).charAt(0) == '*') {
                    dap_an_dung = "A";
                    dap_an_a = dap_an_a.substring(1);
                } else if (lstTemp.get(3).charAt(0) == '*') {
                    dap_an_dung = "B";
                    dap_an_b = dap_an_b.substring(1);
                } else if (lstTemp.get(4).charAt(0) == '*') {
                    dap_an_dung = "C";
                    dap_an_c = dap_an_c.substring(1);
                } else if (lstTemp.get(5).charAt(0) == '*') {
                    dap_an_dung = "D";
                    dap_an_d = dap_an_d.substring(1);
                }

                if (dap_an_a.charAt(0) == '.') {
                    dap_an_a = dap_an_a.substring(1);
                }
                if (dap_an_b.charAt(0) == '.') {
                    dap_an_b = dap_an_b.substring(1);
                }
                if (dap_an_c.charAt(0) == '.') {
                    dap_an_c = dap_an_c.substring(1);
                }
                if (dap_an_d.charAt(0) == '.') {
                    dap_an_d = dap_an_d.substring(1);
                }

                if (isNumberic(dap_an_a)) {
                    dap_an_a = (int) Double.parseDouble(dap_an_a) + "";
                }
                if (isNumberic(dap_an_b)) {
                    dap_an_b = (int) Double.parseDouble(dap_an_b) + "";
                }
                if (isNumberic(dap_an_c)) {
                    dap_an_c = (int) Double.parseDouble(dap_an_c) + "";
                }
                if (isNumberic(dap_an_d)) {
                    dap_an_d = (int) Double.parseDouble(dap_an_d) + "";
                }

                if (lstTemp.get(lstTemp.size() - 1).contains("GK")) {
                    thuoc_chuong = "GK";
                } else {
                    thuoc_chuong = "CK";
                }
                Question q = new Question(content, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_e, dap_an_f, rate,
                        dap_an_dung, thuoc_chuong);

                lstQuestion.add(q);
            }
            workbook.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumberic(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void insertDB() {
        try (Connection connection = new MySQLConnectionManager().getConnection("localhost:3306", "thitracnghiem_NL1",
                "root", "123456");) {
            String query = "call insert_list_question(?,?,?,?,?,?,?,?,?,?);";

            CallableStatement stmt = connection.prepareCall(query);
            for (Question temp : lstQuestion) {
                stmt.setString(1, temp.content);
                stmt.setString(2, temp.dap_an_a);
                stmt.setString(3, temp.dap_an_b);
                stmt.setString(4, temp.dap_an_c);
                stmt.setString(5, temp.dap_an_d);
                stmt.setString(6, temp.dap_an_e);
                stmt.setString(7, temp.dap_an_f);

                stmt.setString(8, temp.rate);
                stmt.setString(9, temp.dap_an_dung);
                stmt.setString(10, temp.thuoc_chuong);

                stmt.execute();
            }
            System.out.println("Done!");
            //call insert_list_question('1+1=?', '1','2','3','4','','','1','B','1');
        } catch (Exception e) {
        }
    }
}

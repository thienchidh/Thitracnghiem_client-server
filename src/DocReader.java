import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

public class DocReader {
    public static List<String> lstRows = new LinkedList<String>();
    public static List<Question> lstQuestion = new LinkedList<Question>();


    public static void readDocFile(String fileName) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("data/temp.txt"));

            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            HWPFDocument doc = new HWPFDocument(fis);

            WordExtractor we = new WordExtractor(doc);

            String[] paragraphs = we.getParagraphText();

            for (String para : paragraphs) {
                String t = para.replace("\n", "").replace("\r", "").trim();


                int c1 = 0;
                String temp = t;
                while (temp.contains("\t")) {
                    temp = temp.replaceFirst("\t", "\n");
                    c1++;
                }

                while (c1 > 1) {
                    t = t.replaceFirst("\t", "");
                    c1--;
                }
                t = t.replaceFirst("\t", "\n");

                if (t.length() < 3) continue;
                bw.write(t + "\n");
            }
            fis.close();
            we.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void insertDB() {
        try (Connection connection = new MySQLConnectionManager().getConnection("localhost:3306", "thitracnghiem",
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
            e.printStackTrace();
        }
    }

    public static void readDocxFile(String fileName) {

        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            System.out.println("Total no of paragraph " + paragraphs.size());
            for (XWPFParagraph para : paragraphs) {
                System.out.println(para.getText());
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ReadFileText() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/temp.txt"));
            String line;
            int cnt = 0;
            while ((line = br.readLine()) != null) {

                String temp = line;
                for (int i = 1; i <= 100; i++) {
                    temp = temp.replaceFirst("Câu " + i + ": ", "");
                }

                temp = temp.replaceFirst("DapAn: ", "");
                if (temp.length() >= 4 && (cnt % 6) >= 1)
                    temp = temp.substring(3, temp.length());
                lstRows.add(temp);
                cnt++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = "", dap_an_a = "",
                dap_an_b = "", dap_an_c = "", dap_an_d = "",
                dap_an_e = "", dap_an_f = "", rate = "1",
                dap_an_dung = "", thuoc_chuong = "1";

        for (int i = 0; i < lstRows.size(); i++) {
            int idx = i % 6;
            if (idx == 0) {
                content = lstRows.get(i);
            } else if (idx == 1) {
                dap_an_a = lstRows.get(i);
            } else if (idx == 2) {
                dap_an_b = lstRows.get(i);
            } else if (idx == 3) {
                dap_an_c = lstRows.get(i);
            } else if (idx == 4) {
                dap_an_d = lstRows.get(i);
            } else if (idx == 5) {
                dap_an_dung = lstRows.get(i);
            }
            if (idx == 0 && i >= 6) {
                Question temp = new Question(content, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_e, dap_an_f, rate, dap_an_dung, thuoc_chuong);
                lstQuestion.add(temp);
            }

        }
    }

    public static void docWorking() {
        readDocFile("data/2.doc");
        ReadFileText();
        insertDB();
    }

    public static void main(String[] args) {
        //docWorking();
    }
}


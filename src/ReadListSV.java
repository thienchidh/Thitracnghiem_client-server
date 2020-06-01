import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ReadListSV {
    public static String ROOT = "datasv/";
    public static List<user_infor> lstuser_infor = new LinkedList<user_infor>();


    public static void main(String[] args) throws Exception {
        List<String> lst = GetPath(ROOT);
        for (String x : lst) {
            String html = ReadFile(x);
            GetData(html);
        }

        insertDB();
    }

    public static void insertDB() throws Exception {
        try (Connection connection = new MySQLConnectionManager().getConnection("localhost:3306", "thitracnghiem_NL1",
                "root", "123456");) {
            String query = "call insert_user_infor(?,?,?,?,?,?,?);";

            CallableStatement stmt = connection.prepareCall(query);
            for (user_infor temp : lstuser_infor) {
                stmt.setString(1, temp.mssv);
                stmt.setString(2, temp.ho_ten);
                stmt.setString(3, temp.sdt);
                stmt.setString(4, temp.lop);
                stmt.setString(5, temp.ngaysinh);
                stmt.setString(6, temp.email);
                stmt.setString(7, temp._default);

                stmt.execute();

                System.out.println(temp.toString());
            }
            System.out.println("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void GetData(String html) {
        Document doc = Jsoup.parse(html);
        String cssQuery = ".Inbox_Item td:not(td:contains(=round))";
        Elements elements = doc.select(cssQuery);
        Iterator<Element> iterator = elements.iterator();

        int cnt = 0;
        List<String> lstTemp = new LinkedList<String>();

        while (iterator.hasNext()) {
            Element e = iterator.next();
            if (!e.text().equals("")) {
                cnt++;
                if (cnt % 6 == 1) {
                    if (lstTemp.size() != 5) {
                        continue;
                    }
                    String email = Util.removeAccent(lstTemp.get(3)).toLowerCase() + "_" + lstTemp.get(0) + "@dau.edu.vn";
                    String _default = lstTemp.get(4).replaceAll("/", "*");

                    user_infor temp = new user_infor(lstTemp.get(0), lstTemp.get(2) + " " + lstTemp.get(3), "", lstTemp.get(1), lstTemp.get(4), email, _default);
                    lstuser_infor.add(temp);
                    lstTemp = new LinkedList<String>();
                    continue;
                }
                lstTemp.add(e.text());
            }
        }
        if (lstTemp.size() == 5) {
            String email = Util.removeAccent(lstTemp.get(3)).toLowerCase() + "_" + lstTemp.get(0) + "@dau.edu.vn";
            String _default = lstTemp.get(4).replaceAll("/", "*");
            user_infor temp = new user_infor(lstTemp.get(0), lstTemp.get(2) + " " + lstTemp.get(3), "", lstTemp.get(1), lstTemp.get(4), email, _default);
            lstuser_infor.add(temp);
        }
    }

    public static String ReadFile(String path) {
        String res = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                res += line;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<String> GetPath(String pathRoot) {
        List<String> lst = new LinkedList<String>();
        try {
            final File folder = new File(pathRoot);
            for (final File fileEntry : folder.listFiles()) {
                if (!fileEntry.isDirectory()) {
                    lst.add(fileEntry.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }
}

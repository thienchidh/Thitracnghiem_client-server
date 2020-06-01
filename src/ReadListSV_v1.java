import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ReadListSV_v1 {
    public static String ROOT = "datasv/";
    public static List<user_infor> lstuser_infor = new LinkedList<user_infor>();

    public static void main(String[] args) throws Exception {
        List<String> lst = GetPath(ROOT);
        for (String x : lst) {
            String html = ReadFile(x);
            GetData(html);
        }
        //System.out.println(f("Đỗ Phúc Hảo", "19CT1"));
        //System.out.println(f1("Đỗ Phúc Hảo", "19CT1"));
        writeFile();
    }

    // ho_ten=Trần Phi Phụng, sdt=, lop=19CT3
    public static String f1(String ho_ten, String lop) {
        String temp = Util.removeAccent(ho_ten).toLowerCase();
        String[] split = temp.split(" ");
        String res = split[split.length - 1];
        for (int i = 0; i < split.length - 1; i++) {
            res += split[i].charAt(0);
        }
        String kq = lop.toLowerCase() + "_" + res;
        return kq;
    }

    public static void writeFile() {
        try {
			/*
			 * teams	1
				1	1	3	19CT1 - Đỗ Phúc Hảo
			 */
			/*
			 * accounts	1
				admin	3	tmp	19ct1_haodp	123	1
			 */

            BufferedWriter bw = new BufferedWriter(new FileWriter("teams.tsv"));
            String t = "teams" + "\t" + "1" + "\n";
            bw.write(t);

            BufferedWriter bw1 = new BufferedWriter(new FileWriter("accounts.tsv"));
            String t1 = "accounts" + "\t" + "1" + "\n";
            bw1.write(t1);


            int idx = 1;


            for (int i = 0; i < lstuser_infor.size(); i++) {
                user_infor d = lstuser_infor.get(i);
                String ho_ten = d.ho_ten;
                String lop = d.lop;

                String name = f(ho_ten, lop);
                String line = idx + "\t" + idx + "\t" + "3" + "\t" + name + "\n";
                bw.write(line);

                String name1 = f1(ho_ten, lop);
                String line1 = "admin" + "\t" + "3" + "\t" + "tmp" + "\t" + name1 + "\t" + "123" + "\t" + idx + "\n";
                bw1.write(line1);


                idx++;
            }


            bw.close();
            bw1.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String f(String ho_ten, String lop) {
        return lop + " - " + ho_ten;
    }

    public static void showdata() {
        for (user_infor temp : lstuser_infor) {
            System.out.println(temp.toString());
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
                    String email = Util.removeAccent(lstTemp.get(3)).toLowerCase() + "_" + lstTemp.get(0)
                            + "@dau.edu.vn";
                    String _default = lstTemp.get(4).replaceAll("/", "*");

                    user_infor temp = new user_infor(lstTemp.get(0), lstTemp.get(2) + " " + lstTemp.get(3), "",
                            lstTemp.get(1), lstTemp.get(4), email, _default);
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
            user_infor temp = new user_infor(lstTemp.get(0), lstTemp.get(2) + " " + lstTemp.get(3), "", lstTemp.get(1),
                    lstTemp.get(4), email, _default);
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
        }
        return lst;
    }
}

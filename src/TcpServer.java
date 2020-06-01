import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TcpServer extends Thread {

    public static List<user_infor> lstUserInfor = new LinkedList<user_infor>();
    public static List<Question> lstQuestion = new LinkedList<Question>();
    public static List<GiangVien> lstGiangVien = new LinkedList<GiangVien>();
    // for other information
    public static List<Favourite> lstFavourite = new LinkedList<Favourite>();
    public static List<ListCauHoi> lstListCauHoi = new LinkedList<ListCauHoi>();
    public static List<Sinhvien_Baithi> lstSinhvien_Baithi = new LinkedList<Sinhvien_Baithi>();
    public static List<BaiThi> lstBaithi = new LinkedList<BaiThi>();
    public static List<Gen_de> lstGende = new LinkedList<Gen_de>();
    public boolean isable2exit = false;
    // public int num = 10;
    public int num = 10;

    public TcpServer(String[] args) {
        try {
            int port = 41235;
            if (args.length == 1)
                port = Integer.parseInt(args[0]);

            refreshData();
            this.start();

            ServerSocket server = new ServerSocket(port);
            while (!isable2exit) {
                try {
                    Socket s = server.accept();
                    Process newProcess = new Process(s, this);
                    if (newProcess.processCode.equals("stop")) {
                        isable2exit = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("errP server.accept");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TcpServer(args);
    }

    public void run() {
        while (!isable2exit) {
            try {
                Thread.sleep(15 * 60 * 1000);
                refreshData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshData() {
        long start = new Date().getTime();
        GetALLData();
        long end = new Date().getTime();
        System.out.println("time load db: " + (end - start));
    }

    public void refreshData_ForBaithi() {
        try (Connection connection = new MySQLConnectionManager().getConnection("localhost:3306", "thitracnghiem",
                "root", "123456");) {

            // for BaiThi
            String sql6 = "select * from thitracnghiem.bai_thi";
            CallableStatement callableStatement6 = connection.prepareCall(sql6);
            ResultSet resultSet6 = callableStatement6.executeQuery();
            lstBaithi = new LinkedList<BaiThi>();

            while (resultSet6.next()) {
                String id, ma_loai_kt, thoi_gian_bat_dau, bao_lau, thoi_gian_ket_thuc, lop, status, ten_bai_thi,
                        isDisabled;
                id = resultSet6.getString("id");
                ma_loai_kt = resultSet6.getString("ma_loai_kt");
                thoi_gian_bat_dau = resultSet6.getString("thoi_gian_bat_dau");
                bao_lau = resultSet6.getString("bao_lau");
                thoi_gian_ket_thuc = resultSet6.getString("thoi_gian_ket_thuc");
                ten_bai_thi = resultSet6.getString("ten_bai_thi");
                lop = resultSet6.getString("lop");
                status = resultSet6.getString("status");
                isDisabled = resultSet6.getString("isDisabled");

                BaiThi temp = new BaiThi(id, ma_loai_kt, thoi_gian_bat_dau, bao_lau, thoi_gian_ket_thuc, lop, status,
                        ten_bai_thi, isDisabled);
                lstBaithi.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshData_ForSV() {
        try (Connection connection = new MySQLConnectionManager().getConnection("localhost:3306", "thitracnghiem",
                "root", "123456");) {

            // for Favourite
            String sql3 = "select * from thitracnghiem.favourite";
            CallableStatement callableStatement3 = connection.prepareCall(sql3);
            ResultSet resultSet3 = callableStatement3.executeQuery();
            lstFavourite = new LinkedList<Favourite>();

            while (resultSet3.next()) {
                String id, mssv, idQuestion;
                id = resultSet3.getString("id");
                mssv = resultSet3.getString("mssv");
                idQuestion = resultSet3.getString("idQuestion");

                Favourite temp = new Favourite(id, mssv, idQuestion);
                lstFavourite.add(temp);
            }

            // for sinhvien_baithi
            String sql5 = "select * from thitracnghiem.sinhvien_baithi";
            CallableStatement callableStatement5 = connection.prepareCall(sql5);
            ResultSet resultSet5 = callableStatement5.executeQuery();
            lstSinhvien_Baithi = new LinkedList<Sinhvien_Baithi>();

            while (resultSet5.next()) {
                String id, mssv, bai_thi, list_cau_hoi, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_e, dap_an_f,
                        diem, isSubmited;
                id = resultSet5.getString("id");
                mssv = resultSet5.getString("mssv");
                bai_thi = resultSet5.getString("bai_thi");
                list_cau_hoi = resultSet5.getString("list_cau_hoi");
                dap_an_a = resultSet5.getString("dap_an_a");
                dap_an_b = resultSet5.getString("dap_an_b");
                dap_an_c = resultSet5.getString("dap_an_c");
                dap_an_d = resultSet5.getString("dap_an_d");
                dap_an_e = resultSet5.getString("dap_an_e");
                dap_an_f = resultSet5.getString("dap_an_f");
                diem = resultSet5.getString("diem");
                // isSubmited
                isSubmited = resultSet5.getString("isSubmited");

                Sinhvien_Baithi temp = new Sinhvien_Baithi(id, mssv, bai_thi, list_cau_hoi, dap_an_a, dap_an_b,
                        dap_an_c, dap_an_d, dap_an_e, dap_an_f, diem, isSubmited);
                lstSinhvien_Baithi.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh_Favourite() {
        try (Connection connection = new MySQLConnectionManager().getConnection("localhost:3306", "thitracnghiem",
                "root", "123456");) {
            // for Favourite
            String sql3 = "select * from thitracnghiem.favourite";
            CallableStatement callableStatement3 = connection.prepareCall(sql3);
            ResultSet resultSet3 = callableStatement3.executeQuery();
            lstFavourite = new LinkedList<Favourite>();

            while (resultSet3.next()) {
                String id, mssv, idQuestion;
                id = resultSet3.getString("id");
                mssv = resultSet3.getString("mssv");
                idQuestion = resultSet3.getString("idQuestion");

                Favourite temp = new Favourite(id, mssv, idQuestion);
                lstFavourite.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetALLData() {
        try (Connection connection = new MySQLConnectionManager().getConnection("localhost:3306", "thitracnghiem",
                "root", "123456");) {

            // question
            String sql = "select * from thitracnghiem.list_questions";
            CallableStatement callableStatement = connection.prepareCall(sql);
            ResultSet resultSet = callableStatement.executeQuery();
            lstQuestion = new LinkedList<Question>();

            while (resultSet.next()) {
                String id;
                String content, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_e, dap_an_f;
                String rate, dap_an_dung, thuoc_chuong;
                id = resultSet.getString("id");
                content = resultSet.getString("content");
                dap_an_a = resultSet.getString("dap_an_a");
                dap_an_b = resultSet.getString("dap_an_b");
                dap_an_c = resultSet.getString("dap_an_c");
                dap_an_d = resultSet.getString("dap_an_d");
                dap_an_e = resultSet.getString("dap_an_e");
                dap_an_f = resultSet.getString("dap_an_f");
                rate = resultSet.getString("rate");
                dap_an_dung = resultSet.getString("dap_an_dung");
                thuoc_chuong = resultSet.getString("thuoc_chuong");

                Question temp = new Question(id, content, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_e, dap_an_f,
                        rate, dap_an_dung, thuoc_chuong);
                lstQuestion.add(temp);
            }

            // giangvien
            String sql1 = "select * from thitracnghiem.giang_vien";
            CallableStatement callableStatement1 = connection.prepareCall(sql1);
            ResultSet resultSet1 = callableStatement1.executeQuery();
            lstGiangVien = new LinkedList<GiangVien>();

            while (resultSet1.next()) {
                String ma_gv, name, user, pass, comment;
                ma_gv = resultSet1.getString("ma_gv");
                name = resultSet1.getString("name");
                user = resultSet1.getString("user");
                pass = resultSet1.getString("pass");
                comment = resultSet1.getString("comment");
                GiangVien temp = new GiangVien(ma_gv, name, user, pass, comment);
                lstGiangVien.add(temp);
            }

            // user_infor
            String sql2 = "select * from thitracnghiem.user_infor";
            CallableStatement callableStatement2 = connection.prepareCall(sql2);
            ResultSet resultSet2 = callableStatement2.executeQuery();
            lstUserInfor = new LinkedList<user_infor>();

            while (resultSet2.next()) {
                String mssv, ho_ten, sdt, lop, ngaysinh, email, _default;
                mssv = resultSet2.getString("mssv");
                ho_ten = resultSet2.getString("ho_ten");
                sdt = resultSet2.getString("sdt");
                lop = resultSet2.getString("lop");
                ngaysinh = resultSet2.getString("ngaysinh");
                email = resultSet2.getString("email");
                _default = resultSet2.getString("default");

                user_infor temp = new user_infor(mssv, ho_ten, sdt, lop, ngaysinh, email, _default);
                lstUserInfor.add(temp);
            }

            // for Favourite
            String sql3 = "select * from thitracnghiem.favourite";
            CallableStatement callableStatement3 = connection.prepareCall(sql3);
            ResultSet resultSet3 = callableStatement3.executeQuery();
            lstFavourite = new LinkedList<Favourite>();

            while (resultSet3.next()) {
                String id, mssv, idQuestion;
                id = resultSet3.getString("id");
                mssv = resultSet3.getString("mssv");
                idQuestion = resultSet3.getString("idQuestion");

                Favourite temp = new Favourite(id, mssv, idQuestion);
                lstFavourite.add(temp);
            }

            // for list_cau_hoi
            String sql4 = "select * from thitracnghiem.list_cau_hoi";
            CallableStatement callableStatement4 = connection.prepareCall(sql4);
            ResultSet resultSet4 = callableStatement4.executeQuery();
            lstListCauHoi = new LinkedList<ListCauHoi>();

            while (resultSet4.next()) {
                String id, id_list_cau_hoi, id_question;
                id = resultSet4.getString("id");
                id_list_cau_hoi = resultSet4.getString("id_list_cau_hoi");
                id_question = resultSet4.getString("id_question");

                ListCauHoi temp = new ListCauHoi(id, id_list_cau_hoi, id_question);
                lstListCauHoi.add(temp);
            }

            // for DiemThi
            String sql5 = "select * from thitracnghiem.sinhvien_baithi";
            CallableStatement callableStatement5 = connection.prepareCall(sql5);
            ResultSet resultSet5 = callableStatement5.executeQuery();
            lstSinhvien_Baithi = new LinkedList<Sinhvien_Baithi>();

            while (resultSet5.next()) {
                String id, mssv, bai_thi, list_cau_hoi, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_e, dap_an_f,
                        diem, isSubmited;
                id = resultSet5.getString("id");
                mssv = resultSet5.getString("mssv");
                bai_thi = resultSet5.getString("bai_thi");
                list_cau_hoi = resultSet5.getString("list_cau_hoi");
                dap_an_a = resultSet5.getString("dap_an_a");
                dap_an_b = resultSet5.getString("dap_an_b");
                dap_an_c = resultSet5.getString("dap_an_c");
                dap_an_d = resultSet5.getString("dap_an_d");
                dap_an_e = resultSet5.getString("dap_an_e");
                dap_an_f = resultSet5.getString("dap_an_f");
                diem = resultSet5.getString("diem");
                isSubmited = resultSet5.getString("isSubmited");
                Sinhvien_Baithi temp = new Sinhvien_Baithi(id, mssv, bai_thi, list_cau_hoi, dap_an_a, dap_an_b,
                        dap_an_c, dap_an_d, dap_an_e, dap_an_f, diem, isSubmited);
                lstSinhvien_Baithi.add(temp);
            }

            // for BaiThi
            String sql6 = "select * from thitracnghiem.bai_thi";
            CallableStatement callableStatement6 = connection.prepareCall(sql6);
            ResultSet resultSet6 = callableStatement6.executeQuery();
            lstBaithi = new LinkedList<BaiThi>();

            while (resultSet6.next()) {
                String id, ma_loai_kt, thoi_gian_bat_dau, bao_lau, thoi_gian_ket_thuc, lop, status, ten_bai_thi,
                        isDisabled;
                id = resultSet6.getString("id");
                ma_loai_kt = resultSet6.getString("ma_loai_kt");
                thoi_gian_bat_dau = resultSet6.getString("thoi_gian_bat_dau");
                bao_lau = resultSet6.getString("bao_lau");
                thoi_gian_ket_thuc = resultSet6.getString("thoi_gian_ket_thuc");
                ten_bai_thi = resultSet6.getString("ten_bai_thi");
                lop = resultSet6.getString("lop");
                status = resultSet6.getString("status");
                isDisabled = resultSet6.getString("isDisabled");

                BaiThi temp = new BaiThi(id, ma_loai_kt, thoi_gian_bat_dau, bao_lau, thoi_gian_ket_thuc, lop, status,
                        ten_bai_thi, isDisabled);
                lstBaithi.add(temp);
            }
            // for Gen_de
            String sql7 = "select * from thitracnghiem.gen_de";
            CallableStatement callableStatement7 = connection.prepareCall(sql7);
            ResultSet resultSet7 = callableStatement7.executeQuery();
            lstGende = new LinkedList<Gen_de>();

            while (resultSet7.next()) {
                String idgen_de, lop, bai_thi, list_cau_hoi;
                idgen_de = resultSet7.getString("idgen_de");
                lop = resultSet7.getString("lop");
                bai_thi = resultSet7.getString("bai_thi");
                list_cau_hoi = resultSet7.getString("list_cau_hoi");

                Gen_de temp = new Gen_de(idgen_de, lop, bai_thi, list_cau_hoi);
                lstGende.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * Tạm thời các truy vấn - list question
 * localhost:8080/apiThitracnghiem/api01/General?doing=getListQuestion&
 * startId=0 - list dap an
 * localhost:8080/apiThitracnghiem/api01/General?doing=getListDapAn&startId=
 * 0
 *
 * - authen localhost:8080/apiThitracnghiem/api01/General/getAuthen body: +
 * +for sinhvien userName: 1851220056 pass: 31*07*2000
 *
 * + for giangvien userName:haodo pass: 123
 *
 *
 * -general getListQuestion + search getAuthen
 *
 * - for mssv getListFavourite getList_Baithi (GK, CK) getBaiLam_Diem_BaiThi
 * posBailam_DapAn_BaiThi
 *
 * - for giang_vien getDSLop getDSSV_Lop getBangDiem_Lop_BaiThi
 * getBaiThi_Sinhvien postLichThi_Lop_BaiThi
 *
 * - function search FuzzySearch.partialRatio(pre, y);
 *
 */

/*
 * process thread
 */

class Process extends Thread {
    static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public String processCode = "";
    Socket soc;
    TcpServer server;
    DataInputStream dis;
    DataOutputStream dos;
    String st = "";

    public Process(Socket soc, TcpServer server) {
        this.soc = soc;
        this.server = server;

        try {
            dis = new DataInputStream(soc.getInputStream());
            dos = new DataOutputStream(soc.getOutputStream());
            soc.setSoTimeout(10000);
            st = dis.readUTF();

            if (st.equals("stop")) {
                this.processCode = "stop";
                dos.writeUTF("Server will stop now");
            } else if (st.equals("")) {
                this.processCode = "timeout";
            } else {
                this.start();
            }
        } catch (Exception e) {
            System.out.println("err");
        }
    }

    public String getListFavourite(String mssv, int startId) {
        List<Question> lstQuestionNew = new LinkedList<Question>();

        List<String> lstIdQuestionOfFavourite = new LinkedList<String>();
        for (Favourite x : TcpServer.lstFavourite) {
            if (x.mssv.equals(mssv)) {
                lstIdQuestionOfFavourite.add(x.idQuestion);
            }
        }

        for (Question x : TcpServer.lstQuestion) {
            boolean ok = false;
            for (String y : lstIdQuestionOfFavourite) {
                if (x.id.equals(y)) {
                    ok = true;
                }
            }
            if (ok) {
                lstQuestionNew.add(x);
            }
        }

        // question
        List<String> lstBaithiForSV = new LinkedList<String>();
        for (Sinhvien_Baithi x : TcpServer.lstSinhvien_Baithi) {
            if (x.mssv.equals(mssv)) {
                lstBaithiForSV.add(x.bai_thi);
            }
        }

        boolean ok = true;
        for (String x : lstBaithiForSV) {
            for (BaiThi y : TcpServer.lstBaithi) {
                if (y.ma_loai_kt.equals(x) && y.status.equals("running")) {
                    ok = false;
                    break;
                }
            }
        }

        List<String> lstIdListQuestionOfMSSV = new LinkedList<String>();
        for (Favourite x : TcpServer.lstFavourite) {
            if (x.mssv.equals(mssv)) {
                lstIdListQuestionOfMSSV.add(x.idQuestion);
            }
        }

        if (!ok) {
            return "{\"status\": \"failed\"}";
        }

        int num = server.num;
        Random r = new Random();
        String nextId = (startId + num) + "";
        try {
            List<ListQuestionDatum> listData = new LinkedList<ListQuestionDatum>();

            for (int i = startId; i < Math.min(lstQuestionNew.size(), startId + num); i++) {
                Question temp = lstQuestionNew.get(i);
                String id = temp.id;
                String content = temp.content;
                String dap_an_a = temp.dap_an_a;
                String dap_an_b = temp.dap_an_b;
                String dap_an_c = temp.dap_an_c;
                String dap_an_d = temp.dap_an_d;
                String dap_an_e = temp.dap_an_e;
                String dap_an_f = temp.dap_an_f;

                int position = r.nextInt(10) + 3;
                int direction = r.nextInt() % 2 == 0 ? -1 : 1;
                String res = "";
                res += getAlphaNumericString(position - 1);
                res += temp.dap_an_dung;
                res += getAlphaNumericString(r.nextInt(15) + 1);

                if (direction == 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(res);
                    sb = sb.reverse();
                    res = sb.toString();
                }
                String dap_an_dung = res;
                String rate = temp.rate;
                String thuoc_chuong = temp.thuoc_chuong;
                String isFavourite = "0";
                boolean checkOk = false;
                for (String y : lstIdListQuestionOfMSSV) {
                    if (y.equals(id)) {
                        checkOk = true;
                        break;
                    }
                }
                if (checkOk) {
                    isFavourite = "1";
                }

                ListQuestionDatum tmpListQuestionDatum = new ListQuestionDatum(id, content, dap_an_a, dap_an_b,
                        dap_an_c, dap_an_d, dap_an_e, dap_an_f, direction + "", position + "", dap_an_dung, rate,
                        thuoc_chuong, isFavourite);
                listData.add(tmpListQuestionDatum);
            }
            QuestionModel qm = new QuestionModel(nextId, listData);
            return gson.toJson(qm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    // ben general thi khong truyen hoac truyen rong --> set bien do bang null
    public String getListQuestion(String key, int startId, String mssv) {
        List<Question> lstQuestionNew = new LinkedList<Question>();
        if (!key.equals("null")) {
            key = Util.decode(key);
            key = Util.compound2Unicode(key);

            for (Question x : TcpServer.lstQuestion) {
                int temp = FuzzySearch.partialRatio(key.toLowerCase(), x.content.toLowerCase());
                if (temp > 80) {
                    lstQuestionNew.add(x);
                }
            }
        }

        if (lstQuestionNew.size() == 0) {
            for (Question x : TcpServer.lstQuestion) {
                lstQuestionNew.add(x);
            }
        }

        // question
        List<String> lstBaithiForSV = new LinkedList<String>();
        for (Sinhvien_Baithi x : TcpServer.lstSinhvien_Baithi) {
            if (x.mssv.equals(mssv)) {
                lstBaithiForSV.add(x.bai_thi);
            }
        }

        boolean ok = true;
        for (String x : lstBaithiForSV) {
            for (BaiThi y : TcpServer.lstBaithi) {
                if (y.ma_loai_kt.equals(x) && y.status.equals("running")) {
                    ok = false;
                    break;
                }
            }
        }

        List<String> lstIdListQuestionOfMSSV = new LinkedList<String>();

        for (Favourite x : TcpServer.lstFavourite) {
            if (x.mssv.equals(mssv)) {
                lstIdListQuestionOfMSSV.add(x.idQuestion);
            }
        }

        if (!ok) {
            return "{\"status\": \"failed\"}";
        }

        // yyy
        // neu null thi load all data
        int num = server.num;
        Random r = new Random();
        String nextId = (startId + num) + "";
        try {
            List<ListQuestionDatum> listData = new LinkedList<ListQuestionDatum>();

            for (int i = startId; i < Math.min(lstQuestionNew.size(), startId + num); i++) {
                Question temp = lstQuestionNew.get(i);
                String id = temp.id;
                String content = temp.content;
                String dap_an_a = temp.dap_an_a;
                String dap_an_b = temp.dap_an_b;
                String dap_an_c = temp.dap_an_c;
                String dap_an_d = temp.dap_an_d;
                String dap_an_e = temp.dap_an_e;
                String dap_an_f = temp.dap_an_f;

                int position = r.nextInt(10) + 3;
                int direction = r.nextInt() % 2 == 0 ? -1 : 1;
                String res = "";
                res += getAlphaNumericString(position - 1);
                res += temp.dap_an_dung;
                res += getAlphaNumericString(r.nextInt(15) + 1);

                if (direction == 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(res);
                    sb = sb.reverse();
                    res = sb.toString();
                }
                String dap_an_dung = res;
                String rate = temp.rate;
                String thuoc_chuong = temp.thuoc_chuong;
                String isFavourite = "0";

                boolean checkOk = false;
                for (String y : lstIdListQuestionOfMSSV) {
                    if (y.equals(id)) {
                        checkOk = true;
                        break;
                    }
                }
                if (checkOk) {
                    isFavourite = "1";
                }

                ListQuestionDatum tmpListQuestionDatum = new ListQuestionDatum(id, content, dap_an_a, dap_an_b,
                        dap_an_c, dap_an_d, dap_an_e, dap_an_f, direction + "", position + "", dap_an_dung, rate,
                        thuoc_chuong, isFavourite);
                listData.add(tmpListQuestionDatum);
            }
            QuestionModel qm = new QuestionModel(nextId, listData);
            return gson.toJson(qm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }

    public String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    private List<Integer> genListCauHoi(int n, int socauhoi) {
        List<Integer> lstResult = new LinkedList<Integer>();
        Random r = new Random();
        // System.out.println("n: " + n);
        while (lstResult.size() < socauhoi) {
            int i = r.nextInt(Math.abs(n));
            if (i <= 0)
                i = 1;

            if (!lstResult.contains(i)) {
                lstResult.add(i);
            }
        }

        return lstResult;
    }

    public void run() {
        try {
            if (st.equals("general")) {
                String doing = dis.readUTF();
                System.out.println("Doing: " + doing);
                if (doing.equals("getListQuestion")) {
                    String key = dis.readUTF();
                    int startId = Integer.parseInt(dis.readUTF());
                    String mssv = dis.readUTF();

                    String res = getListQuestion(key, startId, mssv);
                    dos.writeUTF(res);
                } else if (doing.equals("getAuthen")) {
                    String userName = dis.readUTF();
                    String pass = dis.readUTF();
                    String res = getAuthen(userName, pass);
                    dos.writeUTF(res);
                } else if (doing.equals("getInfo")) {
                    String actionInfo = dis.readUTF();

                    if (actionInfo.equals("getDSLop")) {
                        Set<String> hash_Set = new HashSet<String>();
                        for (user_infor x : TcpServer.lstUserInfor) {
                            hash_Set.add(x.lop);
                        }
                        String res = getJsonFromSet(hash_Set);
                        dos.writeUTF(res);
                    } else if (actionInfo.equals("getListInfoOfLop")) {
                        String lop = dis.readUTF();
                        String mssv = dis.readUTF();

                        List<ListInfoSinhvien> lstListInfoSinhvien = new LinkedList<ListInfoSinhvien>();
                        for (user_infor x : TcpServer.lstUserInfor) {
                            if (x.lop.equals(lop)) {
                                ListInfoSinhvien temp = new ListInfoSinhvien(x.mssv, x.ho_ten, x.email);
                                lstListInfoSinhvien.add(temp);
                            }
                        }
                        // 2019-07-24 12:30:00
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());

                        List<ListInfoBaithi> lstListInfoBaithi = new LinkedList<ListInfoBaithi>();
                        for (BaiThi x : TcpServer.lstBaithi) {
                            if (x.lop.equals(lop)) {

                                String ma_loai_kt = x.ma_loai_kt;
                                Sinhvien_Baithi sv_bt = null;

                                for (Sinhvien_Baithi y : TcpServer.lstSinhvien_Baithi) {
                                    if (y.bai_thi.equals(ma_loai_kt) && y.mssv.equals(mssv)) {
                                        sv_bt = y;
                                    }
                                }
                                if (sv_bt != null && sv_bt.isSubmited.equals("1")) {
                                    x.status = "finished";
                                }
                                ListInfoBaithi temp = new ListInfoBaithi(x.ma_loai_kt, x.thoi_gian_bat_dau, x.bao_lau,
                                        x.thoi_gian_ket_thuc, x.lop, x.status, x.ten_bai_thi, dateFormat.format(date));
                                lstListInfoBaithi.add(temp);
                            }
                        }
                        List<ListInfoBaithi> lstListInfoBaithiSorted = new LinkedList<ListInfoBaithi>();
                        for (ListInfoBaithi x : lstListInfoBaithi) {
                            if (x.getStatus().equals("running")) {
                                lstListInfoBaithiSorted.add(x);
                            }
                        }
                        for (ListInfoBaithi x : lstListInfoBaithi) {
                            if (x.getStatus().equals("upcoming")) {
                                lstListInfoBaithiSorted.add(x);
                            }
                        }
                        for (ListInfoBaithi x : lstListInfoBaithi) {
                            if (x.getStatus().equals("finished")) {
                                lstListInfoBaithiSorted.add(x);
                            }
                        }
                        String res = gson.toJson(new InfosLop(lop, lstListInfoSinhvien, lstListInfoBaithiSorted));
                        dos.writeUTF(res);
                    } else if (actionInfo.equals("getFavourite")) {
                        String mssv = dis.readUTF();
                        int startId = Integer.parseInt(dis.readUTF());

                        String res = getListFavourite(mssv, startId);
                        dos.writeUTF(res);
                    } else if (actionInfo.equals("getDethi")) {
                        String mssv = dis.readUTF();
                        String bai_thi = dis.readUTF();

                        String res = getDethi(mssv, bai_thi);
                        dos.writeUTF(res);
                    } else if (actionInfo.equals("getInfoDethiOfLopBaithi")) {
                        String lop = dis.readUTF();
                        String bai_thi = dis.readUTF();
                        int startId = Integer.parseInt(dis.readUTF());
                        String res = getInfoDethiOfLopBaithi(lop, bai_thi, startId);
                        dos.writeUTF(res);
                    } else if (actionInfo.equals("getInfoDethiOfMSSVBaithi")) {
                        String mssv = dis.readUTF();
                        int startId = Integer.parseInt(dis.readUTF());
                        String res = getInfoDethiOfMSSVBaithi(mssv, startId);
                        dos.writeUTF(res);
                    } else if (actionInfo.equals("getDiem_Baithi")) {
                        String mssv = dis.readUTF();
                        String bai_thi = dis.readUTF();

                        String res = getDiem_Baithi(mssv, bai_thi);
                        dos.writeUTF(res);

                    }
                }
            } else if (st.equals("sinhvien")) {
                String doing = dis.readUTF();

                if (doing.equals("postDapAn_Bailam")) {
                    String username = dis.readUTF();
                    String pass = dis.readUTF();
                    String bai_thi = dis.readUTF();
                    String dap_an_a = dis.readUTF();
                    String dap_an_b = dis.readUTF();
                    String dap_an_c = dis.readUTF();
                    String dap_an_d = dis.readUTF();
                    String dap_an_e = dis.readUTF();
                    String dap_an_f = dis.readUTF();
                    String isSubmited = dis.readUTF();

                    Connection connection = new MySQLConnectionManager().getConnection("localhost:3306",
                            "thitracnghiem", "root", "123456");

                    boolean ok = false;
                    for (user_infor x : TcpServer.lstUserInfor) {
                        if (username.equals(x.mssv) && pass.equals(x._default)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) {
                        dos.writeUTF("{\"status\": \"failed\"}");
                        return;
                    }

                    ok = false;
                    Sinhvien_Baithi sv_bt = null;
                    for (Sinhvien_Baithi x : TcpServer.lstSinhvien_Baithi) {
                        if (x.bai_thi.equals(bai_thi) && x.mssv.equals(username)) {
                            sv_bt = x;
                            if (x.isSubmited == null) {
                                ok = true;
                                break;
                            } else if (x.isSubmited != null && !x.isSubmited.equals("1")) {
                                ok = true;
                                break;
                            }
                        }
                    }

                    if (!ok) {
                        System.out.println(sv_bt.toString());
                        dos.writeUTF("{\"status\": \"failed\"}");
                        return;
                    }

                    BaiThi tempBaithi = null;
                    for (BaiThi x : TcpServer.lstBaithi) {
                        if (x.ma_loai_kt.equals(bai_thi) && !x.isDisabled.equals("1")) {
                            tempBaithi = x;
                            break;
                        }
                    }
                    if (tempBaithi == null) {
                        dos.writeUTF("{\"status\": \"failed\"}");
                        return;
                    }
                    ok = false;
                    for (BaiThi x : TcpServer.lstBaithi) {
                        if (x.ma_loai_kt.equals(bai_thi)) {
                            if (x.status.equals("running")) {
                                ok = true;
                                break;
                            } else if (x.status.equals("finished")) {
                                Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                        .parse(tempBaithi.thoi_gian_ket_thuc);
                                long t = System.currentTimeMillis() - date1.getTime();
                                if (t <= 15 * 60 * 1000) {
                                    ok = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!ok) {
                        dos.writeUTF("{\"status\": \"failed\"}");
                        return;
                    }

                    if (sv_bt != null) {
                        sv_bt.mssv = username;
                        sv_bt.bai_thi = bai_thi;
                        sv_bt.dap_an_a = dap_an_a;
                        sv_bt.dap_an_b = dap_an_b;
                        sv_bt.dap_an_c = dap_an_c;
                        sv_bt.dap_an_d = dap_an_d;
                        sv_bt.dap_an_e = dap_an_e;
                        sv_bt.dap_an_f = dap_an_f;
                        sv_bt.isSubmited = isSubmited;
                    }


                    if (isSubmited.equals("1")) {
                        String[] _dap_an_a = null;
                        String[] _dap_an_b = null;
                        String[] _dap_an_c = null;
                        String[] _dap_an_d = null;
                        String[] _dap_an_e = null;
                        String[] _dap_an_f = null;
                        if (sv_bt.dap_an_a != null && !sv_bt.dap_an_a.equals("null") && !sv_bt.dap_an_a.equals("NULL")
                                && !sv_bt.dap_an_a.equals("")) {
                            _dap_an_a = sv_bt.dap_an_a.split(";");
                        }
                        if (sv_bt.dap_an_b != null && !sv_bt.dap_an_b.equals("null") && !sv_bt.dap_an_b.equals("NULL")
                                && !sv_bt.dap_an_b.equals("")) {
                            _dap_an_b = sv_bt.dap_an_b.split(";");
                        }
                        if (sv_bt.dap_an_c != null && !sv_bt.dap_an_c.equals("null") && !sv_bt.dap_an_c.equals("NULL")
                                && !sv_bt.dap_an_c.equals("")) {
                            _dap_an_c = sv_bt.dap_an_c.split(";");
                        }
                        if (sv_bt.dap_an_d != null && !sv_bt.dap_an_d.equals("null") && !sv_bt.dap_an_d.equals("NULL")
                                && !sv_bt.dap_an_d.equals("")) {
                            _dap_an_d = sv_bt.dap_an_d.split(";");
                        }
                        if (sv_bt.dap_an_e != null && !sv_bt.dap_an_e.equals("null") && !sv_bt.dap_an_e.equals("NULL")
                                && !sv_bt.dap_an_e.equals("")) {
                            _dap_an_e = sv_bt.dap_an_e.split(";");
                        }
                        if (sv_bt.dap_an_f != null && !sv_bt.dap_an_f.equals("null") && !sv_bt.dap_an_f.equals("NULL")
                                && !sv_bt.dap_an_f.equals("")) {
                            _dap_an_f = sv_bt.dap_an_f.split(";");
                        }
                        Map<String, String> mapDapAn = new ConcurrentHashMap<String, String>();

                        if (_dap_an_a != null) {
                            for (String x : _dap_an_a) {
                                mapDapAn.put(x, "A");
                            }
                        }
                        if (_dap_an_b != null) {
                            for (String x : _dap_an_b) {
                                mapDapAn.put(x, "B");
                            }
                        }
                        if (_dap_an_c != null) {
                            for (String x : _dap_an_c) {
                                mapDapAn.put(x, "C");
                            }
                        }
                        if (_dap_an_d != null) {
                            for (String x : _dap_an_d) {
                                mapDapAn.put(x, "D");
                            }
                        }
                        if (_dap_an_e != null) {
                            for (String x : _dap_an_e) {
                                mapDapAn.put(x, "E");
                            }
                        }

                        if (_dap_an_f != null) {
                            for (String x : _dap_an_f) {
                                mapDapAn.put(x, "F");
                            }
                        }

                        int n = 40;
                        int cnt = 0;
                        for (Map.Entry<String, String> entry : mapDapAn.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();

                            boolean check = false;
                            for (Question y : TcpServer.lstQuestion) {
                                if (key.equals(y.id) && value.equals(y.dap_an_dung)) {
                                    check = true;
                                    break;
                                }
                            }
                            if (check)
                                cnt++;
                        }
                        String diem = (cnt * 10.0 / n) + "";
                        sv_bt.diem = diem;
                    }

                    // for bai_thi
                    String query = "CALL update_sinhvien_baithi(?,?,?,?,?,?,?,?,?,?); ";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);

                    preparedStmt.setString(1, sv_bt.mssv);
                    preparedStmt.setString(2, sv_bt.bai_thi);
                    preparedStmt.setString(3, sv_bt.dap_an_a);
                    preparedStmt.setString(4, sv_bt.dap_an_b);
                    preparedStmt.setString(5, sv_bt.dap_an_c);
                    preparedStmt.setString(6, sv_bt.dap_an_d);
                    preparedStmt.setString(7, sv_bt.dap_an_e);
                    preparedStmt.setString(8, sv_bt.dap_an_f);
                    preparedStmt.setString(9, sv_bt.diem);
                    preparedStmt.setString(10, sv_bt.isSubmited);
                    preparedStmt.execute();

                    Sinhvien_Baithi_Model temp = new Sinhvien_Baithi_Model("success", sv_bt.mssv, sv_bt.bai_thi,
                            sv_bt.dap_an_a, sv_bt.dap_an_b, sv_bt.dap_an_c, sv_bt.dap_an_d, sv_bt.dap_an_e,
                            sv_bt.dap_an_f, sv_bt.diem, sv_bt.isSubmited);
                    String res = gson.toJson(temp);


                    // update finished
                    String query1 = "call setStatusBaithi_finished(?);";
                    CallableStatement callableStatement1 = connection.prepareCall(query1);
                    callableStatement1.setString(1, bai_thi);

                    ResultSet resultSet1 = callableStatement1.executeQuery();
                    String count01 = "0";
                    while (resultSet1.next()) {
                        count01 = resultSet1.getString("count01");
                    }
                    if (count01.equals("1")) {
                        server.refreshData_ForBaithi();
                    }

                    /* for sv */
                    server.refreshData_ForSV();
                    server.refreshData_ForBaithi();
                    dos.writeUTF(res);
                } else if (doing.equals("postFavourite")) {
                    String username = dis.readUTF();
                    String pass = dis.readUTF();
                    String idQuestion = dis.readUTF();
                    String isFavourite = dis.readUTF();

                    boolean ok = false;
                    for (user_infor x : TcpServer.lstUserInfor) {
                        if (username.equals(x.mssv) && pass.equals(x._default)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) {
                        dos.writeUTF("{\"status\": \"failed\"}");
                        return;
                    }

                    try (Connection connection = new MySQLConnectionManager().getConnection("localhost:3306",
                            "thitracnghiem", "root", "123456");) {
                        // for bai_thi
                        String query = "CALL update_sinhvien_favourite(?,?,?); ";
                        PreparedStatement preparedStmt = connection.prepareStatement(query);

                        preparedStmt.setString(1, username);
                        preparedStmt.setString(2, idQuestion);
                        preparedStmt.setString(3, isFavourite);
                        preparedStmt.execute();

                        server.refresh_Favourite();

                        Favourite_Model temp = new Favourite_Model("success", username, idQuestion, isFavourite);
                        String res = gson.toJson(temp);
                        dos.writeUTF(res);
                    } catch (Exception ex1) {
                    }

                }

                // System.out.println("======Sinhvien posted==========");
                server.refreshData_ForSV();

            } else if (st.equals("giangvien")) {
                // giangvien
                // postLichThi_Lop_BaiThi
                String doing = dis.readUTF();
                if (doing.equals("postLichThi_Lop_BaiThi")) {
                    String thoi_gian_bat_dau = Util.decode(dis.readUTF());
                    String bao_lau = Util.decode(dis.readUTF());
                    String lop = Util.decode(dis.readUTF());
                    String ten_bai_thi = Util.decode(dis.readUTF());
                    String username = Util.decode(dis.readUTF());
                    String pass = Util.decode(dis.readUTF());

                    Connection connection = new MySQLConnectionManager().getConnection("localhost:3306",
                            "thitracnghiem", "root", "123456");

                    boolean ok = false;
                    /*
                     * System.out.println(thoi_gian_bat_dau); System.out.println(bao_lau);
                     * System.out.println(lop); System.out.println(ten_bai_thi);
                     * System.out.println(username); System.out.println(pass);
                     */

                    for (GiangVien x : TcpServer.lstGiangVien) {
                        if (username.equals(x.user) && pass.equals(x.pass)) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) {
                        dos.writeUTF("{\"status\": \"failed\"}");
                        return;
                    }
                    String status = "upcoming";
                    String ma_loai_kt = lop + "_" + getAlphaNumericString(6);
                    String thoi_gian_ket_thuc = "";
                    String isDisabled = "0";

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(thoi_gian_bat_dau);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.MINUTE, Integer.parseInt(bao_lau));
                    Date currentDatePlusMinute = c.getTime();
                    thoi_gian_ket_thuc += dateFormat.format(currentDatePlusMinute);

                    List<Question> lstTempQuestion = new LinkedList<Question>();
                    for (Question x : TcpServer.lstQuestion) {
                        if (ten_bai_thi.contains(x.thuoc_chuong)) {
                            lstTempQuestion.add(x);
                        }
                    }

                    List<String> lstMssv = new LinkedList<String>();

                    for (user_infor x : TcpServer.lstUserInfor) {
                        if (x.lop.equals(lop)) {
                            lstMssv.add(x.mssv);
                        }
                    }

                    List<String> lstIdListCauHoi = new LinkedList<String>();

                    // for bai_thi
                    String query = " insert into thitracnghiem.bai_thi (ma_loai_kt, thoi_gian_bat_dau, bao_lau, thoi_gian_ket_thuc, lop, status, ten_bai_thi, isDisabled)"
                            + " values (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStmt = connection.prepareStatement(query);

                    preparedStmt.setString(1, ma_loai_kt);
                    preparedStmt.setString(2, thoi_gian_bat_dau);
                    preparedStmt.setString(3, bao_lau);
                    preparedStmt.setString(4, thoi_gian_ket_thuc);
                    preparedStmt.setString(5, lop);
                    preparedStmt.setString(6, status);
                    preparedStmt.setString(7, ten_bai_thi);
                    preparedStmt.setString(8, isDisabled);
                    preparedStmt.execute();

                    dos.writeUTF("{\"status\": \"success\"}");

                    // for gen_de
                    String query1 = " insert into thitracnghiem.gen_de (lop, bai_thi, list_cau_hoi)"
                            + " values (?, ?, ?)";
                    PreparedStatement preparedStmt1 = connection.prepareStatement(query1);
                    String idListCauHoi;
                    for (int i = 1; i <= 10; i++) {

                        idListCauHoi = getCodeListCauHoi(ma_loai_kt, i);
                        lstIdListCauHoi.add(idListCauHoi);

                        preparedStmt1.setString(1, lop);
                        preparedStmt1.setString(2, ma_loai_kt);
                        preparedStmt1.setString(3, idListCauHoi); // ListCauHoi.18CT2_r9PsTH.D01
                        preparedStmt1.addBatch();
                    }

                    if (preparedStmt1 != null) {
                        preparedStmt1.executeBatch();
                    }

                    // for list_cau_hoi
                    String query2 = " insert into thitracnghiem.list_cau_hoi (id_list_cau_hoi, id_question)"
                            + " values (?, ?)";
                    PreparedStatement preparedStmt2 = connection.prepareStatement(query2);

                    for (int i = 0; i < lstIdListCauHoi.size(); i++) {
                        String id_list_cau_hoi = lstIdListCauHoi.get(i);
                        String id_question = "";

                        List<Integer> lstTemp = genListCauHoi(lstTempQuestion.size(), 40);
                        for (Integer x : lstTemp) {
                            id_question = lstTempQuestion.get(x).id;
                            preparedStmt2.setString(1, id_list_cau_hoi);
                            preparedStmt2.setString(2, id_question);
                            preparedStmt2.addBatch();
                        }
                        preparedStmt2.executeBatch();
                    }

                    Random r = new Random();
                    // for sinhvien_baithi
                    String query3 = " insert into thitracnghiem.sinhvien_baithi (mssv, bai_thi, list_cau_hoi)"
                            + " values (?, ?, ?)";
                    PreparedStatement preparedStmt3 = connection.prepareStatement(query3);
                    for (int i = 0; i < lstMssv.size(); i++) {
                        String mssvTemp = lstMssv.get(i);
                        String bai_thi = ma_loai_kt;
                        int idx = r.nextInt(lstIdListCauHoi.size());
                        String list_cau_hoi_Temp = lstIdListCauHoi.get(idx);
                        preparedStmt3.setString(1, mssvTemp);
                        preparedStmt3.setString(2, bai_thi);
                        preparedStmt3.setString(3, list_cau_hoi_Temp);
                        preparedStmt3.addBatch();
                    }
                    if (preparedStmt3 != null) {
                        preparedStmt3.executeBatch();
                    }

                    connection.close();
                    server.refreshData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDiem_Baithi(String mssv, String bai_thi) {
        String msgFailed = "{\"status\": \"failed\"}";
        Sinhvien_Baithi sv_bt = null;

        for (Sinhvien_Baithi x : TcpServer.lstSinhvien_Baithi) {
            if (x.mssv.equals(mssv) && x.bai_thi.equals(bai_thi)) {
                sv_bt = x;
                break;
            }
        }

        if (sv_bt == null) {
            return msgFailed;
        }

        String[] dap_an_a = null;
        String[] dap_an_b = null;
        String[] dap_an_c = null;
        String[] dap_an_d = null;
        String[] dap_an_e = null;
        String[] dap_an_f = null;

        if (sv_bt.dap_an_a != null && !sv_bt.dap_an_a.equals("null") && !sv_bt.dap_an_a.equals("NULL")
                && !sv_bt.dap_an_a.equals("")) {
            dap_an_a = sv_bt.dap_an_a.split(";");
        }
        if (sv_bt.dap_an_b != null && !sv_bt.dap_an_b.equals("null") && !sv_bt.dap_an_b.equals("NULL")
                && !sv_bt.dap_an_b.equals("")) {
            dap_an_b = sv_bt.dap_an_b.split(";");
        }
        if (sv_bt.dap_an_c != null && !sv_bt.dap_an_c.equals("null") && !sv_bt.dap_an_c.equals("NULL")
                && !sv_bt.dap_an_c.equals("")) {
            dap_an_c = sv_bt.dap_an_c.split(";");
        }
        if (sv_bt.dap_an_d != null && !sv_bt.dap_an_d.equals("null") && !sv_bt.dap_an_d.equals("NULL")
                && !sv_bt.dap_an_d.equals("")) {
            dap_an_d = sv_bt.dap_an_d.split(";");
        }
        if (sv_bt.dap_an_e != null && !sv_bt.dap_an_e.equals("null") && !sv_bt.dap_an_e.equals("NULL")
                && !sv_bt.dap_an_e.equals("")) {
            dap_an_e = sv_bt.dap_an_e.split(";");
        }
        if (sv_bt.dap_an_f != null && !sv_bt.dap_an_f.equals("null") && !sv_bt.dap_an_f.equals("NULL")
                && !sv_bt.dap_an_f.equals("")) {
            dap_an_f = sv_bt.dap_an_f.split(";");
        }
        Map<String, String> mapDapAn = new ConcurrentHashMap<String, String>();

        if (dap_an_a != null) {
            for (String x : dap_an_a) {
                mapDapAn.put(x, "A");
            }
        }

        if (dap_an_b != null) {
            for (String x : dap_an_b) {
                mapDapAn.put(x, "B");
            }
        }
        if (dap_an_c != null) {
            for (String x : dap_an_c) {
                mapDapAn.put(x, "C");
            }
        }

        if (dap_an_d != null) {
            for (String x : dap_an_d) {
                mapDapAn.put(x, "D");
            }
        }

        if (dap_an_e != null) {
            for (String x : dap_an_e) {
                mapDapAn.put(x, "E");
            }
        }

        if (dap_an_f != null) {
            for (String x : dap_an_f) {
                mapDapAn.put(x, "F");
            }
        }

        int n = 40;
        int cnt = 0;
        for (Map.Entry<String, String> entry : mapDapAn.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            boolean ok = false;
            for (Question y : TcpServer.lstQuestion) {
                if (key.equals(y.id) && value.equals(y.dap_an_dung)) {
                    ok = true;
                    break;
                }
            }
            if (ok)
                cnt++;
        }

        String diem = (cnt * 10.0 / n) + "";
        sv_bt.diem = diem;

        DiemThi_Model temp = new DiemThi_Model("success", sv_bt.mssv, sv_bt.bai_thi, sv_bt.list_cau_hoi, sv_bt.dap_an_a,
                sv_bt.dap_an_b, sv_bt.dap_an_c, sv_bt.dap_an_d, sv_bt.dap_an_e, sv_bt.dap_an_f, sv_bt.diem);
        String res = gson.toJson(temp);
        try {
            dos.writeUTF(res);
        } catch (Exception e) {
            // TODO: handle exception
        }

        /*
         * try (Connection connection = new
         * MySQLConnnectionManager().getConnection("localhost:3306",
         * "thitracnghiem", "root", "123456");) { // for bai_thi String query
         * = "CALL insertDiemFromSV_BaiThi(?,?,?); "; PreparedStatement preparedStmt =
         * connection.prepareStatement(query);
         *
         * preparedStmt.setString (1, sv_bt.mssv); preparedStmt.setString (2,
         * sv_bt.bai_thi); preparedStmt.setString(3, sv_bt.diem);
         * preparedStmt.execute();
         *
         * DiemThi_Model temp = new DiemThi_Model("success", sv_bt.mssv, sv_bt.bai_thi,
         * sv_bt.list_cau_hoi, sv_bt.dap_an_a, sv_bt.dap_an_b, sv_bt.dap_an_c,
         * sv_bt.dap_an_d, sv_bt.dap_an_e, sv_bt.dap_an_f, sv_bt.diem); String res =
         * gson.toJson(temp); dos.writeUTF(res); } catch(Exception ex1) { }
         */

        return msgFailed;
    }

    private String getInfoDethiOfLopBaithi(String lop, String bai_thi, int startId) {
        BaiThi tempBaiThi = null;
        String msgFailed = "{\"status\": \"failed\"}";
        for (BaiThi x : TcpServer.lstBaithi) {
            if (x.ma_loai_kt.equals(bai_thi) && x.isDisabled.equals("0")) {
                tempBaiThi = x;
                break;
            }
        }
        if (tempBaiThi == null) {
            return msgFailed;
        }

        // based tempBaiThi
        Random r = new Random();
        List<Dethi_Model> dethiModels = new LinkedList<Dethi_Model>();

        List<Sinhvien_Baithi> lstSinhvienOfBaiThi = new LinkedList<Sinhvien_Baithi>();
        for (Sinhvien_Baithi sv_bt : TcpServer.lstSinhvien_Baithi) {
            if (sv_bt.bai_thi.equals(bai_thi)) {
                lstSinhvienOfBaiThi.add(sv_bt);
            }
        }

        int num = 3;
        String nextId = (startId + num) + "";

        for (int j = startId; j < Math.min(lstSinhvienOfBaiThi.size(), startId + num); j++) {
            Sinhvien_Baithi sv_bt = lstSinhvienOfBaiThi.get(j);

            List<String> lstIdQuestion = new LinkedList<String>();
            List<Question> lstQuestionNew = new LinkedList<Question>();
            for (ListCauHoi x : TcpServer.lstListCauHoi) {
                if (x.id_list_cau_hoi.equals(sv_bt.list_cau_hoi)) {
                    lstIdQuestion.add(x.id_question);
                }
            }
            for (Question x : TcpServer.lstQuestion) {
                boolean ok = false;
                for (String y : lstIdQuestion) {
                    if (x.id.equals(y)) {
                        ok = true;
                    }
                }
                if (ok) {
                    lstQuestionNew.add(x);
                }
            }
            List<ListCauHoiDetail> listData = new LinkedList<ListCauHoiDetail>();
            try {
                for (int i = 0; i < lstQuestionNew.size(); i++) {
                    Question temp = lstQuestionNew.get(i);
                    String id = temp.id;
                    String content = temp.content;
                    String dap_an_a = temp.dap_an_a;
                    String dap_an_b = temp.dap_an_b;
                    String dap_an_c = temp.dap_an_c;
                    String dap_an_d = temp.dap_an_d;
                    String dap_an_e = temp.dap_an_e;
                    String dap_an_f = temp.dap_an_f;

                    int position = r.nextInt(10) + 3;
                    int direction = r.nextInt() % 2 == 0 ? -1 : 1;
                    String res = "";
                    res += getAlphaNumericString(position - 1);
                    res += temp.dap_an_dung;
                    res += getAlphaNumericString(r.nextInt(15) + 1);

                    if (direction == 1) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(res);
                        sb = sb.reverse();
                        res = sb.toString();
                    }
                    String dap_an_dung = res;
                    String rate = temp.rate;
                    String thuoc_chuong = temp.thuoc_chuong;

                    listData.add(new ListCauHoiDetail(id, content, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_e,
                            dap_an_f, direction + "", "" + position, dap_an_dung, rate, thuoc_chuong));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (sv_bt.dap_an_a == null) {
                sv_bt.dap_an_a = "null";
            }
            if (sv_bt.dap_an_b == null) {
                sv_bt.dap_an_b = "null";
            }
            if (sv_bt.dap_an_c == null) {
                sv_bt.dap_an_c = "null";
            }
            if (sv_bt.dap_an_d == null) {
                sv_bt.dap_an_d = "null";
            }
            if (sv_bt.dap_an_e == null) {
                sv_bt.dap_an_e = "null";
            }
            if (sv_bt.dap_an_f == null) {
                sv_bt.dap_an_f = "null";
            }
            if (sv_bt.diem == null || sv_bt.diem.equals("null")) {
                sv_bt.diem = "0.0";
            }

            String ho_ten = null;
            for (user_infor yy : TcpServer.lstUserInfor) {
                if (yy.mssv.equals(sv_bt.mssv)) {
                    ho_ten = yy.ho_ten;
                    break;
                }
            }
            if (ho_ten == null) {
                ho_ten = "null";
            }

            String status = null;

            if (tempBaiThi != null && sv_bt != null) {
                status = tempBaiThi.status;
                if (tempBaiThi.status.equals("running") && sv_bt.isSubmited.equals("1")) {
                    status = "finished";
                }
            }

            if (status == null) {
                status = "upcoming";
            }

            Dethi_Model dtTemp = new Dethi_Model(sv_bt.mssv, ho_ten, sv_bt.bai_thi, sv_bt.list_cau_hoi, sv_bt.dap_an_a,
                    sv_bt.dap_an_b, sv_bt.dap_an_c, sv_bt.dap_an_d, sv_bt.dap_an_e, sv_bt.dap_an_f, sv_bt.diem, status,
                    listData);

            dethiModels.add(dtTemp);
        }

        String res = (gson.toJson(new InfoDethiOfLop(nextId, dethiModels)));
        return res;
    }

    private String getInfoDethiOfMSSVBaithi(String mssv, int startId) {
        Random r = new Random();
        List<Dethi_Model> dethiModels = new LinkedList<Dethi_Model>();

        List<Sinhvien_Baithi> lstSinhvienOfBaiThi = new LinkedList<Sinhvien_Baithi>();
        for (Sinhvien_Baithi sv_bt : TcpServer.lstSinhvien_Baithi) {
            if (sv_bt.mssv.equals(mssv)) {
                String _bai_thi = sv_bt.bai_thi;
                boolean ok = false;
                for (BaiThi y : TcpServer.lstBaithi) {
                    if (y.ma_loai_kt.equals(_bai_thi) && !y.isDisabled.equals("1")) {
                        ok = true;
                        break;
                    }
                }
                if (ok) {
                    lstSinhvienOfBaiThi.add(sv_bt);
                }
            }
        }

        String msgFailed = "{\"status\": \"failed\"}";

        for (int j = startId; j < lstSinhvienOfBaiThi.size(); j++) {
            Sinhvien_Baithi sv_bt = lstSinhvienOfBaiThi.get(j);

            String status = null;
            for (BaiThi yy : TcpServer.lstBaithi) {
                if (yy.ma_loai_kt.equals(sv_bt.bai_thi)) {
                    status = yy.status;
                    break;
                }
            }
            if (status != null && status.equals("running")) {
                return msgFailed;
            }
        }

        int num = 3;
        String nextId = (startId + num) + "";

        for (int j = startId; j < Math.min(lstSinhvienOfBaiThi.size(), startId + num); j++) {
            Sinhvien_Baithi sv_bt = lstSinhvienOfBaiThi.get(j);

            List<String> lstIdQuestion = new LinkedList<String>();
            List<Question> lstQuestionNew = new LinkedList<Question>();
            for (ListCauHoi x : TcpServer.lstListCauHoi) {
                if (x.id_list_cau_hoi.equals(sv_bt.list_cau_hoi)) {
                    lstIdQuestion.add(x.id_question);
                }
            }
            for (Question x : TcpServer.lstQuestion) {
                boolean ok = false;
                for (String y : lstIdQuestion) {
                    if (x.id.equals(y)) {
                        ok = true;
                    }
                }
                if (ok) {
                    lstQuestionNew.add(x);
                }
            }
            List<ListCauHoiDetail> listData = new LinkedList<ListCauHoiDetail>();
            try {
                for (int i = 0; i < lstQuestionNew.size(); i++) {
                    Question temp = lstQuestionNew.get(i);
                    String id = temp.id;
                    String content = temp.content;
                    String dap_an_a = temp.dap_an_a;
                    String dap_an_b = temp.dap_an_b;
                    String dap_an_c = temp.dap_an_c;
                    String dap_an_d = temp.dap_an_d;
                    String dap_an_e = temp.dap_an_e;
                    String dap_an_f = temp.dap_an_f;

                    int position = r.nextInt(10) + 3;
                    int direction = r.nextInt() % 2 == 0 ? -1 : 1;
                    String res = "";
                    res += getAlphaNumericString(position - 1);
                    res += temp.dap_an_dung;
                    res += getAlphaNumericString(r.nextInt(15) + 1);

                    if (direction == 1) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(res);
                        sb = sb.reverse();
                        res = sb.toString();
                    }
                    String dap_an_dung = res;
                    String rate = temp.rate;
                    String thuoc_chuong = temp.thuoc_chuong;

                    listData.add(new ListCauHoiDetail(id, content, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_e,
                            dap_an_f, direction + "", "" + position, dap_an_dung, rate, thuoc_chuong));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (sv_bt.dap_an_a == null) {
                sv_bt.dap_an_a = "null";
            }
            if (sv_bt.dap_an_b == null) {
                sv_bt.dap_an_b = "null";
            }
            if (sv_bt.dap_an_c == null) {
                sv_bt.dap_an_c = "null";
            }
            if (sv_bt.dap_an_d == null) {
                sv_bt.dap_an_d = "null";
            }
            if (sv_bt.dap_an_e == null) {
                sv_bt.dap_an_e = "null";
            }
            if (sv_bt.dap_an_f == null) {
                sv_bt.dap_an_f = "null";
            }
            if (sv_bt.diem == null || sv_bt.diem.equals("null")) {
                sv_bt.diem = "0.0";
            }

            String ho_ten = null;
            for (user_infor yy : TcpServer.lstUserInfor) {
                if (yy.mssv.equals(sv_bt.mssv)) {
                    ho_ten = yy.ho_ten;
                    break;
                }
            }
            if (ho_ten == null) {
                ho_ten = "null";
            }

            String status = null;
            for (BaiThi yy : TcpServer.lstBaithi) {
                if (yy.ma_loai_kt.equals(sv_bt.bai_thi)) {
                    status = yy.status;
                    break;
                }
            }
            if (status == null) {
                status = "upcoming";
            }

            if (status.equals("running") && sv_bt.isSubmited.equals("1")) {
                status = "finished";
            }

            Dethi_Model dtTemp = new Dethi_Model(sv_bt.mssv, ho_ten, sv_bt.bai_thi, sv_bt.list_cau_hoi, sv_bt.dap_an_a,
                    sv_bt.dap_an_b, sv_bt.dap_an_c, sv_bt.dap_an_d, sv_bt.dap_an_e, sv_bt.dap_an_f, sv_bt.diem, status,
                    listData);

            dethiModels.add(dtTemp);
        }

        String res = (gson.toJson(new InfoDethiOfLop(nextId, dethiModels)));
        return res;
    }

    private String getDethi(String mssv, String bai_thi) {
        BaiThi tempBaiThi = null;
        for (BaiThi x : TcpServer.lstBaithi) {
            if (x.ma_loai_kt.equals(bai_thi) && x.isDisabled.equals("0")) {
                tempBaiThi = x;
                break;
            }
        }


        String msgFailed = "{\"status\": \"failed\"}";
        if (tempBaiThi == null) {
            return msgFailed;
        }

        // System.out.println("tempBaiThi");
        // System.out.println(tempBaiThi.toString());

        try {
            Connection connection = new MySQLConnectionManager().getConnection("localhost:3306", "thitracnghiem",
                    "root", "123456");

            if (tempBaiThi.status.equals("upcoming")) {
                // for bai_thi
                String query = "call checkConvertStatusBaithi(?);";
                CallableStatement callableStatement = connection.prepareCall(query);
                callableStatement.setString(1, bai_thi);

                ResultSet resultSet = callableStatement.executeQuery();
                String count01 = "0";
                while (resultSet.next()) {
                    count01 = resultSet.getString("count01");
                }
                if (count01.equals("0")) {
                    return msgFailed;
                } else {
                    server.refreshData_ForBaithi();
                }
            }

            if (tempBaiThi != null) {
                Sinhvien_Baithi sv_bt = null;
                for (Sinhvien_Baithi x : TcpServer.lstSinhvien_Baithi) {
                    if (x.mssv.equals(mssv) && x.bai_thi.equals(bai_thi)) {
                        sv_bt = x;
                        break;
                    }
                }
                if (sv_bt == null) {
                    return msgFailed;
                }

                if (tempBaiThi.status.equals("running") && sv_bt.isSubmited.equals("1")) {
                    String query1 = "call setStatusBaithi_finished_nobody(?);";
                    CallableStatement callableStatement1 = connection.prepareCall(query1);
                    callableStatement1.setString(1, bai_thi);
                    ResultSet resultSet1 = callableStatement1.executeQuery();
                    String count01 = "0";
                    while (resultSet1.next()) {
                        count01 = resultSet1.getString("count01");
                    }
                    if (count01.equals("1")) {
                        server.refreshData_ForBaithi();
                    } else {
                        return msgFailed;
                    }
                }

                List<String> lstIdQuestion = new LinkedList<String>();
                for (ListCauHoi x : TcpServer.lstListCauHoi) {
                    if (x.id_list_cau_hoi.equals(sv_bt.list_cau_hoi)) {
                        lstIdQuestion.add(x.id_question);
                    }
                }

                List<Question> lstQuestionNew = new LinkedList<Question>();
                for (Question x : TcpServer.lstQuestion) {
                    boolean ok = false;
                    for (String y : lstIdQuestion) {
                        if (x.id.equals(y)) {
                            ok = true;
                        }
                    }
                    if (ok) {
                        lstQuestionNew.add(x);
                    }
                }

                Random r = new Random();
                List<ListCauHoiDetail> listData = new LinkedList<ListCauHoiDetail>();

                try {
                    for (int i = 0; i < lstQuestionNew.size(); i++) {
                        Question temp = lstQuestionNew.get(i);
                        String id = temp.id;
                        String content = temp.content;

                        // dang sua o day
                        if (temp.dap_an_a == null) {
                            temp.dap_an_a = "null";
                        }
                        if (temp.dap_an_b == null) {
                            temp.dap_an_b = "null";
                        }
                        if (temp.dap_an_c == null) {
                            temp.dap_an_c = "null";
                        }
                        if (temp.dap_an_d == null) {
                            temp.dap_an_d = "null";
                        }
                        if (temp.dap_an_e == null) {
                            temp.dap_an_e = "null";
                        }
                        if (temp.dap_an_f == null) {
                            temp.dap_an_f = "null";
                        }

                        String dap_an_a = temp.dap_an_a;
                        String dap_an_b = temp.dap_an_b;
                        String dap_an_c = temp.dap_an_c;
                        String dap_an_d = temp.dap_an_d;
                        String dap_an_e = temp.dap_an_e;
                        String dap_an_f = temp.dap_an_f;

                        int position = r.nextInt(10) + 3;
                        int direction = r.nextInt() % 2 == 0 ? -1 : 1;
                        String res = "";
                        res += getAlphaNumericString(position - 1);
                        res += temp.dap_an_dung;
                        res += getAlphaNumericString(r.nextInt(15) + 1);

                        if (direction == 1) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(res);
                            sb = sb.reverse();
                            res = sb.toString();
                        }
                        String dap_an_dung = res;
                        String rate = temp.rate;
                        String thuoc_chuong = temp.thuoc_chuong;

                        listData.add(new ListCauHoiDetail(id, content, dap_an_a, dap_an_b, dap_an_c, dap_an_d, dap_an_e,
                                dap_an_f, direction + "", "" + position, dap_an_dung, rate, thuoc_chuong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (sv_bt.dap_an_a == null) {
                    sv_bt.dap_an_a = "null";
                }
                if (sv_bt.dap_an_b == null) {
                    sv_bt.dap_an_b = "null";
                }
                if (sv_bt.dap_an_c == null) {
                    sv_bt.dap_an_c = "null";
                }
                if (sv_bt.dap_an_d == null) {
                    sv_bt.dap_an_d = "null";
                }
                if (sv_bt.dap_an_e == null) {
                    sv_bt.dap_an_e = "null";
                }
                if (sv_bt.dap_an_f == null) {
                    sv_bt.dap_an_f = "null";
                }
                if (sv_bt.diem == null || sv_bt.diem.equals("null")) {
                    sv_bt.diem = "0.0";
                }

                String ho_ten = null;
                for (user_infor yy : TcpServer.lstUserInfor) {
                    if (yy.mssv.equals(sv_bt.mssv)) {
                        ho_ten = yy.ho_ten;
                        break;
                    }
                }
                if (ho_ten == null) {
                    ho_ten = "null";
                }

                String status = null;
                if (tempBaiThi != null && sv_bt != null) {
                    status = tempBaiThi.status;
                    if (tempBaiThi.status.equals("running") && sv_bt.isSubmited.equals("1")) {
                        status = "finished";
                    }
                }

                Dethi_Model dtTemp = new Dethi_Model(sv_bt.mssv, ho_ten, sv_bt.bai_thi, sv_bt.list_cau_hoi,
                        sv_bt.dap_an_a, sv_bt.dap_an_b, sv_bt.dap_an_c, sv_bt.dap_an_d, sv_bt.dap_an_e, sv_bt.dap_an_f,
                        sv_bt.diem, status, listData);
                // update finished (setStatusBaithi_finished_nobody)

                String query1 = "call setStatusBaithi_finished_nobody(?);";
                CallableStatement callableStatement1 = connection.prepareCall(query1);
                callableStatement1.setString(1, bai_thi);
                ResultSet resultSet1 = callableStatement1.executeQuery();
                String count01 = "0";
                while (resultSet1.next()) {
                    count01 = resultSet1.getString("count01");
                }
                if (count01.equals("1")) {
                    server.refreshData_ForBaithi();
                }
                return gson.toJson(dtTemp);
            }
        } catch (Exception e) {
        }
        return msgFailed;
    }

    private String getJsonFromSet(Set<String> hash_Set) {
        int size = hash_Set.size();
        String res = "{";
        res += "\"size\":\"" + size + "\"" + ",";
        res += "\"ListData\":[";
        int i = 0;
        for (String x : hash_Set) {
            res += "\"";
            res += x;
            res += "\"";
            if (i != size - 1) {
                res += ",";
            }
            i++;
        }
        res += "]";
        res += "}";
        return res;
    }

    private String getCodeListCauHoi(String ma_loai_kt, int i) {
        // ListCauHoi.18CT2_r9PsTH.D01
        String res = "ListCauHoi." + ma_loai_kt + ".D";
        if (i < 10) {
            res += "0" + i;
        } else {
            res += i;
        }

        return res;
    }

    private String getAuthen(String userName, String pass) {
        String status = null, role = null, maso = null, name = null;
        String lop = null;

        for (GiangVien x : TcpServer.lstGiangVien) {
            if (userName.equals(x.user) && pass.equals(x.pass)) {
                status = "success";
                role = "giangvien";
                maso = x.ma_gv;
                name = x.name;
                lop = "null";
                return gson.toJson(new AuthenModel(status, role, maso, name, lop));
            }
        }
        for (user_infor x : TcpServer.lstUserInfor) {
            if (userName.equals(x.mssv) && pass.equals(x._default)) {
                status = "success";
                role = "sinhvien";
                maso = x.mssv;
                name = x.ho_ten;
                lop = x.lop;
                return gson.toJson(new AuthenModel(status, role, maso, name, lop));
            }
        }
        return "{\"status\": \"Not found\"}";
    }
}

class Gen_de {
    public String idgen_de;
    public String lop;
    public String bai_thi;
    public String list_cau_hoi;

    public Gen_de(String idgen_de, String lop, String bai_thi, String list_cau_hoi) {
        super();
        this.idgen_de = idgen_de;
        this.lop = lop;
        this.bai_thi = bai_thi;
        this.list_cau_hoi = list_cau_hoi;
    }

    @Override
    public String toString() {
        return "Gen_de [idgen_de=" + idgen_de + ", lop=" + lop + ", bai_thi=" + bai_thi + ", list_cau_hoi="
                + list_cau_hoi + "]";
    }
}

class BaiThi {
    public String id;
    public String ma_loai_kt;
    public String thoi_gian_bat_dau;
    public String bao_lau;
    public String thoi_gian_ket_thuc;
    public String lop;
    public String status;
    public String ten_bai_thi;
    public String isDisabled;

    public BaiThi(String id, String ma_loai_kt, String thoi_gian_bat_dau, String bao_lau, String thoi_gian_ket_thuc,
                  String lop, String status, String ten_bai_thi, String isDisabled) {
        super();
        this.id = id;
        this.ma_loai_kt = ma_loai_kt;
        this.thoi_gian_bat_dau = thoi_gian_bat_dau;
        this.bao_lau = bao_lau;
        this.thoi_gian_ket_thuc = thoi_gian_ket_thuc;
        this.lop = lop;
        this.status = status;
        this.ten_bai_thi = ten_bai_thi;
        this.isDisabled = isDisabled;
    }

    @Override
    public String toString() {
        return "BaiThi [id=" + id + ", ma_loai_kt=" + ma_loai_kt + ", thoi_gian_bat_dau=" + thoi_gian_bat_dau
                + ", bao_lau=" + bao_lau + ", thoi_gian_ket_thuc=" + thoi_gian_ket_thuc + ", lop=" + lop + ", status="
                + status + ", ten_bai_thi=" + ten_bai_thi + ", isDisabled=" + isDisabled + "]";
    }

}

class Sinhvien_Baithi {
    public String id;
    public String mssv;
    public String bai_thi;
    public String list_cau_hoi;
    public String dap_an_a;
    public String dap_an_b;
    public String dap_an_c;
    public String dap_an_d;
    public String dap_an_e;
    public String dap_an_f;
    public String diem;
    public String isSubmited;

    public Sinhvien_Baithi(String id, String mssv, String bai_thi, String list_cau_hoi, String dap_an_a,
                           String dap_an_b, String dap_an_c, String dap_an_d, String dap_an_e, String dap_an_f, String diem,
                           String isSubmited) {
        super();
        this.id = id;
        this.mssv = mssv;
        this.bai_thi = bai_thi;
        this.list_cau_hoi = list_cau_hoi;
        this.dap_an_a = dap_an_a;
        this.dap_an_b = dap_an_b;
        this.dap_an_c = dap_an_c;
        this.dap_an_d = dap_an_d;
        this.dap_an_e = dap_an_e;
        this.dap_an_f = dap_an_f;
        this.diem = diem;
        this.isSubmited = isSubmited;
    }

    @Override
    public String toString() {
        return "Sinhvien_Baithi [id=" + id + ", mssv=" + mssv + ", bai_thi=" + bai_thi + ", list_cau_hoi="
                + list_cau_hoi + ", dap_an_a=" + dap_an_a + ", dap_an_b=" + dap_an_b + ", dap_an_c=" + dap_an_c
                + ", dap_an_d=" + dap_an_d + ", dap_an_e=" + dap_an_e + ", dap_an_f=" + dap_an_f + ", diem=" + diem
                + ", isSubmited=" + isSubmited + "]";
    }

}

class ListCauHoi {
    public String id;
    public String id_list_cau_hoi;
    public String id_question;

    public ListCauHoi(String id, String id_list_cau_hoi, String id_question) {
        super();
        this.id = id;
        this.id_list_cau_hoi = id_list_cau_hoi;
        this.id_question = id_question;
    }

    @Override
    public String toString() {
        return "ListCauHoi [id=" + id + ", id_list_cau_hoi=" + id_list_cau_hoi + ", id_question=" + id_question + "]";
    }

}

class Favourite {
    public String id;
    public String mssv;
    public String idQuestion;

    public Favourite(String id, String mssv, String idQuestion) {
        super();
        this.id = id;
        this.mssv = mssv;
        this.idQuestion = idQuestion;
    }

    @Override
    public String toString() {
        return "Favourite [id=" + id + ", mssv=" + mssv + ", idQuestion=" + idQuestion + "]";
    }
}

class GiangVien {
    public String ma_gv;
    public String name;
    public String user;
    public String pass;
    public String comment;

    public GiangVien(String ma_gv, String name, String user, String pass, String comment) {
        super();
        this.ma_gv = ma_gv;
        this.name = name;
        this.user = user;
        this.pass = pass;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "GiangVien [ma_gv=" + ma_gv + ", name=" + name + ", user=" + user + ", pass=" + pass + ", comment="
                + comment + "]";
    }

}

class Question {
    public String id;
    public String content;
    public String dap_an_a;
    public String dap_an_b;
    public String dap_an_c;
    public String dap_an_d;
    public String dap_an_e;
    public String dap_an_f;
    public String rate;
    public String dap_an_dung;
    public String thuoc_chuong;

    public Question(String content, String dap_an_a, String dap_an_b, String dap_an_c, String dap_an_d, String dap_an_e,
                    String dap_an_f, String rate, String dap_an_dung, String thuoc_chuong) {
        super();
        this.content = content;
        this.dap_an_a = dap_an_a;
        this.dap_an_b = dap_an_b;
        this.dap_an_c = dap_an_c;
        this.dap_an_d = dap_an_d;
        this.dap_an_e = dap_an_e;
        this.dap_an_f = dap_an_f;
        this.rate = rate;
        this.dap_an_dung = dap_an_dung;
        this.thuoc_chuong = thuoc_chuong;
    }

    public Question(String id, String content, String dap_an_a, String dap_an_b, String dap_an_c, String dap_an_d,
                    String dap_an_e, String dap_an_f, String rate, String dap_an_dung, String thuoc_chuong) {
        super();
        this.id = id;
        this.content = content;
        this.dap_an_a = dap_an_a;
        this.dap_an_b = dap_an_b;
        this.dap_an_c = dap_an_c;
        this.dap_an_d = dap_an_d;
        this.dap_an_e = dap_an_e;
        this.dap_an_f = dap_an_f;
        this.rate = rate;
        this.dap_an_dung = dap_an_dung;
        this.thuoc_chuong = thuoc_chuong;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", content=" + content + ", dap_an_a=" + dap_an_a + ", dap_an_b=" + dap_an_b
                + ", dap_an_c=" + dap_an_c + ", dap_an_d=" + dap_an_d + ", dap_an_e=" + dap_an_e + ", dap_an_f="
                + dap_an_f + ", rate=" + rate + ", dap_an_dung=" + dap_an_dung + ", thuoc_chuong=" + thuoc_chuong + "]";
    }

}

class user_infor {
    public String mssv;
    public String ho_ten;
    public String sdt;
    public String lop;
    public String ngaysinh;
    public String email;
    public String _default;

    public user_infor(String mssv, String ho_ten, String sdt, String lop, String ngaysinh) {
        super();
        this.mssv = mssv;
        this.ho_ten = ho_ten;
        this.sdt = sdt;
        this.lop = lop;
        this.ngaysinh = ngaysinh;
    }

    public user_infor(String mssv, String ho_ten, String sdt, String lop, String ngaysinh, String email,
                      String _default) {
        super();
        this.mssv = mssv;
        this.ho_ten = ho_ten;
        this.sdt = sdt;
        this.lop = lop;
        this.ngaysinh = ngaysinh;
        this.email = email;
        this._default = _default;
    }

    @Override
    public String toString() {
        return "user_infor [mssv=" + mssv + ", ho_ten=" + ho_ten + ", sdt=" + sdt + ", lop=" + lop + ", ngaysinh="
                + ngaysinh + ", email=" + email + ", _default=" + _default + "]";
    }
}

/*
 * http://localhost:8080/apiThitracnghiem/api01/General?doing=getInfo&
 * actionInfo=getDethi&mssv=1651220013&bai_thi=16CT_3cHVte thêm cột điểm
 *
 * truyền lớp vs mã bài thi --> lấy sạch hết cả lớp của bài thi đó truyền mssv
 * --> thông tin điểm của tất cả các bài thi của sinh viên đó.
 */

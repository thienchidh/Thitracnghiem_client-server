import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TcpClient {

    public static void main(String[] args) {
        try {
            int port = 41235;
            String ip = "localhost";
            Socket soc = new Socket(ip, port);
            DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
            DataInputStream dis = new DataInputStream(soc.getInputStream());
            soc.setSoTimeout(10000);

            //String action = "general";
            //String action = "giangvien";
            String action = "sinhvien";

            if (args.length >= 1) {
                action = args[0];
            }

            if (action.equals("general")) {
                //String doing = "getListQuestion";
                //String doing = "getAuthen";
                String doing = "getInfo";

                if (args.length >= 2) {
                    doing = args[1];
                }
                dos.writeUTF(action);
                dos.writeUTF(doing);

                if (doing.equals("getListQuestion")) {
                    //cơ sở theo dõi tình hình địch ở Điện Biên Phủ
                    String key = "%C4%90i%E1%BB%87n%20Bi%C3%AAn%20Ph%E1%BB%A7";
                    //String key = "";
                    String startId = "0";
                    // update mssv
                    String mssv = "1851220085";

                    if (args.length >= 5) {
                        key = args[2];
                        startId = args[3];
                        mssv = args[4];
                    }
                    dos.writeUTF(key);
                    dos.writeUTF(startId);
                    dos.writeUTF(mssv);
                } else if (doing.equals("getAuthen")) {
                    String username = "1851220004";
                    String pass = "14*11*2000";
                    if (args.length >= 4) {
                        username = args[2];
                        pass = args[3];
                    }
                    dos.writeUTF(username);
                    dos.writeUTF(pass);
                } else if (doing.equals("getInfo")) { // hien tai dang forGiangVien
                    //String actionInfo = "getDSLop";
                    //String actionInfo = "getListInfoOfLop";
                    //String actionInfo = "getFavourite";
                    String actionInfo = "getDethi";
                    //String actionInfo = "getDiem_Baithi";
                    //String actionInfo = "getInfoDethiOfLopBaithi";
                    //String actionInfo = "getInfoDethiOfMSSVBaithi";

                    if (args.length >= 3) {
                        actionInfo = args[2];
                    }
                    dos.writeUTF(actionInfo);
                    if (actionInfo.equals("getListInfoOfLop")) {
                        String lop = "16CT";
                        String mssv = "1651220021";

                        if (args.length >= 5) {
                            lop = args[3];
                            mssv = args[4];
                        }
                        dos.writeUTF(lop);
                        dos.writeUTF(mssv);
                    } else if (actionInfo.equals("getFavourite")) {
                        String mssv = "1851220085";
                        String startId = "0";

                        if (args.length >= 5) {
                            mssv = args[3];
                            startId = args[4];
                        }
                        dos.writeUTF(mssv);
                        dos.writeUTF(startId);
                    } else if (actionInfo.equals("getDethi")) { // building
                        String mssv = "1651220021";
                        String bai_thi = "16CT_tzVHrc";

                        if (args.length >= 5) {
                            mssv = args[3];
                            bai_thi = args[4];
                        }
                        dos.writeUTF(mssv);
                        dos.writeUTF(bai_thi);
                    } else if (actionInfo.equals("getInfoDethiOfLopBaithi")) {
                        String lop = "16CT";
                        String bai_thi = "16CT_dfgZrv";
                        String startId = "0";

                        if (args.length >= 6) {
                            lop = args[3];
                            bai_thi = args[4];
                            startId = args[5];
                        }
                        dos.writeUTF(lop);
                        dos.writeUTF(bai_thi);
                        dos.writeUTF(startId);
                    } else if (actionInfo.equals("getInfoDethiOfMSSVBaithi")) { // building_
                        String mssv = "1651220013";
                        //String mssv = "1851220069";
                        String startId = "0";

                        if (args.length >= 5) {
                            mssv = args[3];
                            startId = args[4];
                        }
                        dos.writeUTF(mssv);
                        dos.writeUTF(startId);
                    } else if (actionInfo.equals("getDiem_Baithi")) {
                        String mssv = "1851220140";
                        String bai_thi = "18CT2_oXUdgL";

                        if (args.length >= 5) {
                            mssv = args[3];
                            bai_thi = args[4];
                        }
                        dos.writeUTF(mssv);
                        dos.writeUTF(bai_thi);
                    }
                }
            } else if (action.equals("sinhvien")) {
                String doing = "postDapAn_Bailam";
                //String doing = "postFavourite";

                if (args.length >= 2) {
                    doing = args[1];
                }

                dos.writeUTF(action);
                dos.writeUTF(doing);

                if (doing.equals("postDapAn_Bailam")) {
                    String username = "1651220021";
                    String pass = "22*12*1998";
                    String bai_thi = "16CT_oOrKE0";
                    String dap_an_a = "22";
                    String dap_an_b = "1";
                    String dap_an_c = "3";
                    String dap_an_d = "null";
                    String dap_an_e = "null";
                    String dap_an_f = "null";
                    String isSubmited = "1";

                    if (args.length >= 12) {
                        username = args[2];
                        pass = args[3];
                        bai_thi = args[4];
                        dap_an_a = args[5];
                        dap_an_b = args[6];
                        dap_an_c = args[7];
                        dap_an_d = args[8];
                        dap_an_e = args[9];
                        dap_an_f = args[10];
                        isSubmited = args[11];
                    }

                    dos.writeUTF(username);
                    dos.writeUTF(pass);
                    dos.writeUTF(bai_thi);
                    dos.writeUTF(dap_an_a);
                    dos.writeUTF(dap_an_b);
                    dos.writeUTF(dap_an_c);
                    dos.writeUTF(dap_an_d);
                    dos.writeUTF(dap_an_e);
                    dos.writeUTF(dap_an_f);
                    dos.writeUTF(isSubmited);
                } else if (doing.equals("postFavourite")) {
                    String username = "1851210010";
                    String pass = "28*03*2000";
                    String idQuestion = "126";
                    String isFavourite = "0";

                    if (args.length >= 6) {
                        username = args[2];
                        pass = args[3];
                        idQuestion = args[4];
                        isFavourite = args[5];
                    }

                    dos.writeUTF(username);
                    dos.writeUTF(pass);
                    dos.writeUTF(idQuestion);
                    dos.writeUTF(isFavourite);
                }
            } else if (action.equals("giangvien")) {
                String doing = "postLichThi_Lop_BaiThi";
                if (args.length >= 2) {
                    doing = args[1];
                }

                dos.writeUTF(action);
                dos.writeUTF(doing);

                if (doing.equals("postLichThi_Lop_BaiThi")) {
                    String thoi_gian_bat_dau = "2019-07-30+11%3A00%3A00";
                    String bao_lau = "90";
                    String lop = "18CT2"; // 18DD2
                    String ten_bai_thi = "GK_Lan1";
                    String username = "haodo";
                    String pass = "123";

                    if (args.length >= 8) {
                        thoi_gian_bat_dau = args[2];
                        bao_lau = args[3];
                        lop = args[4];
                        ten_bai_thi = args[5];
                        username = args[6];
                        pass = args[7];
                    }

                    dos.writeUTF((thoi_gian_bat_dau));
                    dos.writeUTF((bao_lau));
                    dos.writeUTF((lop));
                    dos.writeUTF((ten_bai_thi));
                    dos.writeUTF((username));
                    dos.writeUTF((pass));

                }
            }
            System.out.println(dis.readUTF());
            soc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

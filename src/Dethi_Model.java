import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dethi_Model {

    @SerializedName("mssv")
    @Expose
    private String mssv;

    @SerializedName("ho_ten")
    @Expose
    private String ho_ten;

    @SerializedName("bai_thi")
    @Expose
    private String baiThi;
    @SerializedName("list_cau_hoi")
    @Expose
    private String listCauHoi;
    @SerializedName("dap_an_a")
    @Expose
    private String dapAnA;
    @SerializedName("dap_an_b")
    @Expose
    private String dapAnB;
    @SerializedName("dap_an_c")
    @Expose
    private String dapAnC;
    @SerializedName("dap_an_d")
    @Expose
    private String dapAnD;
    @SerializedName("dap_an_e")
    @Expose
    private String dapAnE;
    @SerializedName("dap_an_f")
    @Expose
    private String dapAnF;
    @SerializedName("diem")
    @Expose
    private String diem;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("ListCauHoiDetails")
    @Expose
    private List<ListCauHoiDetail> listCauHoiDetails = null;

    public Dethi_Model(String mssv, String ho_ten, String baiThi, String listCauHoi, String dapAnA, String dapAnB,
                       String dapAnC, String dapAnD, String dapAnE, String dapAnF, String diem, String status,
                       List<ListCauHoiDetail> listCauHoiDetails) {
        super();
        this.mssv = mssv;
        this.ho_ten = ho_ten;
        this.baiThi = baiThi;
        this.listCauHoi = listCauHoi;
        this.dapAnA = dapAnA;
        this.dapAnB = dapAnB;
        this.dapAnC = dapAnC;
        this.dapAnD = dapAnD;
        this.dapAnE = dapAnE;
        this.dapAnF = dapAnF;
        this.diem = diem;
        this.status = status;
        this.listCauHoiDetails = listCauHoiDetails;
    }

    @Override
    public String toString() {
        return "Dethi_Model [mssv=" + mssv + ", ho_ten=" + ho_ten + ", baiThi=" + baiThi + ", listCauHoi=" + listCauHoi
                + ", dapAnA=" + dapAnA + ", dapAnB=" + dapAnB + ", dapAnC=" + dapAnC + ", dapAnD=" + dapAnD
                + ", dapAnE=" + dapAnE + ", dapAnF=" + dapAnF + ", diem=" + diem + ", status=" + status
                + ", listCauHoiDetails=" + listCauHoiDetails + "]";
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getHo_ten() {
        return ho_ten;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public String getBaiThi() {
        return baiThi;
    }

    public void setBaiThi(String baiThi) {
        this.baiThi = baiThi;
    }

    public String getListCauHoi() {
        return listCauHoi;
    }

    public void setListCauHoi(String listCauHoi) {
        this.listCauHoi = listCauHoi;
    }

    public String getDapAnA() {
        return dapAnA;
    }

    public void setDapAnA(String dapAnA) {
        this.dapAnA = dapAnA;
    }

    public String getDapAnB() {
        return dapAnB;
    }

    public void setDapAnB(String dapAnB) {
        this.dapAnB = dapAnB;
    }

    public String getDapAnC() {
        return dapAnC;
    }

    public void setDapAnC(String dapAnC) {
        this.dapAnC = dapAnC;
    }

    public String getDapAnD() {
        return dapAnD;
    }

    public void setDapAnD(String dapAnD) {
        this.dapAnD = dapAnD;
    }

    public String getDapAnE() {
        return dapAnE;
    }

    public void setDapAnE(String dapAnE) {
        this.dapAnE = dapAnE;
    }

    public String getDapAnF() {
        return dapAnF;
    }

    public void setDapAnF(String dapAnF) {
        this.dapAnF = dapAnF;
    }

    public String getDiem() {
        return diem;
    }

    public void setDiem(String diem) {
        this.diem = diem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ListCauHoiDetail> getListCauHoiDetails() {
        return listCauHoiDetails;
    }

    public void setListCauHoiDetails(List<ListCauHoiDetail> listCauHoiDetails) {
        this.listCauHoiDetails = listCauHoiDetails;
    }


}

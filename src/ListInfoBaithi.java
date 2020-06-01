import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListInfoBaithi {

    @SerializedName("ma_loai_kt")
    @Expose
    private String maLoaiKt;
    @SerializedName("thoi_gian_bat_dau")
    @Expose
    private String thoiGianBatDau;
    @SerializedName("bao_lau")
    @Expose
    private String baoLau;
    @SerializedName("thoi_gian_ket_thuc")
    @Expose
    private String thoiGianKetThuc;
    @SerializedName("lop")
    @Expose
    private String lop;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ten_bai_thi")
    @Expose
    private String ten_bai_thi;
    @SerializedName("timeserver")
    @Expose
    private String timeserver;

    public ListInfoBaithi(String maLoaiKt, String thoiGianBatDau, String baoLau, String thoiGianKetThuc, String lop,
                          String status, String ten_bai_thi, String timeserver) {
        super();
        this.maLoaiKt = maLoaiKt;
        this.thoiGianBatDau = thoiGianBatDau;
        this.baoLau = baoLau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.lop = lop;
        this.status = status;
        this.ten_bai_thi = ten_bai_thi;
        this.timeserver = timeserver;
    }

    @Override
    public String toString() {
        return "ListInfoBaithi [maLoaiKt=" + maLoaiKt + ", thoiGianBatDau=" + thoiGianBatDau + ", baoLau=" + baoLau
                + ", thoiGianKetThuc=" + thoiGianKetThuc + ", lop=" + lop + ", status=" + status + ", ten_bai_thi="
                + ten_bai_thi + ", timeserver=" + timeserver + "]";
    }

    public String getMaLoaiKt() {
        return maLoaiKt;
    }

    public void setMaLoaiKt(String maLoaiKt) {
        this.maLoaiKt = maLoaiKt;
    }

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public String getBaoLau() {
        return baoLau;
    }

    public void setBaoLau(String baoLau) {
        this.baoLau = baoLau;
    }

    public String getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTen_bai_thi() {
        return ten_bai_thi;
    }

    public void setTen_bai_thi(String ten_bai_thi) {
        this.ten_bai_thi = ten_bai_thi;
    }

    public String getTimeserver() {
        return timeserver;
    }

    public void setTimeserver(String timeserver) {
        this.timeserver = timeserver;
    }


}

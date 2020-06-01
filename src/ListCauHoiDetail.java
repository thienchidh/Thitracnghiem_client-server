import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListCauHoiDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("content")
    @Expose
    private String content;
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
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("dap_an_dung")
    @Expose
    private String dapAnDung;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("thuoc_chuong")
    @Expose
    private String thuocChuong;

    public ListCauHoiDetail(String id, String content, String dapAnA, String dapAnB, String dapAnC, String dapAnD,
                            String dapAnE, String dapAnF, String direction, String position, String dapAnDung, String rate,
                            String thuocChuong) {
        super();
        this.id = id;
        this.content = content;
        this.dapAnA = dapAnA;
        this.dapAnB = dapAnB;
        this.dapAnC = dapAnC;
        this.dapAnD = dapAnD;
        this.dapAnE = dapAnE;
        this.dapAnF = dapAnF;
        this.direction = direction;
        this.position = position;
        this.dapAnDung = dapAnDung;
        this.rate = rate;
        this.thuocChuong = thuocChuong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDapAnDung() {
        return dapAnDung;
    }

    public void setDapAnDung(String dapAnDung) {
        this.dapAnDung = dapAnDung;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getThuocChuong() {
        return thuocChuong;
    }

    public void setThuocChuong(String thuocChuong) {
        this.thuocChuong = thuocChuong;
    }

    @Override
    public String toString() {
        return "ListCauHoiDetail [id=" + id + ", content=" + content + ", dapAnA=" + dapAnA + ", dapAnB=" + dapAnB
                + ", dapAnC=" + dapAnC + ", dapAnD=" + dapAnD + ", dapAnE=" + dapAnE + ", dapAnF=" + dapAnF
                + ", direction=" + direction + ", position=" + position + ", dapAnDung=" + dapAnDung + ", rate=" + rate
                + ", thuocChuong=" + thuocChuong + "]";
    }

}

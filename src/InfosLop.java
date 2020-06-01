import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfosLop {

    @SerializedName("lop")
    @Expose
    private String lop;
    @SerializedName("ListInfoSinhvien")
    @Expose
    private List<ListInfoSinhvien> listInfoSinhvien = null;
    @SerializedName("ListInfoBaithi")
    @Expose
    private List<ListInfoBaithi> listInfoBaithi = null;

    public InfosLop(String lop, List<ListInfoSinhvien> listInfoSinhvien, List<ListInfoBaithi> listInfoBaithi) {
        super();
        this.lop = lop;
        this.listInfoSinhvien = listInfoSinhvien;
        this.listInfoBaithi = listInfoBaithi;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public List<ListInfoSinhvien> getListInfoSinhvien() {
        return listInfoSinhvien;
    }

    public void setListInfoSinhvien(List<ListInfoSinhvien> listInfoSinhvien) {
        this.listInfoSinhvien = listInfoSinhvien;
    }

    public List<ListInfoBaithi> getListInfoBaithi() {
        return listInfoBaithi;
    }

    public void setListInfoBaithi(List<ListInfoBaithi> listInfoBaithi) {
        this.listInfoBaithi = listInfoBaithi;
    }

    @Override
    public String toString() {
        return "InfosLop [lop=" + lop + ", listInfoSinhvien=" + listInfoSinhvien + ", listInfoBaithi=" + listInfoBaithi
                + "]";
    }


}

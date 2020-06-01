import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("maso")
    @Expose
    private String maso;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lop")
    @Expose
    private String lop;

    public AuthenModel(String status, String role, String maso, String name, String lop) {
        super();
        this.status = status;
        this.role = role;
        this.maso = maso;
        this.name = name;
        this.lop = lop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }


}

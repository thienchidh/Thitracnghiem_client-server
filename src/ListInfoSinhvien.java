import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListInfoSinhvien {

    @SerializedName("maso")
    @Expose
    private String maso;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;

    public ListInfoSinhvien(String maso, String name, String email) {
        super();
        this.maso = maso;
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ListInfoSinhvien [maso=" + maso + ", name=" + name + ", email=" + email + "]";
    }


}

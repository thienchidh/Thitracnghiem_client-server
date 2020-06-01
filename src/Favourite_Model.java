import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favourite_Model {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mssv")
    @Expose
    private String mssv;
    @SerializedName("idQuestion")
    @Expose
    private String idQuestion;
    @SerializedName("isFavourite")
    @Expose
    private String isFavourite;

    public Favourite_Model(String status, String mssv, String idQuestion, String isFavourite) {
        super();
        this.status = status;
        this.mssv = mssv;
        this.idQuestion = idQuestion;
        this.isFavourite = isFavourite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return "Favourite_Model [status=" + status + ", mssv=" + mssv + ", idQuestion=" + idQuestion + ", isFavourite="
                + isFavourite + "]";
    }

}

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoDethiOfLop {
    @SerializedName("nextId")
    @Expose
    private String nextId;


    @SerializedName("ListData")
    @Expose
    private List<Dethi_Model> dethiModel = null;


    public InfoDethiOfLop(String nextId, List<Dethi_Model> dethiModel) {
        super();
        this.nextId = nextId;
        this.dethiModel = dethiModel;
    }


    @Override
    public String toString() {
        return "InfoDethiOfLop [nextId=" + nextId + ", dethiModel=" + dethiModel + "]";
    }


    public String getNextId() {
        return nextId;
    }


    public void setNextId(String nextId) {
        this.nextId = nextId;
    }


    public List<Dethi_Model> getDethiModel() {
        return dethiModel;
    }


    public void setDethiModel(List<Dethi_Model> dethiModel) {
        this.dethiModel = dethiModel;
    }

}

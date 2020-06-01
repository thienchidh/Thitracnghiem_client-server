import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionModel {

    @SerializedName("nextId")
    @Expose
    private String nextId;
    @SerializedName("ListData")
    @Expose
    private List<ListQuestionDatum> listData = null;

    public QuestionModel(String nextId, List<ListQuestionDatum> listData) {
        super();
        this.nextId = nextId;
        this.listData = listData;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public List<ListQuestionDatum> getListData() {
        return listData;
    }

    public void setListData(List<ListQuestionDatum> listData) {
        this.listData = listData;
    }

}

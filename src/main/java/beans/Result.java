package beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/31 0031.
 */
public class Result {
    @SerializedName("result")
    private String result;

    public Result(String result) {
        this.result = result;
    }
}

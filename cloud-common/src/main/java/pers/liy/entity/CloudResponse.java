package pers.liy.entity;

import lombok.Data;

import java.util.HashMap;

/**
 * @Author Prock.Liy
 * @Date 2020/9/14 22:50
 * @Description
 **/
@Data
public class CloudResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public CloudResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public CloudResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public CloudResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }
}

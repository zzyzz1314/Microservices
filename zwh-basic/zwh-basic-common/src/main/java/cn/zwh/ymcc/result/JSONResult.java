package cn.zwh.ymcc.result;

import cn.zwh.ymcc.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JSONResult {
    private boolean success=true;
    //响应信息 描述字符串
    private String message="成功";
    //响应码，1 代表成功; 0 代表失败
    private String code="20000";
    //返回的数据
    private Object data;

    //增删改 成功响应
    public static JSONResult success() {
        return new JSONResult();
    }

    //查询 成功响应
    public static JSONResult success(Object obj) {
        JSONResult instance = new JSONResult();
        instance.setData(obj);
        return instance;
    }

    public static JSONResult success(Object obj,String code) {
        JSONResult instance = new JSONResult();
        instance.setCode(code);
        instance.setData(obj);
        return instance;
    }

    public static JSONResult error(String message, String code) {
        JSONResult instance = new JSONResult();
        instance.setMessage(message);
        instance.setSuccess(false);
        instance.setCode(code);
        return instance;
    }

    public static JSONResult error(ErrorCode errorCode) {
        JSONResult instance = new JSONResult();
        instance.setMessage(errorCode.getMessage());
        instance.setSuccess(false);
        instance.setCode(errorCode.getCode());
        return instance;
    }

    public static JSONResult error() {
        JSONResult jsonResult = new JSONResult();
        jsonResult.setSuccess(false);
        return jsonResult;
    }

    //失败响应
    public static JSONResult error(String message) {
        return error(message,null);
    }
}
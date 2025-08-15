package cn.zwh.ymcc.constants;

public enum ErrorCode {

    //用户不存在，密码错误，用户名已存在，用户名或密码为空，用户名或密码错误
    //网络错误
    NETWORK_ERROR("网络错误", "1000"),
    USER_NOT_EXIST("用户不存在", "1001"),
    USER_PASSWORD_ERROR("用户名或密码错误", "1002"),
    USER_USERNAME_EXIST("用户名已存在", "1003"),
    USER_USERNAME_EMPTY("用户名不能为空", "1004"),
    USER_PASSWORD_EMPTY("密码不能为空", "1005"),
    USER_USERNAME_PASSWORD_ERROR("用户名或密码错误", "1006"),
    USER_USERNAME_PASSWORD_EMPTY("用户名或密码不能为空", "1007");

    private String code;
    private String message;

    ErrorCode(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package cn.zwh.ymcc.exception;

public class GlobleBusinessException extends RuntimeException{
    public GlobleBusinessException() {
    }

    public GlobleBusinessException(String message) {
        super(message);
    }
}

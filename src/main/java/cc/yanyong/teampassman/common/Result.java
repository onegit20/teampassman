package cc.yanyong.teampassman.common;

public class Result<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private Result() {}

    public static <T> Result<T> ok() {
        Result<T> r = new Result<>();
        r.setSuccess(true);
        r.setCode(Integer.parseInt(CodeAndMsg.SUCCESS_CODE));
        r.setMessage(CodeAndMsg.SUCCESS_MSG);
        return r;
    }

    public static <T> Result<T> fail() {
        Result<T> r = new Result<>();
        r.setSuccess(false);
        r.setCode(Integer.parseInt(CodeAndMsg.FAIL_CODE));
        r.setMessage(CodeAndMsg.FAIL_MSG);
        return r;
    }

    public Result<T> code(int code) {
        this.code = code;
        return this;
    }

    public Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }
}

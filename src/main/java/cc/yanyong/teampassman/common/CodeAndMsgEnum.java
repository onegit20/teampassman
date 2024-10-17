package cc.yanyong.teampassman.common;

public enum CodeAndMsgEnum {
    SUCCESS(200, "请求成功"),
    FAIL(10000, "请求失败"),
    FAIL10001(10001, "用户名长度3到20位且区分大小写，只允许字母、数字、下划线_、小横线-，必须字母或数字开头"),
    FAIL10002(10002, "用户名已存在"),
    FAIL10003(10003, "密码必须包含大小写字母、数字、特殊字符，且不得少于10位，最大40位"),
    FAIL10004(10004, "邮箱地址不正确(不区分大小写)"),
    FAIL10005(10005, "邮箱地址已存在(不区分大小写)"),
    FAIL10006(10006, "手机号码不正确"),
    FAIL10007(10007, "手机号码已存在"),
    FAIL10008(10008, "用户名或密码错误");

    private final int code;
    private final String message;

    CodeAndMsgEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

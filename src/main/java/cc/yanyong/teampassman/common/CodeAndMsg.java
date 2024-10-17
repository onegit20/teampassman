package cc.yanyong.teampassman.common;

public final class CodeAndMsg {
    public static final String SUCCESS_CODE = "200";
    public static final String SUCCESS_MSG = "请求成功";
    public static final String FAIL_CODE = "10000";
    public static final String FAIL_MSG = "请求失败";
    public static final String FAIL10001_CODE = "10001";
    public static final String FAIL10001_MSG = "用户名长度3到20位且区分大小写，只允许字母、数字、下划线_、小横线-，必须字母或数字开头";
    public static final String FAIL10002_CODE = "10002";
    public static final String FAIL10002_MSG = "用户名已存在";
    public static final String FAIL10003_CODE = "10003";
    public static final String FAIL10003_MSG = "密码必须包含大小写字母、数字、特殊字符，且不得少于10位，最大40位";
    public static final String FAIL10004_CODE = "10004";
    public static final String FAIL10004_MSG = "邮箱地址不正确(不区分大小写)";
    public static final String FAIL10005_CODE = "10005";
    public static final String FAIL10005_MSG = "邮箱地址已存在(不区分大小写)";
    public static final String FAIL10006_CODE = "10006";
    public static final String FAIL10006_MSG = "手机号码不正确";
    public static final String FAIL10007_CODE = "10007";
    public static final String FAIL10007_MSG = "手机号码已存在";
    public static final String FAIL10008_CODE = "10008";
    public static final String FAIL10008_MSG = "用户名或密码错误";

    private CodeAndMsg() {}
}

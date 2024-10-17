package cc.yanyong.teampassman.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    // 用户名长度3到20位且区分大小写，只允许字母、数字、下划线_、小横线-，必须字母或数字开头
    // \w等价[A-Za-z0-9_]
    private static final String VALID_USERNAME_REGEX = "^[a-zA-Z0-9][\\w-]{2,19}$";

    // 密码必须包含大小写字母、数字、特殊字符，且不得少于10位，最大40位
    // \S表示非空白字符
    // \p{Punct}在java中表示Punctuation characters: [][!"#$%&'()*+,./:;<=>?@\^_`{|}~-]
    // https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
    // https://en.wikipedia.org/wiki/Regular_expression
    private static final String VALID_PASSWORD_REGEX = "^(?=\\S*[0-9])"
                                                        + "(?=\\S*[a-z])(?=.*[A-Z])"
                                                        + "(?=\\S*\\p{Punct})"
                                                        + "\\S{10,40}$";

    // 邮箱地址，不区分大小写
    // https://knowledge.validity.com/s/articles/What-are-the-rules-for-email-address-syntax?language=en_US
    // https://www.regular-expressions.info/email.html
    private static final Pattern PATTERN_VALID_EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,63}$", Pattern.CASE_INSENSITIVE);

    // 手机号码
    private static final String VALID_PHONE_REGEX="^1[0-9]{10}$";

    private RegexUtils() {}

    public static boolean validateUsername(String username) {
        return match(VALID_USERNAME_REGEX, username);
    }

    public static boolean validatePassword(String password) {
        return match(VALID_PASSWORD_REGEX, password);
    }

    public static boolean validateEmail(String email) {
        return PATTERN_VALID_EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean validatePhone(String phone) {
        return match(VALID_PHONE_REGEX, phone);
    }

    private static boolean match(String regex, String str) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}

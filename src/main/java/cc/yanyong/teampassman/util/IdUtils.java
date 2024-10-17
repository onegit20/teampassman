package cc.yanyong.teampassman.util;

import java.util.UUID;

public class IdUtils {
    private IdUtils() {}

    public static String uuidNoHyphens() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

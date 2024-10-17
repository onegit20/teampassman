package cc.yanyong.teampassman.model;

import cc.yanyong.teampassman.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Setter
@Getter
@ToString
public class LoginUser {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String uuid;
    private Long loginTime;
    private Long expireTime;
    private String ipAddress;
    private String loginLocation;
    private String browser;
    private String os;
    private Set<String> permissions;
    private User user;
}

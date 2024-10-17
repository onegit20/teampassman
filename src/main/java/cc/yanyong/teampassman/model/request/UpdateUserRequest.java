package cc.yanyong.teampassman.model.request;

import cc.yanyong.teampassman.model.UserProfile;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private Long userId;
    private UserProfile userProfile;
}

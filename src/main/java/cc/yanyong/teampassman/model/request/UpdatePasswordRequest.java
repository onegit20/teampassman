package cc.yanyong.teampassman.model.request;

import lombok.Getter;

@Getter
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}

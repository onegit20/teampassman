package cc.yanyong.teampassman.model.request;

import lombok.Getter;

@Getter
public class ChangeOwnerVaultRequest {
    private Integer vaultId;
    private Long userId;
}

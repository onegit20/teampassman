package cc.yanyong.teampassman.model.request;

import cc.yanyong.teampassman.model.VaultProfile;
import lombok.Getter;

@Getter
public class UpdateVaultRequest {
    private Integer vaultId;
    private VaultProfile vaultProfile;
}

package cc.yanyong.teampassman.model.request;

import lombok.Getter;

@Getter
public class ChangeVaultIdItemRequest {
    private Long itemId;
    private Integer vaultId;
}

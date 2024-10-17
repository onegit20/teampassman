package cc.yanyong.teampassman.model.request;

import cc.yanyong.teampassman.model.ItemProfile;
import lombok.Getter;

@Getter
public class UpdateItemRequest {
    private Long itemId;
    private ItemProfile itemProfile;
}

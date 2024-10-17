package cc.yanyong.teampassman.service;

import cc.yanyong.teampassman.common.Result;
import cc.yanyong.teampassman.entity.Item;
import cc.yanyong.teampassman.model.request.ChangeVaultIdItemRequest;
import cc.yanyong.teampassman.model.request.DeleteItemRequest;
import cc.yanyong.teampassman.model.request.UpdateItemRequest;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ItemService extends IService<Item> {
    Result createItem(Item item);
    Result deleteItem(DeleteItemRequest deleteItemRequest);
    Result updateItem(UpdateItemRequest updateItemRequest);
    Result changeVaultIdItem(ChangeVaultIdItemRequest changeVaultIdItemRequest);
    Result listItems(long pageNumber, long pageSize);
}

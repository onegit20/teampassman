package cc.yanyong.teampassman.service.impl;

import cc.yanyong.teampassman.common.Result;
import cc.yanyong.teampassman.entity.Item;
import cc.yanyong.teampassman.mapper.ItemMapper;
import cc.yanyong.teampassman.model.ItemProfile;
import cc.yanyong.teampassman.model.request.ChangeVaultIdItemRequest;
import cc.yanyong.teampassman.model.request.DeleteItemRequest;
import cc.yanyong.teampassman.model.request.UpdateItemRequest;
import cc.yanyong.teampassman.service.ItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Result createItem(Item item) {
        item.setId(null);
        if (itemMapper.insert(item) > 0)
            return Result.ok().message("创建成功");
        return Result.fail().message("创建失败");
    }

    @Override
    public Result deleteItem(DeleteItemRequest deleteItemRequest) {
        Long itemId = deleteItemRequest.getItemId();

        if (valueExists(Item::getId, itemId)) {
            itemMapper.deleteById(itemId);
            return Result.ok().message("成功删除条目");
        }

        return Result.fail().message("此条目不存在");
    }

    @Override
    public Result updateItem(UpdateItemRequest updateItemRequest) {
        Long itemId = updateItemRequest.getItemId();
        ItemProfile itemProfile = updateItemRequest.getItemProfile();

        if (valueExists(Item::getId, itemId)) {
            Item item = new Item();
            item.setId(itemId);
            item.setUsername(itemProfile.getUsername());
            item.setPassword(itemProfile.getPassword());
            item.setWebsite(itemProfile.getWebsite());
            item.setNotes(itemProfile.getNotes());
            item.setTag(itemProfile.getTag());
            itemMapper.updateById(item);
            return Result.ok().message("成功更新条目");
        }
        return Result.fail().message("此条目不存在");
    }

    @Override
    public Result changeVaultIdItem(ChangeVaultIdItemRequest changeVaultIdItemRequest) {
        Long itemId = changeVaultIdItemRequest.getItemId();
        Integer vaultId = changeVaultIdItemRequest.getVaultId();

        if (valueExists(Item::getId, itemId)) {
            Item item = new Item();
            item.setId(itemId);
            item.setVaultId(vaultId);
            itemMapper.updateById(item);
            return Result.ok().message("成功更新条目");
        }
        return Result.fail().message("此条目不存在");
    }

    @Override
    public Result listItems(long pageNumber, long pageSize) {
        Page<Item> page = new Page<>(pageNumber, pageSize);
        IPage<Item> iPage = itemMapper.selectPage(page, null);
        return Result.ok().data(iPage);
    }

    private <T> boolean valueExists(SFunction<Item, ?> sFunction, T value) {
        LambdaQueryWrapper<Item> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(sFunction, value);
        return itemMapper.exists(wrapper);
    }
}

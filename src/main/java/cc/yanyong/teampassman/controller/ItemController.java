package cc.yanyong.teampassman.controller;

import cc.yanyong.teampassman.common.Constants;
import cc.yanyong.teampassman.common.Result;
import cc.yanyong.teampassman.entity.Item;
import cc.yanyong.teampassman.model.request.*;
import cc.yanyong.teampassman.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
@Tag(name = "条目")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Operation(summary = "创建条目")
    @PostMapping("/createItem")
    public Result createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @Operation(summary = "删除条目")
    @PostMapping("/deleteItem")
    public Result deleteItem(@RequestBody DeleteItemRequest deleteItemRequest) {
        return itemService.deleteItem(deleteItemRequest);
    }

    @Operation(summary = "更新条目")
    @PostMapping("/updateItem")
    public Result updateItem(@RequestBody UpdateItemRequest updateItemRequest) {
        return itemService.updateItem(updateItemRequest);
    }

    @Operation(summary = "变更所属保险库")
    @PostMapping("/changeVaultIdItem")
    public Result changeVaultIdItem(@RequestBody ChangeVaultIdItemRequest changeVaultIdItemRequest) {
        return itemService.changeVaultIdItem(changeVaultIdItemRequest);
    }

    @Operation(summary = "列出所有条目")
    @GetMapping("/listItems")
    public Result listItems(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER_STRING) long pageNumber,
                             @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE_STRING) long pageSize) {

        return itemService.listItems(pageNumber, pageSize);
    }
}

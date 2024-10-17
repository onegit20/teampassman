package cc.yanyong.teampassman.controller;

import cc.yanyong.teampassman.common.Constants;
import cc.yanyong.teampassman.common.Result;
import cc.yanyong.teampassman.entity.Vault;
import cc.yanyong.teampassman.model.request.ChangeOwnerVaultRequest;
import cc.yanyong.teampassman.model.request.DeleteVaultRequest;
import cc.yanyong.teampassman.model.request.UpdateVaultRequest;
import cc.yanyong.teampassman.service.VaultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vault")
@Tag(name = "保险库")
public class VaultController {
    @Autowired
    private VaultService vaultService;

    @Operation(summary = "创建保险库")
    @PostMapping("/createVault")
    public Result createVault(@RequestBody Vault vault) {
        return vaultService.createVault(vault);
    }

    @Operation(summary = "删除保险库")
    @PostMapping("/deleteVault")
    public Result deleteVault(@RequestBody DeleteVaultRequest deleteVaultRequest) {
        return vaultService.deleteVault(deleteVaultRequest);
    }

    @Operation(summary = "更新保险库")
    @PostMapping("/updateVault")
    public Result updateVault(@RequestBody UpdateVaultRequest updateVaultRequest) {
        return vaultService.updateVault(updateVaultRequest);
    }

    @Operation(summary = "变更拥有者")
    @PostMapping("/changeOwnerVault")
    public Result changeOwnerVault(@RequestBody ChangeOwnerVaultRequest changeOwnerVaultRequest) {
        return vaultService.changeOwnerVault(changeOwnerVaultRequest);
    }

    @Operation(summary = "列出所有保险库")
    @GetMapping("/listVaults")
    public Result listVaults(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER_STRING) long pageNumber,
                             @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE_STRING) long pageSize) {

        return vaultService.listVaults(pageNumber, pageSize);
    }

    @Operation(summary = "列出所有保险库及其拥有者")
    @GetMapping("/listVaultsWithUser")
    public Result listVaultsWithUser(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER_STRING) long pageNumber,
                                     @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE_STRING) long pageSize) {

        return vaultService.listVaultsWithUser(pageNumber, pageSize);
    }
}

package cc.yanyong.teampassman.service;

import cc.yanyong.teampassman.common.Result;
import cc.yanyong.teampassman.entity.Vault;
import cc.yanyong.teampassman.model.request.ChangeOwnerVaultRequest;
import cc.yanyong.teampassman.model.request.DeleteVaultRequest;
import cc.yanyong.teampassman.model.request.UpdateVaultRequest;
import com.baomidou.mybatisplus.extension.service.IService;

public interface VaultService extends IService<Vault> {
    Result createVault(Vault vault);
    Result deleteVault(DeleteVaultRequest deleteVaultRequest);
    Result updateVault(UpdateVaultRequest updateVaultRequest);
    Result changeOwnerVault(ChangeOwnerVaultRequest changeOwnerVaultRequest);
    Result listVaults(long pageNumber, long pageSize);
    Result listVaultsWithUser(long pageNumber, long pageSize);
}

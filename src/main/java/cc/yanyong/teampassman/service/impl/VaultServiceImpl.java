package cc.yanyong.teampassman.service.impl;

import cc.yanyong.teampassman.common.Result;
import cc.yanyong.teampassman.entity.Vault;
import cc.yanyong.teampassman.mapper.VaultMapper;
import cc.yanyong.teampassman.model.VaultProfile;
import cc.yanyong.teampassman.model.request.ChangeOwnerVaultRequest;
import cc.yanyong.teampassman.model.request.DeleteVaultRequest;
import cc.yanyong.teampassman.model.request.UpdateVaultRequest;
import cc.yanyong.teampassman.service.VaultService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VaultServiceImpl extends ServiceImpl<VaultMapper, Vault> implements VaultService {
    @Autowired
    private VaultMapper vaultMapper;

    @Override
    public Result createVault(Vault vault) {
        vault.setId(null);
        vault.setUser(null);
        if (vaultMapper.insert(vault) > 0)
            return Result.ok().message("创建成功");
        return Result.fail().message("创建失败");
    }

    @Override
    public Result deleteVault(DeleteVaultRequest deleteVaultRequest) {
        Integer vaultId = deleteVaultRequest.getVaultId();

        if (valueExists(Vault::getId, vaultId)) {
            vaultMapper.deleteById(vaultId);
            return Result.ok().message("成功删除保险库");
        }

        return Result.fail().message("此保险库不存在");
    }

    @Override
    public Result updateVault(UpdateVaultRequest updateVaultRequest) {
        Integer vaultId = updateVaultRequest.getVaultId();
        VaultProfile vaultProfile = updateVaultRequest.getVaultProfile();

        if (valueExists(Vault::getId, vaultId)) {
            Vault vault = new Vault();
            vault.setId(vaultId);
            vault.setName(vaultProfile.getName());
            vault.setDescription(vaultProfile.getDescription());
            vaultMapper.updateById(vault);
            return Result.ok().message("成功更新保险库");
        }
        return Result.fail().message("此保险库不存在");
    }

    @Override
    public Result changeOwnerVault(ChangeOwnerVaultRequest changeOwnerVaultRequest) {
        Integer vaultId = changeOwnerVaultRequest.getVaultId();
        Long userId = changeOwnerVaultRequest.getUserId();

        if (valueExists(Vault::getId, vaultId)) {
            Vault vault = new Vault();
            vault.setId(vaultId);
            vault.setUserId(userId);
            vaultMapper.updateById(vault);
            return Result.ok().message("成功更新保险库");
        }
        return Result.fail().message("此保险库不存在");
    }

    @Override
    public Result listVaults(long pageNumber, long pageSize) {
        Page<Vault> page = new Page<>(pageNumber, pageSize);
        IPage<Vault> iPage = vaultMapper.selectPage(page, null);
        return Result.ok().data(iPage);
    }

    @Override
    public Result listVaultsWithUser(long pageNumber, long pageSize) {
        long offset = (pageNumber - 1L) * pageSize;
        List<Vault> vaults = vaultMapper.listVaultsWithUser(pageSize, offset);
        long total = vaultMapper.selectCount(null);
        long pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        Page<Vault> page = new Page<>(pageNumber, pageSize);
        page.setRecords(vaults);
        page.setTotal(total);
        page.setPages(pages);
        return Result.ok().data(page);
    }

    private Map<String, Object> createMap(List<Vault> vaults) {
        Map<String, Object> map = new HashMap<>();
        map.put("vaults", vaults);
        map.put("total", vaults.size());
        return map;
    }

    private <T> boolean valueExists(SFunction<Vault, ?> sFunction, T value) {
        LambdaQueryWrapper<Vault> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(sFunction, value);
        return vaultMapper.exists(wrapper);
    }
}

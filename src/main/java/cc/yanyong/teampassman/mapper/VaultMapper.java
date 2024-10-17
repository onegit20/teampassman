package cc.yanyong.teampassman.mapper;

import cc.yanyong.teampassman.entity.Vault;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VaultMapper extends BaseMapper<Vault> {
    List<Vault> listVaultsWithUser(@Param("row_count") long pageSize, long offset);
}

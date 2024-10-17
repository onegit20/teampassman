package cc.yanyong.teampassman.mapper;

import cc.yanyong.teampassman.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> listUsersWithVaults(@Param("row_count") long pageSize, long offset);
}

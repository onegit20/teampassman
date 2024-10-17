package cc.yanyong.teampassman.service;

import cc.yanyong.teampassman.common.Result;
import cc.yanyong.teampassman.entity.User;
import cc.yanyong.teampassman.model.request.DeleteUserRequest;
import cc.yanyong.teampassman.model.request.LoginRequest;
import cc.yanyong.teampassman.model.request.UpdatePasswordRequest;
import cc.yanyong.teampassman.model.request.UpdateUserRequest;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    Result createUser(User user);

    Result login(LoginRequest loginRequest);

    Result logout(String token);

    Result getLoginUser(String token);

    Result getUser(String token, Long id);

    Result updateUser(String token, UpdateUserRequest updateUserRequest);

    Result updatePassword(String token, UpdatePasswordRequest updatePasswordRequest);

    Result deleteUser(String token, DeleteUserRequest deleteUserRequest);

    Result listUsers(long pageNumber, long pageSize);

    Result listUsersWithVaults(long pageNumber, long pageSize);
}

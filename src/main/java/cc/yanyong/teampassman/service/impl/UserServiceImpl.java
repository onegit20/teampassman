package cc.yanyong.teampassman.service.impl;

import cc.yanyong.teampassman.common.CodeAndMsg;
import cc.yanyong.teampassman.common.Result;
import cc.yanyong.teampassman.entity.User;
import cc.yanyong.teampassman.mapper.UserMapper;
import cc.yanyong.teampassman.model.LoginUser;
import cc.yanyong.teampassman.model.request.DeleteUserRequest;
import cc.yanyong.teampassman.model.request.LoginRequest;
import cc.yanyong.teampassman.model.request.UpdatePasswordRequest;
import cc.yanyong.teampassman.model.request.UpdateUserRequest;
import cc.yanyong.teampassman.service.UserService;
import cc.yanyong.teampassman.util.IdUtils;
import cc.yanyong.teampassman.util.JwtUtils;
import cc.yanyong.teampassman.util.RegexUtils;
import cc.yanyong.teampassman.util.TypeUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final String LOGIN_USER_KEY_PREFIX = "login_user:";
    private static final String CLAIM_KEY = "login_user_key" ;
    private static final String TOKEN_BLACKLIST_KEY_PREFIX = "token:blacklist:";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Result createUser(User user) {
        /* 检测用户名是否有效及是否已存在 */
        if ((Objects.equals(user.getUsername(), "") || user.getUsername() == null))  // 传空值或不传参
            return Result.fail()
                    .message("请正确输入username");
        if (! RegexUtils.validateUsername(user.getUsername()))
            return Result.fail()
                    .code(Integer.parseInt(CodeAndMsg.FAIL10001_CODE))
                    .message(CodeAndMsg.FAIL10001_MSG);
        if (valueExists(User::getUsername, user.getUsername()))
            return Result.fail()
                    .code(Integer.parseInt(CodeAndMsg.FAIL10002_CODE))
                    .message(CodeAndMsg.FAIL10002_MSG);

        /* 密码强度检测 */
        if ((Objects.equals(user.getPassword(), "") || user.getPassword() == null))  // 传空值或不传参
            return Result.fail()
                    .message("请正确输入password");
        if (! RegexUtils.validatePassword(user.getPassword()))
            return Result.fail()
                    .code(Integer.parseInt(CodeAndMsg.FAIL10003_CODE))
                    .message(CodeAndMsg.FAIL10003_MSG);

        /* 检测邮箱是否有效及是否已存在 */
        if ((Objects.equals(user.getEmail(), "") || user.getEmail() == null)) {
            // 传空值或不传参，不做检测
        } else {
            if (! RegexUtils.validateEmail(user.getEmail()))
                return Result.fail()
                        .code(Integer.parseInt(CodeAndMsg.FAIL10004_CODE))
                        .message(CodeAndMsg.FAIL10004_MSG);
            if (valueExists(User::getEmail, user.getEmail()))
                return Result.fail()
                        .code(Integer.parseInt(CodeAndMsg.FAIL10005_CODE))
                        .message(CodeAndMsg.FAIL10005_MSG);
        }

        /* 检测手机号是否有效及是否已存在 */
        if ((Objects.equals(user.getPhone(), "") || user.getPhone() == null)) {
            // 传空值或不传参，不做检测
        } else {
            if (! RegexUtils.validatePhone(user.getPhone()))
                return Result.fail()
                        .code(Integer.parseInt(CodeAndMsg.FAIL10006_CODE))
                        .message(CodeAndMsg.FAIL10006_MSG);
            if (valueExists(User::getPhone, user.getPhone()))
                return Result.fail()
                        .code(Integer.parseInt(CodeAndMsg.FAIL10007_CODE))
                        .message(CodeAndMsg.FAIL10007_MSG);
        }

        /* 检测完成，创建用户 */
        user.setId(null);  // 强制置空，避免前端传id值带来的问题
        user.setVaults(null);  // 强制置空
        if (userMapper.insert(user) > 0)
            return Result.ok().message("创建成功").data(user);
        return Result.fail().message("创建失败");
    }

    @Override
    public Result login(LoginRequest loginRequest) {
        if (checkUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())) {
            LoginUser loginUser = new LoginUser();
            User user = selectUserByUsername(loginRequest.getUsername());

            String uuid = IdUtils.uuidNoHyphens();
            long timestamp = System.currentTimeMillis();
            loginUser.setUserId(user.getId());
            loginUser.setUuid(uuid);
            loginUser.setLoginTime(timestamp);
            loginUser.setExpireTime(timestamp + JwtUtils.EXPIRATION_TIME_MS);
            loginUser.setUser(user);

            redisTemplate.opsForValue().set(LOGIN_USER_KEY_PREFIX + uuid, loginUser, JwtUtils.EXPIRATION_TIME_MS, TimeUnit.MILLISECONDS);

            Map<String, Object> claims = new HashMap<>();
            claims.put(CLAIM_KEY, uuid);
            String token = JwtUtils.generateToken(null, claims);

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            return Result.ok().message("登录成功").data(data);
        }
        return Result.fail()
                .code(Integer.parseInt(CodeAndMsg.FAIL10008_CODE))
                .message(CodeAndMsg.FAIL10008_MSG);
    }

    @Override
    public Result logout(String token) {
        if (! JwtUtils.validateToken(token))
            return Result.fail().message("token无效").code(20001);

        String uuid = JwtUtils.getClaimFromToken(token, CLAIM_KEY).toString();
        boolean loginUserKeyExists = Boolean.TRUE.equals(redisTemplate.hasKey(LOGIN_USER_KEY_PREFIX + uuid));
        boolean tokenBlacklistKeyExists = Boolean.TRUE.equals(redisTemplate.hasKey(TOKEN_BLACKLIST_KEY_PREFIX + uuid));

        if (loginUserKeyExists)
            redisTemplate.delete(LOGIN_USER_KEY_PREFIX + uuid);
        if (! tokenBlacklistKeyExists) {
            long timeout = JwtUtils.getExpirationTimeFromToken(token) - System.currentTimeMillis();
            redisTemplate.opsForValue().set(TOKEN_BLACKLIST_KEY_PREFIX + uuid, null, timeout, TimeUnit.MILLISECONDS);
        }

        return Result.ok().message("成功登出");
    }

    @Override
    public Result getLoginUser(String token) {
        if (! JwtUtils.validateToken(token))
            return Result.fail().message("token无效").code(20001);

        String uuid = JwtUtils.getClaimFromToken(token, CLAIM_KEY).toString();
        Object loginUser = redisTemplate.opsForValue().get(LOGIN_USER_KEY_PREFIX + uuid);

        return Result.ok().message("获取登录用户信息成功").data(loginUser);
    }

    @Override
    public Result getUser(String token, Long id) {
        if (JwtUtils.validateToken(token)) {
            User user = userMapper.selectById(id);
            return Result.ok().data(user);
        } else {
            return Result.fail().message("token无效").code(20001);
        }
    }

    @Override
    public Result updateUser(String token, UpdateUserRequest updateUserRequest) {
        if (JwtUtils.validateToken(token)) {
            if (valueExists(User::getId, updateUserRequest.getUserId())) {
                User user = new User();
                user.setId(updateUserRequest.getUserId());
                user.setNickname(updateUserRequest.getUserProfile().getNickname());
                user.setAvatar(updateUserRequest.getUserProfile().getAvatar());
                user.setPhone(updateUserRequest.getUserProfile().getPhone());
                user.setEmail(updateUserRequest.getUserProfile().getEmail());
                userMapper.updateById(user);
                return Result.ok().message("成功更新用户").data(updateUserRequest);
            }
            return Result.fail().message("此用户不存在");
        } else {
            return Result.fail().message("token无效").code(20001);
        }
    }

    @Override
    public Result updatePassword(String token, UpdatePasswordRequest updatePasswordRequest) {
        if (! JwtUtils.validateToken(token))
            return Result.fail().message("token无效").code(20001);

        String uuid = JwtUtils.getClaimFromToken(token, CLAIM_KEY).toString();
        String oldPassword = updatePasswordRequest.getOldPassword();
        String newPassword = updatePasswordRequest.getNewPassword();

        Object loginUser = redisTemplate.opsForValue().get(LOGIN_USER_KEY_PREFIX + uuid);
        System.out.println(loginUser);

        try {
            LoginUser loginUser1 = TypeUtils.convertObjectToType(loginUser, LoginUser.class);
            Long userId = (loginUser1.getUserId());

            if (checkUserIdAndPassword(userId, oldPassword)) {
                if (updatePasswordByUserId(userId, newPassword) > 0)
                    return Result.ok().message("密码更新成功");
                return Result.fail().message("密码更新失败");
            } else {
                return Result.fail().message("原密码不正确");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Result.fail().message("Json转换异常，密码更新失败");
        }
    }

    @Override
    public Result deleteUser(String token, DeleteUserRequest deleteUserRequest) {
        if (! JwtUtils.validateToken(token))
            return Result.fail().message("token无效").code(20001);

        if (valueExists(User::getId, deleteUserRequest.getUserId())) {
            userMapper.deleteById(deleteUserRequest.getUserId());
            return Result.ok().message("成功删除用户");
        }

        return Result.fail().message("此用户不存在");
    }

    @Override
    public Result listUsers(long pageNumber, long pageSize) {
        Page<User> page = new Page<>(pageNumber, pageSize);
        IPage<User> iPage = userMapper.selectPage(page, null);
        return Result.ok().data(iPage);
    }

    @Override
    public Result listUsersWithVaults(long pageNumber, long pageSize) {
        long offset = (pageNumber - 1L) * pageSize;
        List<User> users = userMapper.listUsersWithVaults(pageSize, offset);
        long total = userMapper.selectCount(null);
        long pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        Page<User> page = new Page<>(pageNumber, pageSize);
        page.setRecords(users);
        page.setTotal(total);
        page.setPages(pages);
        return Result.ok().data(page);
    }

    private <T> boolean valueExists(SFunction<User, ?> sFunction, T value) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(sFunction, value);
        return userMapper.exists(wrapper);
    }

    private boolean checkUsernameAndPassword(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username).eq(User::getPassword, password);
        return userMapper.exists(wrapper);
    }

    private User selectUserByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    private boolean checkUserIdAndPassword(Long userId, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, userId).eq(User::getPassword, password);
        return userMapper.exists(wrapper);
    }

    private int updatePasswordByUserId(Long userId, String password) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, userId).set(User::getPassword, password);
        return userMapper.update(wrapper);
    }
}

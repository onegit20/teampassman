package cc.yanyong.teampassman.controller;

import cc.yanyong.teampassman.common.CodeAndMsg;
import cc.yanyong.teampassman.common.Constants;
import cc.yanyong.teampassman.common.Result;
import cc.yanyong.teampassman.entity.User;
import cc.yanyong.teampassman.model.request.DeleteUserRequest;
import cc.yanyong.teampassman.model.request.LoginRequest;
import cc.yanyong.teampassman.model.request.UpdatePasswordRequest;
import cc.yanyong.teampassman.model.request.UpdateUserRequest;
import cc.yanyong.teampassman.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "用户")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "创建用户")
    @ApiResponses({
            @ApiResponse(responseCode = CodeAndMsg.SUCCESS_CODE, description = CodeAndMsg.SUCCESS_MSG),
            @ApiResponse(responseCode = CodeAndMsg.FAIL_CODE, description = CodeAndMsg.FAIL_MSG),
            @ApiResponse(responseCode = CodeAndMsg.FAIL10001_CODE, description = CodeAndMsg.FAIL10001_MSG),
            @ApiResponse(responseCode = CodeAndMsg.FAIL10002_CODE, description = CodeAndMsg.FAIL10002_MSG),
            @ApiResponse(responseCode = CodeAndMsg.FAIL10003_CODE, description = CodeAndMsg.FAIL10003_MSG),
            @ApiResponse(responseCode = CodeAndMsg.FAIL10004_CODE, description = CodeAndMsg.FAIL10004_MSG),
            @ApiResponse(responseCode = CodeAndMsg.FAIL10005_CODE, description = CodeAndMsg.FAIL10005_MSG),
            @ApiResponse(responseCode = CodeAndMsg.FAIL10006_CODE, description = CodeAndMsg.FAIL10006_MSG),
            @ApiResponse(responseCode = CodeAndMsg.FAIL10007_CODE, description = CodeAndMsg.FAIL10007_MSG)
    })
    @PostMapping("/createUser")
    public Result createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result logout(
            @Parameter(name = "Authorization", description = "Access Token", in = ParameterIn.HEADER, required = true)
            @RequestHeader("Authorization") String token) {

        return userService.logout(token);
    }

    @Operation(summary = "获取登录用户信息")
    @GetMapping("/getLoginUser")
    public Result getLoginUser(
            @Parameter(name = "Authorization", description = "Access Token", in = ParameterIn.HEADER, required = true)
            @RequestHeader("Authorization") String token) {

        return userService.getLoginUser(token);
    }

    @Operation(summary = "获取用户资料")
    @GetMapping("/getUser")
    public Result getUser(
            @Parameter(name = "Authorization", description = "Access Token", in = ParameterIn.HEADER, required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "User ID")
            @RequestParam(name = "userId") Long id) {

        return userService.getUser(token, id);
    }

    @Operation(summary = "更新用户资料")
    @PostMapping("/updateUser")
    public Result updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateUserRequest updateUserRequest) {

        return userService.updateUser(token, updateUserRequest);
    }

    @Operation(summary = "更新用户密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdatePasswordRequest updatePasswordRequest) {

        return userService.updatePassword(token, updatePasswordRequest);
    }

    @Operation(summary = "删除用户"/*, description = "将用户标记为已删除"*/)
    @PostMapping("/deleteUser")
    public Result deleteUser(
            @RequestHeader("Authorization") String token,
            @RequestBody DeleteUserRequest deleteUserRequest) {

        return userService.deleteUser(token, deleteUserRequest);
    }

    @Operation(summary = "列出所有用户")
    @GetMapping("/listUsers")
    public Result listUsers(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER_STRING) long pageNumber,
                            @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE_STRING) long pageSize) {

        return userService.listUsers(pageNumber, pageSize);
    }

    @Operation(summary = "列出所有用户及其拥有的保险库")
    @GetMapping("/listUsersWithVaults")
    public Result listUsersWithVaults(@RequestParam(defaultValue = Constants.DEFAULT_PAGE_NUMBER_STRING) long pageNumber,
                                      @RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE_STRING) long pageSize) {

        return userService.listUsersWithVaults(pageNumber, pageSize);
    }
}

package cc.yanyong.teampassman.controller;

import cc.yanyong.teampassman.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "ping")
public class PingController {
    @Operation(summary = "ping接口", description = "用来测试API服务是否可用！")
    @GetMapping("/ping")
    public Result ping() {
        System.out.println("PingController: ping()");
        return Result.ok().data("这是ping接口!");
    }
}

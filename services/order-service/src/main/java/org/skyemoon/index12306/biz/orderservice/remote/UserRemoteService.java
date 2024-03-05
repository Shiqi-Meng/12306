package org.skyemoon.index12306.biz.orderservice.remote;

import jakarta.validation.constraints.NotEmpty;
import org.skyemoon.index12306.biz.orderservice.remote.dto.UserQueryActualRespDTO;
import org.skyemoon.index12306.framework.starter.convention.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户远程服务调用
 */
@FeignClient(value = "index12306-user${unique-name:}-service", url = "${aggregation.remote-url:}")
public interface UserRemoteService {

    @GetMapping("/api/user-service/actual/query")
    Result<UserQueryActualRespDTO> queryActualUserByUsername(@RequestParam("username") @NotEmpty String username);
}

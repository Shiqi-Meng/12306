package org.skyemoon.index12306.biz.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.userservice.dto.req.PassengerReqDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.PassengerActualRespDTO;
import org.skyemoon.index12306.biz.userservice.dto.resp.PassengerRespDTO;
import org.skyemoon.index12306.biz.userservice.service.PassengerService;
import org.skyemoon.index12306.framework.starter.convention.result.Result;
import org.skyemoon.index12306.framework.starter.web.Results;
import org.skyemoon.index12306.frameworks.starter.user.core.UserContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PassengerController {

    private final PassengerService passengerService;

    /**
     * 根据用户名查询乘车人列表
     */
    @GetMapping("api/user-service/passenger/query")
    public Result<List<PassengerRespDTO>> listPassengerQueryByUsername() {
        return Results.success(passengerService.listPassengerQueryByUsername(UserContext.getUsername()));
    }

    /**
     * 根据乘车人ID集合查询乘车人列表
     */
    @GetMapping("/api/user-service/inner/passenger/actual/query/ids")
    public Result<List<PassengerActualRespDTO>> listPassengerQueryByIds(@RequestParam("username") String username, @RequestParam("ids") List<Long> ids) {
        return Results.success(passengerService.listPassengerQueryByIds(username, ids));
    }

    /**
     * 新增乘车人
     */
    @PostMapping("/api/user-service/passenger/save")
    public Result<Void> savePassenger(@RequestBody PassengerReqDTO requestParam) {
        passengerService.savePassenger(requestParam);
        return Results.success();
    }
}

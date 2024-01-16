package org.skyemoon.index12306.biz.ticketservice.remote;

import org.skyemoon.index12306.biz.ticketservice.remote.dto.PayInfoRespDTO;
import org.skyemoon.index12306.biz.ticketservice.remote.dto.RefundReqDTO;
import org.skyemoon.index12306.biz.ticketservice.remote.dto.RefundRespDTO;
import org.skyemoon.index12306.framework.starter.convention.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 支付订单远程调用
 */
@FeignClient(value = "index12306-pay${unique-name:}-service", url = "${dev.remote-url:}")
public interface PayRemoteService {

    @GetMapping("/api/pay-service/pay/query")
    Result<PayInfoRespDTO> getPayInfo(@RequestParam(value = "orderSn") String orderSn);

    @PostMapping("/api/pay-service/common/refund")
    Result<RefundRespDTO> commonRefund(@RequestParam RefundReqDTO requestParam);
}

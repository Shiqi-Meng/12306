package org.skyemoon.index12306.biz.ticketservice.controller;

import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.ticketservice.dto.resp.TrainStationQueryRespDTO;
import org.skyemoon.index12306.biz.ticketservice.service.TrainStationService;
import org.skyemoon.index12306.framework.starter.convention.result.Result;
import org.skyemoon.index12306.framework.starter.web.Results;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrainStationController {

    private final TrainStationService trainStationService;

    /**
     * 根据列车 ID 查询站点信息
     */
    @GetMapping("/api/ticket-service/train-station/query")
    public Result<List<TrainStationQueryRespDTO>> listTrainStationQuery(String trainId) {
        return Results.success(trainStationService.listTrainStationQuery(trainId));
    }
}

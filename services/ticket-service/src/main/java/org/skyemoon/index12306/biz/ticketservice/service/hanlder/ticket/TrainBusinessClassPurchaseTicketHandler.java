package org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.skyemoon.index12306.biz.ticketservice.common.enums.VehicleSeatTypeEnum;
import org.skyemoon.index12306.biz.ticketservice.common.enums.VehicleTypeEnum;
import org.skyemoon.index12306.biz.ticketservice.dto.domain.PurchaseTicketPassengerDetailDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.domain.SelectSeatDTO;
import org.skyemoon.index12306.biz.ticketservice.dto.resp.TrainPurchaseTicketRespDTO;
import org.skyemoon.index12306.biz.ticketservice.service.SeatService;
import org.skyemoon.index12306.biz.ticketservice.service.hanlder.ticket.base.AbstractTrainPurchaseTicketTemplate;
import org.skyemoon.index12306.framework.starter.convention.exception.ServiceException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 高铁商务座购票组件
 */
@RequiredArgsConstructor
@Component
public class TrainBusinessClassPurchaseTicketHandler extends AbstractTrainPurchaseTicketTemplate {

    private final SeatService seatService;
    private static final Map<Character, Integer> SEAT_Y_INT = Map.of('A', 0, 'C', 1, 'F', 2);

    @Override
    public String mark() {
        return VehicleTypeEnum.HIGH_SPEED_RAIN.getName() + VehicleSeatTypeEnum.BUSINESS_CLASS.getName();
    }

    @Override
    protected List<TrainPurchaseTicketRespDTO> selectSeats(SelectSeatDTO requestParam) {
        String trainId = requestParam.getRequestParam().getTrainId();
        String departure = requestParam.getRequestParam().getDeparture();
        String arrival = requestParam.getRequestParam().getArrival();
        List<PurchaseTicketPassengerDetailDTO> passengerSeatDetails = requestParam.getPassengerSeatDetails();
        List<String> trainCarriageList = seatService.listUsableCarriageNumber(trainId, requestParam.getSeatType(), departure, arrival);
        List<Integer> trainStationCarriageRemainingTicket = seatService.listSeatRemainingTicket(trainId, departure, arrival, trainCarriageList);
        int remainingTicketSum = trainStationCarriageRemainingTicket.stream().mapToInt(Integer::intValue).sum();
        if (remainingTicketSum < passengerSeatDetails.size()) {
            throw new ServiceException("站点余票不足，请尝试更换座位类型或选择其它站点");
        }
        if (passengerSeatDetails.size() < 3) {
            if (CollUtil.isNotEmpty(requestParam.getRequestParam().getChooseSeats())) {
                Pair<List<TrainPurchaseTicketRespDTO>, Boolean> actualSeatPair = findMatchSeats(requestParam, trainCarriageList, trainStationCarriageRemainingTicket);
                return actualSeatPair.getKey();
            }
            return selectSeats(requestParam, trainCarriageList, trainStationCarriageRemainingTicket);
        } else {
            if (CollUtil.isNotEmpty(requestParam.getRequestParam().getChooseSeats())) {
                Pair<List<TrainPurchaseTicketRespDTO>, Boolean> actualSeatPair = findMatchSeats(requestParam, trainCarriageList, trainStationCarriageRemainingTicket);
                return actualSeatPair.getKey();
            }
        return selectComplexSeats(requestParam, trainCarriageList, trainStationCarriageRemainingTicket);
    }

}

    private List<TrainPurchaseTicketRespDTO> selectComplexSeats(SelectSeatDTO requestParam, List<String> trainCarriageList, List<Integer> trainStationCarriageRemainingTicket) {
        return null;
    }

    private List<TrainPurchaseTicketRespDTO> selectSeats(SelectSeatDTO requestParam, List<String> trainCarriageList, List<Integer> trainStationCarriageRemainingTicket) {
        return null;
    }

    private Pair<List<TrainPurchaseTicketRespDTO>, Boolean> findMatchSeats(SelectSeatDTO requestParam, List<String> trainCarriageList, List<Integer> trainStationCarriageRemainingTicket) {
        return null;
    }
}

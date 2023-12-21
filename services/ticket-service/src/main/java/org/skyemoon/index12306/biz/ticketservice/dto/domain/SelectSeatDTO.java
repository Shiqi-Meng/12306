package org.skyemoon.index12306.biz.ticketservice.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skyemoon.index12306.biz.ticketservice.dto.req.PurchaseTicketReqDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectSeatDTO {

    /**
     * 座位类型
     */
    private Integer seatType;

    /**
     * 座位对应的乘车人集合
     */
    private List<PurchaseTicketPassengerDetailDTO> passengerSeatDetails;

    /**
     * 购票原始入参
     */
    private PurchaseTicketReqDTO requestParam;
}

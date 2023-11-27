package org.skyemoon.index12306.biz.userservice.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
import org.skyemoon.index12306.framework.starter.database.base.BaseDO;

import java.util.Date;

@Data
@TableName("t_passenger")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerDO extends BaseDO {

    /**
     * id
     */
    private Long id;

    /**
     * username
     */
    private String username;

    /**
     * realName
     */
    private String realName;

    /**
     * id_type
     */
    private Integer idType;

    /**
     * id_card
     */
    private String idCard;

    /**
     * discount_type
     */
    private Integer discountType;

    /**
     * phone
     */
    private String phone;

    /**
     * create_date
     */
    private Date createDate;

    /**
     * verify_status
     */
    private Integer verifyStatus;
}

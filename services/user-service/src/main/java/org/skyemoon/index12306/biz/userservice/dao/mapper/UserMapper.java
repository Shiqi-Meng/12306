package org.skyemoon.index12306.biz.userservice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.skyemoon.index12306.biz.userservice.dao.entity.UserDO;

public interface UserMapper extends BaseMapper<UserDO> {

    void deletionUser(UserDO userDO);
}

package org.skyemoon.index12306.framework.starter.idempotent.core.token;

import org.skyemoon.index12306.framework.starter.idempotent.core.IdempotentExecuteHandler;

public interface IdempotentTokenService extends IdempotentExecuteHandler {

    /**
     * 创建幂等验证 Token
     */
    String createToken();
}

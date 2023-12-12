package org.skyemoon.index12306.framework.starter.idempotent.core;

import org.skyemoon.index12306.framework.starter.bases.ApplicationContextHolder;
import org.skyemoon.index12306.framework.starter.idempotent.core.param.IdempotentParamService;
import org.skyemoon.index12306.framework.starter.idempotent.core.spel.IdempotentSpELByMQExecuteHandler;
import org.skyemoon.index12306.framework.starter.idempotent.core.spel.IdempotentSpELByRestAPIExecuteHandler;
import org.skyemoon.index12306.framework.starter.idempotent.core.token.IdempotentTokenService;
import org.skyemoon.index12306.framework.starter.idempotent.enums.IdempotentSceneEnum;
import org.skyemoon.index12306.framework.starter.idempotent.enums.IdempotentTypeEnum;

public class IdempotentExecuteHandlerFactory {

    public static IdempotentExecuteHandler getInstance(IdempotentSceneEnum scene, IdempotentTypeEnum type) {
        IdempotentExecuteHandler result = null;
        switch (scene) {
            case RESTAPI -> {
                switch (type) {
                    case PARAM -> result = ApplicationContextHolder.getBean(IdempotentParamService.class);
                    case TOKEN -> result = ApplicationContextHolder.getBean(IdempotentTokenService.class);
                    case SPEL -> result = ApplicationContextHolder.getBean(IdempotentSpELByRestAPIExecuteHandler.class);
                    default -> {
                    }
                }
            }
            case MQ -> result = ApplicationContextHolder.getBean(IdempotentSpELByMQExecuteHandler.class);
            default -> {
            }
        }
        return result;
    }
}

package org.skyemoon.index12306.framework.starter.idempotent.toolkit;

import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;

/**
 * SpEL 表达式解析工具
 */
public class SpELUtil {

    public static Object parseKey(String spEL, Method method, Object[] contextObj) {
        ArrayList<String> spELFlag = Lists.newArrayList("#", "T(");
        Optional<String> optional = spELFlag.stream().filter(spEL::contains).findFirst();
        if (optional.isPresent()) {
            return parse(spEL, method, contextObj);
        }
        return spEL;
    }

    public static Object parse(String spEL, Method method, Object[] contextObj) {
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(spEL);
        String[] params = discoverer.getParameterNames(method);
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (ArrayUtil.isNotEmpty(params)) {
            for (int len = 0; len < params.length; len++) {
                context.setVariable(params[len], contextObj[len]);
            }
        }
        return exp.getValue(context);
    }
}

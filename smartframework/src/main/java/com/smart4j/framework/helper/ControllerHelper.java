package com.smart4j.framework.helper;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.smart4j.framework.annotation.Action;
import com.smart4j.framework.bean.Hander;
import com.smart4j.framework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ym on 2018/5/11.
 */
public class ControllerHelper {

    private final static Map<Request, Hander> ACTION_MAP = Maps.newHashMap();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> clazz : controllerClassSet) {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                for (Method method : declaredMethods) {
                    if (method.isAnnotationPresent(Action.class)) {
                        Action annotation = method.getAnnotation(Action.class);
                        String mapping = annotation.value();
                        if (mapping.matches("\\w+:/\\w*")) {
                            List<String> list = Splitter.on(":").omitEmptyStrings().splitToList(mapping);
                            if (CollectionUtils.size(list) == 2) {
                                String requestMethod = list.get(0);
                                String requestPath = list.get(1);
                                Request request = new Request(requestMethod, requestPath);
                                Hander hander = new Hander(clazz, method);
                                ACTION_MAP.put(request, hander);
                            }
                        }
                    }
                }
            }
        }
    }

    public static Hander getHander(String requestMethod, String requestPath) {
        return ACTION_MAP.get(new Request(requestMethod, requestPath));
    }
}

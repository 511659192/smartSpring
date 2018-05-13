package com.smart4j.framework.bean;

import com.google.common.base.Objects;
import com.sun.org.apache.regexp.internal.REUtil;
import lombok.Getter;

/**
 * Created by ym on 2018/5/11.
 */
@Getter
public class Request {

    private String requestMethod;

    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(requestMethod, requestPath);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Request) {
            Request other = (Request) obj;
            return Objects.equal(this.requestMethod, other.requestMethod) &&
                    Objects.equal(this.requestPath, other.requestPath);
        }
        return false;
    }
}

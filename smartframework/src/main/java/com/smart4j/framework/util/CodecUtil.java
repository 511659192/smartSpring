package com.smart4j.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by ym on 2018/5/11.
 */
@Slf4j
public class CodecUtil {

    public static String encodeURL(String source) {
        String encode = null;
        try {
            encode = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return encode;
    }

    public static String decodeURL(String source) {
        String decode = null;
        try {
            decode = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return decode;
    }
}

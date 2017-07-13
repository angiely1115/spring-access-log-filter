package me.laiyijie.util.log;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017-05-19.
 */
public class WebLogUtils {


    public static String getAllHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, Object> headers = new HashMap<>();
        for (; headerNames.hasMoreElements(); ) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return JSON.toJSONString(headers);
    }

    public static String getRequestParams(HttpServletRequest request) {

        String re = JSON.toJSONString(request.getParameterMap());

        if (re.length() > 256) {
            return re.substring(0, 256);
        }
        return re;
    }

    public static String getSessionUsername(HttpServletRequest request, String key) {
        Object result = request.getSession()
                               .getAttribute(key);
        if (result == null)
            return "";
        return String.valueOf(result);
    }

    public static String getRequestUrl(HttpServletRequest request) {
        return request.getRequestURL()
                      .toString();
    }

    public static String getAllHeaders(HttpServletResponse response) {
        Collection<String> headerNames = response.getHeaderNames();
        Map<String, Object> headers = new HashMap<>();
        headerNames.forEach(key -> headers.put(key, response.getHeaders(key)));
        return JSON.toJSONString(headers);
    }

    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}

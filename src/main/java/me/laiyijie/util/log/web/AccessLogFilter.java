package me.laiyijie.util.log.web;

import me.laiyijie.util.log.BLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AccessLogFilter extends OncePerRequestFilter {
    private static final Logger logger = LogManager.getLogger(AccessLogFilter.class);

    private String usernameKey = "username";
    private Integer payloadMaxLength = 1024;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        String requestPayload = getPayLoad(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());
        String responsePayload = getPayLoad(responseWrapper.getContentAsByteArray(),
                response.getCharacterEncoding());
        responseWrapper.copyBodyToResponse();
        BLog.accessJsonLogBuilder()
            .addRequestPayLoad(requestPayload)
            .addResponsePayLoad(responsePayload)
            .put(request, usernameKey)
            .put(response)
            .put("_COST_", System.currentTimeMillis() - startTime)
            .log();
    }

    private String getPayLoad(byte[] buf, String characterEncoding) {
        String payload = "";
        if (buf == null) return payload;
        if (buf.length > 0) {
            int length = Math.min(buf.length, getPayloadMaxLength());
            try {
                payload = new String(buf, 0, length, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
        }
        return payload;
    }


    public String getUsernameKey() {
        return usernameKey;
    }

    public void setUsernameKey(String usernameKey) {
        this.usernameKey = usernameKey;
    }

    public Integer getPayloadMaxLength() {
        return payloadMaxLength;
    }

    public void setPayloadMaxLength(Integer payloadMaxLength) {
        this.payloadMaxLength = payloadMaxLength;
    }
}

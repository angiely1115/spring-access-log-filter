package me.laiyijie.util.log.builder;

import me.laiyijie.util.log.WebLogUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by admin on 2017-05-19.
 */
public abstract class JsonLogBuilder<T extends JsonLogBuilder> {
    protected JSONObject data;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ZZZ");

    JsonLogBuilder() {
        data = new JSONObject();
        put("_TIME_", dateFormat.format(new Date(System.currentTimeMillis())));
        put("_LOGGER_", getLogger().getName());
    }

    @SuppressWarnings("unchecked")
    public T put(String key, Object value) {
        data.put(key, value);
        return (T) this;
    }
    @SuppressWarnings("unchecked")
    public T put(HttpServletResponse response) {
        return (T) this.put("P_HEADERS", WebLogUtils.getAllHeaders(response))
                       .put("P_CODE", response.getStatus());
    }
    @SuppressWarnings("unchecked")
    public T put(HttpServletRequest request, String userSessionKey) {
        return (T) this.put("Q_USERNAME", WebLogUtils.getSessionUsername(request, userSessionKey))
                       .put("Q_METHOD", request.getMethod())
                       .put("Q_URL", WebLogUtils.getRequestUrl(request))
                       .put("Q_PARAM", WebLogUtils.getRequestParams(request))
                       .put("Q_HEADERS", WebLogUtils.getAllHeaders(request));
    }

    public T put(Throwable throwable) {
        return this.put("E_TRACE", WebLogUtils.getTrace(throwable));
    }
    @SuppressWarnings("unchecked")
    public T putAll(Map<? extends String, ?> ob) {
        data.putAll(ob);
        return (T) this;
    }

    public JSONObject getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    public T clear() {
        data = new JSONObject();
        return (T) this;
    }

    public void log() {
        getLogger().info(JSON.toJSONString(getData(), SerializerFeature.WriteMapNullValue) );
    }

    protected abstract Logger getLogger();
}

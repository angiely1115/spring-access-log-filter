package me.laiyijie.util.log.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by admin on 2017/5/20.
 */
public class ErrorJsonLogBuilder extends JsonLogBuilder<ErrorJsonLogBuilder> {

    private static final Logger logger = LogManager.getLogger("Error");

    public ErrorJsonLogBuilder() {
        super();
        getData().put("_LOGGER_", "ERROR");
    }

    public ErrorJsonLogBuilder addErrorMessage(String message) {
        put("_ERRORMESSAGE_", message);
        return this;
    }

    @Override
    public void log() {
        getLogger().error(getData().toJSONString());
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}

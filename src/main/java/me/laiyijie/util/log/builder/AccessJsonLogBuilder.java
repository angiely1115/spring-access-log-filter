package me.laiyijie.util.log.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by admin on 2017-05-20.
 */
public class AccessJsonLogBuilder extends JsonLogBuilder<AccessJsonLogBuilder> {

    private static Logger logger = LogManager.getLogger("Access");

    public AccessJsonLogBuilder() {
        super();
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    public AccessJsonLogBuilder addRequestPayLoad(String requestPayLoad) {
        this.put("Q_PAYLOAD", requestPayLoad);
        return this;
    }

    public AccessJsonLogBuilder addResponsePayLoad(String responsePayLoad) {
        this.put("P_PAYLOAD", responsePayLoad);
        return this;
    }

}

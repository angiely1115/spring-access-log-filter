package me.laiyijie.util.log.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by admin on 2017-05-19.
 */
public class BusinessJsonLogBuilder extends JsonLogBuilder<BusinessJsonLogBuilder> {
    private String module;
    private static final Logger logger = LogManager.getLogger("Business");

    public BusinessJsonLogBuilder(String module) {
        super();
        getData().put("_LOGGER_", "BUSINESS");
        this.module = module;
    }

    public BusinessJsonLogBuilder addAction(String action) {
        this.put("_ACTION_", action);
        return this;
    }

    public BusinessJsonLogBuilder AddOperator(String operator) {
        this.put("_OPERATOR_", operator);
        return this;
    }

    public BusinessJsonLogBuilder AddOperator(Long operator) {
        this.put("_OPERATOR_", String.valueOf(operator));
        return this;
    }

    @Override
    public void log() {
        this.put("_MODULE_", module);
        super.log();
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}

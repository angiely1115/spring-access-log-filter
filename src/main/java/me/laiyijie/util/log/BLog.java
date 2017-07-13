package me.laiyijie.util.log;

import me.laiyijie.util.log.builder.AccessJsonLogBuilder;
import me.laiyijie.util.log.builder.BusinessJsonLogBuilder;
import me.laiyijie.util.log.builder.ErrorJsonLogBuilder;

/**
 * Created by admin on 2017-05-19.
 */
public class BLog {

    public static BusinessJsonLogBuilder businessJsonLogBuilder(String module) {
        return new BusinessJsonLogBuilder(module);
    }

    public static AccessJsonLogBuilder accessJsonLogBuilder() {
        return new AccessJsonLogBuilder();
    }

    public static ErrorJsonLogBuilder errorJsonLogBuilder() {
        return new ErrorJsonLogBuilder();
    }

}

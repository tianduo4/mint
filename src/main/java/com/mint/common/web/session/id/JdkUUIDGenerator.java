package com.mint.common.web.session.id;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class JdkUUIDGenerator
        implements SessionIdGenerator {
    public String get() {
        return StringUtils.remove(UUID.randomUUID().toString(), '-');
    }
}


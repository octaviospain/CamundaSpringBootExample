/*
 * Copyright (c) 2018. CreativeDock s.r.o. All rights reserved.
 */
package com.transgressoft.csbe.model.uuid;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
@Configuration
public class UuidGenerationWebMvcActivator implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UuidArgumentResolver());
    }
}

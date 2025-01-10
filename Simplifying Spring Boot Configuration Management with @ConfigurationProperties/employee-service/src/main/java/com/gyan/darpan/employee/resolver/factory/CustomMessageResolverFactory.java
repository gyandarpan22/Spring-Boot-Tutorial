package com.gyan.darpan.employee.resolver.factory;

import com.gyan.darpan.employee.resolver.CustomMessageResolver;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.layout.template.json.resolver.*;

@Plugin(name = "CustomMessageResolverFactory", category = TemplateResolverFactory.CATEGORY)
public class CustomMessageResolverFactory implements EventResolverFactory {

    private static final CustomMessageResolverFactory INSTANCE = new CustomMessageResolverFactory();

    private CustomMessageResolverFactory() {
    }

    @PluginFactory
    public static CustomMessageResolverFactory getInstance() {
        return INSTANCE;
    }


    @Override
    public String getName() {
        return CustomMessageResolver.getNAME();
    }

    @Override
    public TemplateResolver<LogEvent> create(EventResolverContext context, TemplateResolverConfig config) {
        return new CustomMessageResolver(config);
    }
}

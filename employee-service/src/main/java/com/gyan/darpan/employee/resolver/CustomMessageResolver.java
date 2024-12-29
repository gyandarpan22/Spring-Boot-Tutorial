package com.gyan.darpan.employee.resolver;

import lombok.Getter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.layout.template.json.resolver.EventResolver;
import org.apache.logging.log4j.layout.template.json.resolver.TemplateResolverConfig;
import org.apache.logging.log4j.layout.template.json.util.JsonWriter;
import org.apache.logging.log4j.message.Message;

public class CustomMessageResolver implements EventResolver {

    @Getter
    private static final String NAME = "customMessage";
    private final String prefix;

    public CustomMessageResolver(TemplateResolverConfig config) {
        prefix = config.getString("prefix");
    }


    @Override
    public void resolve(LogEvent logEvent, JsonWriter jsonWriter) {

        Message message = logEvent.getMessage();

        String messageStr = message.getFormattedMessage();

        messageStr = prefix + messageStr;

        jsonWriter.writeString(messageStr);

    }
}

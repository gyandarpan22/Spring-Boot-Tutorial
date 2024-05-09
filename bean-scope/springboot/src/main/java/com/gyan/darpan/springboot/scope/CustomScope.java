package com.gyan.darpan.springboot.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CustomScope implements Scope {
    private Map<String, Object> customObjectMap = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Runnable> destructionCallback = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        int random = ThreadLocalRandom.current().nextInt(10);
        System.out.println("Generated Value : " + random);
        boolean isEven = random % 2 == 0;
        if (isEven) {
            return objectFactory.getObject();
        }

        return customObjectMap.computeIfAbsent(
                name,
                key -> objectFactory.getObject()
        );
    }

    @Override
    public Object remove(String name) {
        destructionCallback.get(name).run();
        destructionCallback.remove(name);
        return customObjectMap.remove(name);
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        destructionCallback.put(name, callback);
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "custom";
    }
}

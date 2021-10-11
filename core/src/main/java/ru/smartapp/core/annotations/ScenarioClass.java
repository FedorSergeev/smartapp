package ru.smartapp.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Should be used for scenario's class to map with scenario's id
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScenarioClass {
    /**
     * Holds scenario's id
     */
    String[] value();
}

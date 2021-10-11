package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Информация о смартапе.
 */
@Getter
@Setter
public class AppInfoDTO {
    /**
     * Идентификатор проекта в SmartMarket Studio.
     */
    @JsonProperty("projectId")
    private String projectId;
    /**
     * Идентификатор смартапа.
     */
    @JsonProperty("applicationId")
    private String applicationId;
    /**
     * Идентификатор опубликованной версии смартапа.
     */
    @JsonProperty("appversionId")
    private String appVersionId;
    /**
     * Ссылка на веб-приложение. Поле актуально для Canvas Apps.
     */
    @JsonProperty("frontendEndpoint")
    private String frontendEndpoint;
    /**
     * Тип смартапа.
     * Обратите внимание, что ассистент перехватывает навигационные команды "вверх", "вниз", "влево" и "вправо"
     * только в Canvas App (тип приложения WEB_APP). В этом случае команды обрабатываются на уровне фронтенда приложения.
     * В остальных случаях, команды передаются в бекэнд активного приложения.
     * <p>
     * Возможные значения:
     * DIALOG;
     * WEB_APP;
     * APK;
     * CHAT_APP.
     */
    @JsonProperty("frontendType")
    private String frontendType;
    /**
     * Более читаемый аналог поля projectId. Не актуален для внешних приложений.
     */
    @JsonProperty("systemName")
    private String systemName;
    /**
     * Объединённое значение полей projectId, applicationId и appversionId.
     */
    @JsonProperty("frontendStateId")
    private String frontendStateId;
    @JsonProperty("ageLimit")
    private Long ageLimit;
}

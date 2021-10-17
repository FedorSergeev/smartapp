package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;


/**
 * Информация об устройстве пользователя.
 */
@Getter
@Setter
public class DeviceDto {
    /**
     * Операционная система устройства.
     * ANDROID/IOS
     */
    @JsonProperty("platformType")
    private String platformType;
    /**
     * Версия операционной системы.
     */
    @JsonProperty("platformVersion")
    private String platformVersion;
    /**
     * Устройство или мобильное приложение, от которого приходит вызов ассистента
     * Возможные значения:
     * - SBERBOX — запрос пришел от устройства SberBox;
     * - COMPANION — запрос от приложения Салют;
     * - STARGATE — запрос от устройства SberPortal.
     */
    @JsonProperty("surface")
    private String surface;
    /**
     * Версия поверхности.
     */
    @JsonProperty("surfaceVersion")
    private String surfaceVersion;
    /**
     * Идентификатор устройства.
     */
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("deviceManufacturer")
    private String deviceManufacturer;
    @JsonProperty("deviceModel")
    private String deviceModel;
    /**
     * Описание функциональности устройства.
     */
    @JsonProperty("features")
    private JsonNode features;
    /**
     * Описание возможностей устройства пользователя.
     */
    @JsonProperty("capabilities")
    private JsonNode capabilities;
    /**
     * Дополнительная информация об объекте или устройстве. В настоящий момент не используется.
     */
    @JsonProperty("additionalInfo")
    private JsonNode additionalInfo;
}

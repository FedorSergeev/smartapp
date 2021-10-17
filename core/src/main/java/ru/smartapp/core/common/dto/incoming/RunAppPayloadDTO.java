package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.AppInfoDTO;
import ru.smartapp.core.common.dto.CharacterDTO;
import ru.smartapp.core.common.dto.DeviceDTO;

@Getter
@Setter
public class RunAppPayloadDTO {
    @JsonProperty("device")
    private DeviceDTO device;
    @JsonProperty("app_info")
    private AppInfoDTO appInfo;
    /**
     * Имя смартапа, которое задается при создании проекта и отображается в каталоге приложений.
     */
    @JsonProperty("projectName")
    private String projectName;
    /**
     * Интент, который приходит при запуске смартапа.
     * <p>
     * Значение всегда run_app.
     */
    @JsonProperty("intent")
    private String intent;
    @JsonProperty("character")
    private CharacterDTO character;
    /**
     * Возможные стратегии смартапа.
     */
    @Nullable
    @JsonProperty("strategies")
    private JsonNode strategies;
    /**
     * Информация о запускаемом смартапе и параметрах его запуска.
     * Формируется бэкендом приложения.
     * По умолчанию: пустой объект.
     */
    @Nullable
    @JsonProperty("server_action")
    private JsonNode serverAction;
}
package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.AppInfoDto;
import ru.smartapp.core.common.dto.CharacterDto;
import ru.smartapp.core.common.dto.DeviceDto;

@Getter
@Setter
public class ServerActionPayloadDto implements Payload {
    @JsonProperty("device")
    private DeviceDto device;
    @JsonProperty("app_info")
    private AppInfoDto appInfo;
    /**
     * Имя смартапа, которое задается при создании проекта и отображается в каталоге приложений.
     */
    @Nullable
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("character")
    private CharacterDto character;
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

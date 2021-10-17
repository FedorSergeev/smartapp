package ru.smartapp.core.common.dto.outgoing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import ru.smartapp.core.common.dto.DeviceDto;

@Getter
@Setter
public class PolicyRunAppPayloadDto {
    /**
     * Имя смартапа, которое задается при создании проекта и отображается в каталоге приложений.
     */
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("device")
    private DeviceDto device;
    /**
     * Информация о запускаемом смартапе и параметрах его запуска
     */
    // TODO: должен включать AppInfoDTO
    @JsonProperty("server_action")
    private JsonNode serverAction;
}

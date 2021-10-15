package ru.smartapp.core.common.dto.outgoing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.DeviceDto;

@Getter
@Setter
public class ErrorPayloadDto {
    /**
     * Код ошибки
     */
    @JsonProperty("code")
    private Long code;
    /**
     * Описание ошибки.
     */
    @Nullable
    @JsonProperty("description")
    private String description;
    /**
     * Интент, который смартап получит в следующем ответе ассистента.
     */
    @Nullable
    @JsonProperty("intent")
    private String intent;
    @JsonProperty("device")
    private DeviceDto device;
}

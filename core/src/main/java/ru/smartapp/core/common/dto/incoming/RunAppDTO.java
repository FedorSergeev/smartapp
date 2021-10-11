package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.DeviceDTO;

/**
 * Сообщает о запуске смартапа. Приходит в бэкенд смартапа при передаче сообщения
 */
@Getter
@Setter
public class RunAppDTO extends AbstractIncomingMessage {
    @JsonProperty("payload")
    private RunAppPayloadDTO payload;

    @Override
    public @Nullable DeviceDTO getDevice() {
        return payload.getDevice();
    }
}

package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.DeviceDTO;

/**
 * Сообщает смартапу о действиях пользователя на фронтенде, а также фоновые действия полноэкранных приложений
 */
@Getter
@Setter
public class ServerActionDTO extends AbstractIncomingMessage {
    @JsonProperty("payload")
    private ServerActionPayloadDTO payload;

    @Override
    public @Nullable DeviceDTO getDevice() {
        return payload.getDevice();
    }
}

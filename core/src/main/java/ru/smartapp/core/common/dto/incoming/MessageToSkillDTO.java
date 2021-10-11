package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.DeviceDTO;

/**
 * Содержит сообщение для смартапа
 */
@Getter
@Setter
public class MessageToSkillDTO extends AbstractIncomingMessage {
    @JsonProperty("payload")
    private MessageToSkillPayloadDTO payload;

    @Override
    public @Nullable DeviceDTO getDevice() {
        return payload.getDevice();
    }
}

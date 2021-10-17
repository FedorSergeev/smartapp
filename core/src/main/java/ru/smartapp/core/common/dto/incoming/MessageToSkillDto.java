package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.CharacterDto;
import ru.smartapp.core.common.dto.DeviceDto;

/**
 * Содержит сообщение для смартапа
 */
@Getter
@Setter
public class MessageToSkillDto extends AbstractIncomingMessage<MessageToSkillPayloadDto> {
    @JsonProperty("payload")
    private MessageToSkillPayloadDto payload;

    @Override
    public @Nullable DeviceDto getDeviceDto() {
        return payload.getDevice();
    }

    @Nullable
    @Override
    public CharacterDto getCharacterDto() {
        return payload.getCharacter();
    }
}

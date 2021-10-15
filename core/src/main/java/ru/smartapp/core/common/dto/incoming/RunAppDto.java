package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.CharacterDto;
import ru.smartapp.core.common.dto.DeviceDto;

/**
 * Сообщает о запуске смартапа. Приходит в бэкенд смартапа при передаче сообщения
 */
@Getter
@Setter
public class RunAppDto extends AbstractIncomingMessage<RunAppPayloadDto> {
    @JsonProperty("payload")
    private RunAppPayloadDto payload;

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

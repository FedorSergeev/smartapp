package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.CharacterDto;
import ru.smartapp.core.common.dto.DeviceDto;

/**
 * Сообщает смартапу о действиях пользователя на фронтенде, а также фоновые действия полноэкранных приложений
 */
@Getter
@Setter
public class ServerActionDto extends AbstractIncomingMessage<ServerActionPayloadDto> {
    @JsonProperty("payload")
    private ServerActionPayloadDto payload;

    @Override
    public @Nullable DeviceDto getDeviceDto() {
        return payload.getDevice();
    }

    @Nullable
    @Override
    public CharacterDto getCharacterDto() {
        return payload.getCharacter();
    }

    @Override
    public void setPayload(ServerActionPayloadDto payload) {
        this.payload = payload;
    }
}

package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.CharacterDto;
import ru.smartapp.core.common.dto.DeviceDto;

/**
 * Когда пользователь произносит команду для остановки приложения,
 * Ассистент передаёт сообщение CLOSE_APP в текущий открытый смартап и закрывает его.
 * Ассистент не ждёт ответа от смартапа. Содержимое сообщения совпадает с содержимым payload сообщения MESSAGE_TO_SKILL.
 */
@Getter
@Setter
public class CloseAppDto extends AbstractIncomingMessage<CloseAppPayloadDto> {
    @JsonProperty("payload")
    private CloseAppPayloadDto payload;

    @Nullable
    @Override
    public DeviceDto getDeviceDto() {
        return payload.getDevice();
    }

    @Nullable
    @Override
    public CharacterDto getCharacterDto() {
        return payload.getCharacter();
    }
}

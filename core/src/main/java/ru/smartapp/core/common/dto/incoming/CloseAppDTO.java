package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.CharacterDTO;
import ru.smartapp.core.common.dto.DeviceDTO;

/**
 * Когда пользователь произносит команду для остановки приложения,
 * Ассистент передаёт сообщение CLOSE_APP в текущий открытый смартап и закрывает его.
 * Ассистент не ждёт ответа от смартапа. Содержимое сообщения совпадает с содержимым payload сообщения MESSAGE_TO_SKILL.
 */
@Getter
@Setter
public class CloseAppDTO extends AbstractIncomingMessage {
    @JsonProperty("payload")
    private CloseAppPayloadDTO payload;

    @Nullable
    @Override
    public DeviceDTO getDeviceDTO() {
        return payload.getDevice();
    }

    @Nullable
    @Override
    public CharacterDTO getCharacterDTO() {
        return payload.getCharacter();
    }
}

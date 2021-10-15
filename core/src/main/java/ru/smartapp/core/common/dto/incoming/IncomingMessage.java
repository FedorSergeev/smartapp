package ru.smartapp.core.common.dto.incoming;


import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.CharacterDto;
import ru.smartapp.core.common.dto.DeviceDto;
import ru.smartapp.core.common.dto.Message;

/**
 * Interface for incoming messages
 */
public interface IncomingMessage<T extends Payload> extends Message {
    T getPayload();

    void setPayload(T payload);

    @Nullable DeviceDto getDeviceDto();

    @Nullable CharacterDto getCharacterDto();
}

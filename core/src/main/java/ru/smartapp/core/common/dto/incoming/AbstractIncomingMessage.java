package ru.smartapp.core.common.dto.incoming;

import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.AbstractMessage;
import ru.smartapp.core.common.dto.CharacterDTO;
import ru.smartapp.core.common.dto.DeviceDTO;


public abstract class AbstractIncomingMessage<T extends Payload> extends AbstractMessage implements IncomingMessage<T> {
    // TODO: что-то смущает, похоже на питонский @property, что не очень гуд.
    //  Если объект обязателен для ответных сообщений, то почему бы не положить во всех входящий это поле выше уровнем
    @Nullable
    public abstract DeviceDTO getDeviceDTO();

    @Nullable
    public abstract CharacterDTO getCharacterDTO();
}

package ru.smartapp.core.common.dto.incoming;

import ru.smartapp.core.common.dto.Message;


/**
 * Interface for incoming messages
 */
public interface IncomingMessage<T extends Payload> extends Message {
    T getPayload();
    void setPayload(T payload);
}

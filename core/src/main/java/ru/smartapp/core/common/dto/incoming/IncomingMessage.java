package ru.smartapp.core.common.dto.incoming;


/**
 * Interface for incoming messages
 */
public interface IncomingMessage<T extends Payload> {
    T getPayload();
    void setPayload(T payload);
}

package ru.smartapp.core.common.dto.incoming;

import ru.smartapp.core.common.dto.AbstractMessage;


public abstract class AbstractIncomingMessage<T extends Payload> extends AbstractMessage implements IncomingMessage<T> {

}

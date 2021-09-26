package ru.smartapp.core.defaultanswers;

import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

public interface AnswerBuilder<T extends AbstractOutgoingMessage> {
    T run();
}

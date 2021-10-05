package ru.smartapp.core.answersbuilders;

import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

public interface AnswerBuilder {
    AbstractOutgoingMessage build();
}

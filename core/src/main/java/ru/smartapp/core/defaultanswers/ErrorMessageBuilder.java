package ru.smartapp.core.defaultanswers;

import ru.smartapp.core.common.dto.outgoing.ErrorDTO;

public class ErrorMessageBuilder implements AnswerBuilder<ErrorDTO> {
    @Override
    public ErrorDTO run() {
//        TODO implement
        if (true) {
            throw new RuntimeException("TODO: implement");
        }
        return null;
    }
}

package ru.smartapp.core.defaultanswers;

import ru.smartapp.core.common.dto.outgoing.NothingFoundDTO;

public class NothingFoundMessageBuilder implements AnswerBuilder<NothingFoundDTO> {
    @Override
    public NothingFoundDTO run() {
//        TODO implement
        if (true) {
            throw new RuntimeException("TODO: implement");
        }
        return null;
    }
}

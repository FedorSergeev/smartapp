package ru.smartapp.core.answersbuilders;

import ru.smartapp.core.common.dto.outgoing.ErrorDTO;

public class ErrorMessageBuilder implements AnswerBuilder {
    public ErrorDTO build() {
//        TODO implement
        if (true) {
            throw new RuntimeException("TODO: implement");
        }
        return null;
    }
//    @Override
//    public ErrorDTO build(Long code, String description, DeviceDTO device) {
//        ErrorDTO errorDTO = new ErrorDTO();
//        errorDTO.setCode(code);
//        errorDTO.setDescription(description);
//        errorDTO.setPayload(new ErrorPayloadDTO().set);
//    }
}

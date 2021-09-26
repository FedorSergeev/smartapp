package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.smartapp.core.common.dto.AbstractMessage;

@Getter
@Setter
public class MessageToSkillDTO extends AbstractIncomingMessage {
    @JsonProperty("payload")
    private MessageToSkillPayloadDTO payload;
}

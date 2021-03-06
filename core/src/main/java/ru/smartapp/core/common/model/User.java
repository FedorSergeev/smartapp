package ru.smartapp.core.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.UuidDto;
import ru.smartapp.core.common.dto.incoming.IncomingMessage;
import ru.smartapp.core.common.dto.incoming.Payload;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String userId;
    private String sub;
    private String userChannel;

    public<T extends Payload> User(@Nullable IncomingMessage<T> incomingMessage) {
        Optional<UuidDto> optionalUuidDTO = Optional.ofNullable(incomingMessage)
                .map(IncomingMessage::getUuidDTO);
        this.userId = optionalUuidDTO.map(UuidDto::getUserId).orElse(Strings.EMPTY);
        this.sub = optionalUuidDTO.map(UuidDto::getSub).orElse(Strings.EMPTY);
        this.userChannel = optionalUuidDTO.map(UuidDto::getUserChannel).orElse(Strings.EMPTY);
    }

    public String getUserUniqueId() {
        return String.format("%s_%s_%s", sub, userChannel, userId);
    }
}

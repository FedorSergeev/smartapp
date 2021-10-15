package ru.smartapp.core.common.dto;

public interface Message {
    Long getMessageId();
    void setMessageId(Long messageId);
    String getSessionId();
    void setSessionId(String sessionId);
    String getMessageName();
    void setMessageName(String messageName);
    UuidDto getUuidDTO();
    void setUuidDTO(UuidDto uuidDTO);
}

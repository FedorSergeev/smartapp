package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageToSkillMessageDTO implements IncomingMessage {
    @JsonProperty("original_text")
    private String originalText;
    @JsonProperty("asr_normalized_message")
    private String asrNormalizedMessage;
    @JsonProperty("normalized_text")
    private String normalizedText;
    @JsonProperty("entities")
    private JsonNode entities;
    @JsonProperty("tokenized_elements_list")
    private JsonNode tokenizedElementsList;
    @JsonProperty("original_message_name")
    private String originalMessageName;
    @JsonProperty("human_normalized_text")
    private String humanNormalizedText;
    @JsonProperty("human_normalized_text_with_anaphora")
    private String humanNormalizedTextWithAnaphora;
}

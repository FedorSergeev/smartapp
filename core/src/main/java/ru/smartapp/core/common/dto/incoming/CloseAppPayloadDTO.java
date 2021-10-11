package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.AppInfoDTO;
import ru.smartapp.core.common.dto.CharacterDTO;
import ru.smartapp.core.common.dto.DeviceDTO;

/**
 * cm {@link MessageToSkillPayloadDTO}
 */
@Getter
@Setter
public class CloseAppPayloadDTO {
    @JsonProperty("applicationId")
    private String applicationId;
    @JsonProperty("appversionId")
    private String appVersionId;
    @JsonProperty("app_info")
    private AppInfoDTO appInfo;
    @JsonProperty("intent")
    private String intent;
    @JsonProperty("original_intent")
    private String original_intent;
    @JsonProperty("intent_meta")
    private JsonNode intentMeta;
    @Nullable
    @JsonProperty("meta")
    private JsonNode meta;
    @Nullable
    @JsonProperty("projectName")
    private String projectName;
    @Nullable
    @JsonProperty("selected_item")
    private JsonNode selectedItem;
    @JsonProperty("device")
    private DeviceDTO device;
    @Nullable
    @JsonProperty("new_session")
    private Boolean newSession;
    @JsonProperty("character")
    private CharacterDTO character;
    @JsonProperty("strategies")
    private JsonNode strategies;
    @JsonProperty("annotations")
    private JsonNode annotations;
    @JsonProperty("message")
    private JsonNode message;
    @JsonProperty("client_profile")
    private JsonNode clientProfile;
    @JsonProperty("asr")
    private JsonNode asr;
}

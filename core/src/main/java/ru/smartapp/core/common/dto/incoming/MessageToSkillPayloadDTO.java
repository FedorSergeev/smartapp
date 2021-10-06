package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dto.AppInfoDTO;
import ru.smartapp.core.common.dto.CharacterDTO;
import ru.smartapp.core.common.dto.DeviceDTO;

@Getter
@Setter
public class MessageToSkillPayloadDTO {
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
    /**
     * Имя смартапа, которое задается при создании проекта и отображается в каталоге приложений.
     */
    @Nullable
    @JsonProperty("projectName")
    private String projectName;
    /**
     * Описание элемента экрана, который пользователь назвал при запросе ("включи второй" / "включи второго терминатора").
     * Для работы этой функциональности нужна отправка во входящем сообщении с фронтенда item_selector со списком элементов.
     * Объект передаётся всегда и может быть либо пустым, либо содержать все указанные поля.
     */
    @Nullable
    @JsonProperty("selected_item")
    private JsonNode selectedItem;
    @JsonProperty("device")
    private DeviceDTO device;
    /**
     * Указывает на характер запуска смартапа. Если поле содержит true, сессии присваивается новый идентификатор (поле sessionId).
     * Возможные значения:
     * - true — приложение запущено впервые или после закрытия приложения,
     * а так же при запуске приложения по истечению тайм-аута (10 минут)
     * или после прерывания работы приложения, например, по запросу "текущее время"i>
     * <p>
     * - false — во всех остальных случаях..
     * По умолчанию: false.
     */
    @Nullable
    @JsonProperty("new_session")
    private Boolean newSession;
    @JsonProperty("character")
    private CharacterDTO character;
    /**
     * Возможные стратегии смартапа.
     */
    @JsonProperty("strategies")
    private JsonNode strategies;
    /**
     * Общие характеристики сообщения пользователя.
     */
    @JsonProperty("annotations")
    private JsonNode annotations;
    /**
     * Результат предобработки.
     */
    @JsonProperty("message")
    private MessageToSkillMessageDTO message;
    @JsonProperty("client_profile")
    private JsonNode clientProfile;
    @JsonProperty("asr")
    private JsonNode asr;
}

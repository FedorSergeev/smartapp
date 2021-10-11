package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

/**
 * Результат предобработки.
 */
@Getter
@Setter
public class MessageToSkillMessageDTO {
    /**
     * Исходное сообщение пользователя: распознанный голос или введенный текст.
     * В случае распознанного голоса предоставляется текст запроса без нормализации числительных и другого,
     * соответственно, все числа, номера телефонов и тд представлены словами.
     * <p>
     * Пример: "хочу заказать пиццу на девять вечера за пятьсот рублей".
     */
    @JsonProperty("original_text")
    private String originalText;
    /**
     * Отображаемый на экране текст запроса / нормализованный на этапе ASR запрос.
     * <p>
     * Пример: "Хочу заказать пиццу на 9 вечера за 500 ₽".
     */
    @JsonProperty("asr_normalized_message")
    private String asrNormalizedMessage;
    /**
     * Нормализованный текст, который ввел пользователь. Можно использовать для снижения многообразия запросов, например, для аналитики.
     * <p>
     * Пример: хотеть заказать пицца на TIME_TIME_TOKEN за MONEY_TOKEN .
     */
    @JsonProperty("normalized_text")
    private String normalizedText;
    /**
     * Извлеченные из запроса сущности.
     */
    @JsonProperty("entities")
    private JsonNode entities;
    /**
     * Список токенов в запросе пользователя. Содержит грамматический и синтаксический разбор,
     * а также привязку к сущностям и их нормализованным значениям для каждого токена.
     */
    @JsonProperty("tokenized_elements_list")
    private JsonNode tokenizedElementsList;
    @JsonProperty("original_message_name")
    private String originalMessageName;
    @JsonProperty("human_normalized_text")
    private String humanNormalizedText;
    @JsonProperty("human_normalized_text_with_anaphora")
    private String humanNormalizedTextWithAnaphora;
}

package ru.smartapp.core.answersbuilders.sdkanswerbuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

public class SdkAnswerBuilder {
    public static final String SDK_ANSWER_TEMPLATE_FIELD_NAME = "sdk_answer_template";
    public static final String MULTILINE_CARD_TEMPLATE_FIELD_NAME = "multiline_card";
    public static final String BUBBLE_TEMPLATE_FIELD_NAME = "bubble_template";
    public static final String SUGGESTION_TEXT_TEMPLATE_FIELD_NAME = "suggestion_text_template";
    public static final String SUGGESTION_DEEPLINK_TEMPLATE_FIELD_NAME = "suggestion_deeplink_template";

    private ObjectMapper mapper;
    private ObjectNode sdkAnswerTemplate;
    private ObjectNode multilineCardTemplate;
    private ObjectNode bubbleTemplate;
    private ObjectNode suggestionTextTemplate;
    private ObjectNode suggestionDeeplinkTemplate;

    public SdkAnswerBuilder(ObjectNode answerTemplates, ObjectMapper mapper) {
        this.mapper = mapper;
        this.sdkAnswerTemplate = (ObjectNode) answerTemplates.get(SDK_ANSWER_TEMPLATE_FIELD_NAME);
        this.multilineCardTemplate = (ObjectNode) answerTemplates.get(MULTILINE_CARD_TEMPLATE_FIELD_NAME);
        this.bubbleTemplate = (ObjectNode) answerTemplates.get(BUBBLE_TEMPLATE_FIELD_NAME);
        this.suggestionTextTemplate = (ObjectNode) answerTemplates.get(SUGGESTION_TEXT_TEMPLATE_FIELD_NAME);
        this.suggestionDeeplinkTemplate = (ObjectNode) answerTemplates.get(SUGGESTION_DEEPLINK_TEMPLATE_FIELD_NAME);

    }

    public SdkAnswerBuilder addVoice(String text) {
        sdkAnswerTemplate.put("pronounceText", text);
        return this;
    }

    /**
     * В тексте можно использовать SSML-разметку
     * Например,
     * С помощью <say-as interpret-as="spell-out">SSML</say-as> разметки я умею делать <break time="2s" /> паузы,
     * <break /> произносить слова по <say-as interpret-as="characters">буквам</say-as> и многое другое.
     */
    public SdkAnswerBuilder useSsmlVoice() {
        sdkAnswerTemplate.put("pronounceTextType", "application/ssml");
        return this;
    }

    /**
     * Возможно передать список фраз, которые нужны отрисовать в отдельных баблах/сообщениях
     *
     * @param texts список фраз или одна фраза
     */
    public SdkAnswerBuilder addTexts(List<String> texts) {
        for (String text : texts) {
            addText(text);
        }
        return this;
    }

    public SdkAnswerBuilder addText(String text) {
        if (Strings.isNotEmpty(text)) {
            ObjectNode node = bubbleTemplate.deepCopy();
            ((ObjectNode) (node.get("bubble"))).put("text", text);
            sdkAnswerTemplate.withArray("items").add(node);
        }
        return this;
    }

    public SdkAnswerBuilder addButtonWithDeeplink(String text, String deeplink) throws JsonProcessingException {
        ObjectNode card = multilineCardTemplate.deepCopy();
        ((ObjectNode) (card.get("card").withArray("cells").get(0).get("left").get("title"))).put("text", text);
        JsonNode deeplinkNode = mapper.readTree(String.format("{\"type\":\"deep_link\", \"deep_link\":\"%s\"}", deeplink));
        ((ObjectNode) (card.get("card").withArray("cells").get(0))).set("actions", deeplinkNode);
        sdkAnswerTemplate.withArray("items").add(card);
        return this;
    }

    public SdkAnswerBuilder addButtonWithText(String text) {
        ObjectNode card = multilineCardTemplate.deepCopy();
        ((ObjectNode) (card.get("card").withArray("cells").get(0).get("left").get("title"))).put("text", text);
        ((ObjectNode) (card.get("card").get("cells").get(0).get("actions").get(0))).put("text", text);
        sdkAnswerTemplate.withArray("items").add(card);
        return this;
    }

    public SdkAnswerBuilder addSuggestionText(String text) {
        ObjectNode node = this.suggestionTextTemplate.deepCopy();
        node.put("title", text);
        ((ObjectNode) (node.get("action"))).put("text", text);
        ((ArrayNode) (sdkAnswerTemplate.get("suggestions").withArray("buttons"))).add(node);
        return this;
    }

    public SdkAnswerBuilder addSuggestionDeeplink(String text, String deeplink) {
        ObjectNode node = this.suggestionDeeplinkTemplate.deepCopy();
        node.put("title", text);
        ((ObjectNode) (node.get("action"))).put("deeplink", deeplink);
        ((ArrayNode) (sdkAnswerTemplate.get("suggestions").withArray("buttons"))).add(node);
        return this;
    }

    public SdkAnswerBuilder addCard(JsonNode card) {
        sdkAnswerTemplate.withArray("items").add(card.deepCopy());
        return this;
    }

    public SdkAnswerBuilder finished() {
        sdkAnswerTemplate.put("finished", true);
        return this;
    }

    public SdkAnswerBuilder notFinished() {
        sdkAnswerTemplate.put("finished", false);
        return this;
    }

    public ObjectNode getJson() {
        return sdkAnswerTemplate;
    }

}

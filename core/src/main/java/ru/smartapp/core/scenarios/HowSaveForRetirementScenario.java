package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smartapp.core.ScenariosTextsService;
import ru.smartapp.core.annotations.ScenarioClass;
import ru.smartapp.core.answersbuilders.AnswerToUserMessageBuilder;
import ru.smartapp.core.answersbuilders.sdkanswerbuilder.SdkAnswerBuilder;
import ru.smartapp.core.answersbuilders.sdkanswerbuilder.SdkAnswerService;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

@Slf4j
@Service
@ScenarioClass("how_to_save_for_retirement")
public class HowSaveForRetirementScenario implements Scenario {
    private ObjectMapper mapper;
    private SdkAnswerService sdkAnswerService;
    private ScenariosTextsService scenariosTextsService;

    @Autowired
    public HowSaveForRetirementScenario(
            ObjectMapper mapper,
            SdkAnswerService sdkAnswerService,
            ScenariosTextsService scenariosTextsService
    ) {
        this.mapper = mapper;
        this.sdkAnswerService = sdkAnswerService;
        this.scenariosTextsService = scenariosTextsService;
    }

    @Override
    public AbstractOutgoingMessage run(ScenarioContext<? extends AbstractIncomingMessage> context) throws JsonProcessingException {
        JsonNode answerJsonNode = scenariosTextsService.getTextMap().get("how_to_save_for_retirement").get("answer_no_client_profile");
        String stateId = context.getStateId();
        if (stateId == null) {
            return bye(context);
        }
        return bye(context);
    }

    private AbstractOutgoingMessage hello(ScenarioContext<? extends AbstractIncomingMessage> context) throws JsonProcessingException {
        AbstractIncomingMessage incomingMessage = context.getMessage();
        SdkAnswerBuilder answerBuilder = sdkAnswerService.getSdkAnswerBuilder();
        answerBuilder
                .addText("Hello from java app")
                .addVoice("Хелло фром джава апп")
                .addButtonWithText("Не нажимай")
                .addSuggestionText("Что ты умеешь?")
                .notFinished();
        context.setStateId("bye");
        return new AnswerToUserMessageBuilder().build(answerBuilder, incomingMessage);
    }

    private AbstractOutgoingMessage bye(ScenarioContext<? extends AbstractIncomingMessage> context) throws JsonProcessingException {
        AbstractIncomingMessage incomingMessage = context.getMessage();
        SdkAnswerBuilder answerBuilder = sdkAnswerService.getSdkAnswerBuilder();
        answerBuilder
                .addText("Упс, ошибочка вышла.")
                .addVoice("<audio text=\"упс\"/>, ошибочка вышла.")
                .useSsmlVoice()
                .finished();
        return new AnswerToUserMessageBuilder().build(answerBuilder, incomingMessage);
    }
}

class ScenarioTexts {

}

package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.smartapp.core.annotations.ScenarioClass;
import ru.smartapp.core.answersbuilders.AnswerToUserMessageBuilder;
import ru.smartapp.core.answersbuilders.sdkanswerbuilder.SdkAnswerBuilder;
import ru.smartapp.core.answersbuilders.sdkanswerbuilder.SdkAnswerService;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

@Slf4j
@Service
@ScenarioClass("run_app")
public class SomeDumbScenario implements Scenario {
    private final ObjectMapper mapper;
    private final SdkAnswerService sdkAnswerService;

    @Autowired
    public SomeDumbScenario(ObjectMapper mapper, SdkAnswerService sdkAnswerService) {
        this.mapper = mapper;
        this.sdkAnswerService = sdkAnswerService;
    }

    @Override
    public Mono<AbstractOutgoingMessage> run(ScenarioContext context) throws JsonProcessingException {
        String stateId = context.getStateId();
        if (stateId == null) {
            return hello(context);
        }
        return bye(context);
    }

    private Mono<AbstractOutgoingMessage> hello(ScenarioContext context) throws JsonProcessingException {
        AbstractIncomingMessage incomingMessage = context.getMessage();
        SdkAnswerBuilder answerBuilder = sdkAnswerService.getSdkAnswerBuilder();
        answerBuilder
                .addText("Hello from java app")
                .addVoice("Хелло фром джава апп")
                .addButtonWithText("Не нажимай")
                .addSuggestionText("Что ты умеешь?")
                .notFinished();
        context.setStateId("bye");
        return Mono.just(new AnswerToUserMessageBuilder().build(answerBuilder, incomingMessage));
    }

    private Mono<AbstractOutgoingMessage> bye(ScenarioContext context) throws JsonProcessingException {
        AbstractIncomingMessage incomingMessage = context.getMessage();
        SdkAnswerBuilder answerBuilder = sdkAnswerService.getSdkAnswerBuilder();
        answerBuilder
                .addText("Упс, ошибочка вышла.")
                .addVoice("<audio text=\"упс\"/>, ошибочка вышла.")
                .useSsmlVoice()
                .finished();
        return Mono.just(new AnswerToUserMessageBuilder().build(answerBuilder, incomingMessage));
    }
}

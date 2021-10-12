package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.smartapp.core.AppConfigsService;
import ru.smartapp.core.ScenariosTextsService;
import ru.smartapp.core.annotations.ScenarioClass;
import ru.smartapp.core.answersbuilders.AnswerToUserMessageBuilder;
import ru.smartapp.core.answersbuilders.NothingFoundMessageBuilder;
import ru.smartapp.core.answersbuilders.sdkanswerbuilder.SdkAnswerBuilder;
import ru.smartapp.core.answersbuilders.sdkanswerbuilder.SdkAnswerService;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.integrationdto.ServiceConfig;
import ru.smartapp.core.common.model.ScenarioContext;

@Slf4j
@Service
@ScenarioClass("how_to_save_for_retirement")
public class HowSaveForRetirementScenario implements Scenario {
    private final WebClient webClient;
    private final SdkAnswerService sdkAnswerService;
    private final ScenariosTextsService scenariosTextsService;
    private final AppConfigsService appConfigsService;

    @Autowired
    public HowSaveForRetirementScenario(
            WebClient webClient,
            SdkAnswerService sdkAnswerService,
            ScenariosTextsService scenariosTextsService,
            AppConfigsService appConfigsService
    ) {
        this.webClient = webClient;
        this.sdkAnswerService = sdkAnswerService;
        this.scenariosTextsService = scenariosTextsService;
        this.appConfigsService = appConfigsService;
    }

    @Override
    public Mono<AbstractOutgoingMessage> run(ScenarioContext context) throws JsonProcessingException {
        ServiceConfig serviceConfig = appConfigsService.getIntegrationAppConfig().getServices().getServiceOne();
        // TODO: how use without creating new WebClient on different hosts
        WebClient webClient = WebClient.create(serviceConfig.getHost());
        // TODO: timeout is not set
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.port(serviceConfig.getPort()).path(serviceConfig.getEndpoint()).build())
                .exchangeToMono(clientResponse -> {
                    log.info(String.format("Got response with status '%s' and body '%s'",
                            clientResponse.rawStatusCode(), clientResponse.bodyToMono(String.class)));
                    JsonNode answerJsonNode =
                            scenariosTextsService.getTextMap()
                                    .get("how_to_save_for_retirement")
                                    .get("answer_no_client_profile");
                    JsonNode sberAnswer = answerJsonNode.withArray("sber").get(0);
                    String text = sberAnswer.get("text").asText();
                    String voice = sberAnswer.get("voice").asText();
                    AbstractIncomingMessage incomingMessage = context.getMessage();
                    SdkAnswerBuilder answerBuilder = sdkAnswerService.getSdkAnswerBuilder();
                    answerBuilder
                            .addText(text)
                            .addVoice(voice)
                            .addButtonWithText("Не нажимай")
                            .addSuggestionText("Что ты умеешь?")
                            .finished();
                    try {
                        return Mono.just(new AnswerToUserMessageBuilder().build(answerBuilder, incomingMessage));
                    } catch (JsonProcessingException e) {
                        log.error("Exception on json processing", e);
                    }
                    return Mono.just(new NothingFoundMessageBuilder().build(incomingMessage));
                });

    }
}

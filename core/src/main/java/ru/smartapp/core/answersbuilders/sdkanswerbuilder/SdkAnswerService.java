package ru.smartapp.core.answersbuilders.sdkanswerbuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class SdkAnswerService {

    @Value("classpath:sdk_answer_templates.json")
    private Resource sdkAnswerTemplateResource;
    private final ObjectMapper mapper;
    private ObjectNode answerTemplates;

    @Autowired
    public SdkAnswerService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    private void postConstruct() throws IOException {
        this.answerTemplates = (ObjectNode) mapper.readTree(sdkAnswerTemplateResource.getInputStream());
    }

    public SdkAnswerBuilder getSdkAnswerBuilder() {
        return new SdkAnswerBuilder(answerTemplates.deepCopy(), mapper);
    }
}

package ru.smartapp.core.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.smartapp.core.common.SdkAnswerBuilder;
import ru.smartapp.core.common.SdkAnswerService;

@SpringBootTest
public class SdkAnswerBuilderTest {
    @Autowired
    private SdkAnswerService sdkAnswerService;

    @Test
    //TODO test all SdkAnswerBuilder methods
    public void test() {
        SdkAnswerBuilder sdkAnswerBuilder = sdkAnswerService.getSdkAnswerBuilder();
    }
}

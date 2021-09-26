package ru.smartapp.core.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO test all SdkAnswerBuilder methods
@SpringBootTest
public class SdkAnswerServiceTest {
    @Autowired
    private SdkAnswerService sdkAnswerService;

    @Test
    public void testTemplateLoading() throws IllegalAccessException {
        assertNotNull(sdkAnswerService.getAnswerTemplates());
        assertTrue(sdkAnswerService.getAnswerTemplates().size() > 0);
        Field[] fields = SdkAnswerBuilder.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAccessible()) {
                String fieldValue = (String) field.get(null);
                assertTrue(sdkAnswerService.getAnswerTemplates().has(fieldValue));
                assertNotNull(sdkAnswerService.getAnswerTemplates().get(fieldValue));
            }
        }
    }
}

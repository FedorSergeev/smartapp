package ru.smartapp.core.answersbuilders.sdkanswerbuilder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SdkAnswerServiceTest {
    @Autowired
    private SdkAnswerService sdkAnswerService;

    @Test
    public void testTemplateLoading() throws IllegalAccessException {
        Field[] fields1 = sdkAnswerService.getClass().getDeclaredFields();
        Field answerTemplatesField = Arrays.stream(fields1)
                .filter(field -> field.getName().equals("answerTemplates"))
                .findAny()
                .orElse(null);
        assertNotNull(answerTemplatesField);
        answerTemplatesField.setAccessible(true);
        ObjectNode answerTemplates = (ObjectNode) answerTemplatesField.get(sdkAnswerService);
        assertNotNull(answerTemplates);
        assertTrue(answerTemplates.size() > 0);
        Field[] fields = SdkAnswerBuilder.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAccessible()) {
                String fieldValue = (String) field.get(null);
                assertTrue(answerTemplates.has(fieldValue));
                assertNotNull(answerTemplates.get(fieldValue));
            }
        }
    }
}

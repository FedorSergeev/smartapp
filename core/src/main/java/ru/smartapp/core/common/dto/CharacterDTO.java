package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Информация о текущем персонаже ассистента, который установлен у пользователя.
 */
@Getter
@Setter
public class CharacterDTO {
    /**
     * Идентификатор персонажа, которого выбрал пользователь.
     * <p>
     * Возможные значения:
     * <p>
     * sber — персонаж мужского пола по имени Сбер. Обращается на "вы".
     * [athena|eva] — персонаж женского пола по имени Афина. Обращается на "вы".
     * joy — персонаж женского пола по имени Джой.  Обращается на "ты".
     * Учитывайте пол персонажа (поле gender) и форму обращения (поле appeal) при проектировании ответов.
     */
    @JsonProperty("id")
    private String id;
    /**
     * Имя персонажа.
     * <p>
     * Возможные значения:
     * <p>
     * Сбер;
     * Афина;
     * Джой.
     */
    @JsonProperty("name")
    private String name;
    /**
     * Пол персонажа. Учитывайте пол персонажа при проектировании ответов.
     * <p>
     * Возможные значения:
     * <p>
     * male;
     * female.
     */
    @JsonProperty("gender")
    private String gender;
    /**
     * Форма обращения персонажа. Учитывайте форму обращения персонажа при проектировании ответов.
     * <p>
     * Возможные значения:
     * <p>
     * official — персонаж обращается на "вы".
     * no_official — персонаж обращается на "ты".
     */
    @JsonProperty("appeal")
    private String appeal;
}

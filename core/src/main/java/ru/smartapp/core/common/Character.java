package ru.smartapp.core.common;

import java.util.Arrays;

public enum Character {
    SBER("sber", "Сбер", "male", "official"),
    APHENA("eva", "Афина", "female", "official"),
    //"athena" - используется для сценариев на смартмаркете
    APHENA_SM("athena", "Афина", "female", "official"),
    JOY("joy", "Джой", "female", "no_official");

    private final String id;
    private final String name;
    private final String gender;
    private final String appeal;

    Character(String id, String name, String gender, String appeal) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.appeal = appeal;
    }

    public static Character valueById(String id) {
        return Arrays.stream(Character.values()).filter(ch -> ch.getId().equals(id)).findAny().orElse(Character.SBER);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAppeal() {
        return appeal;
    }
}

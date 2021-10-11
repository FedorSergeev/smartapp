package ru.smartapp.core.common;

public enum MessageName {
    // region incoming message names
    MESSAGE_TO_SKILL,
    SERVER_ACTION,
    RUN_APP,
    CLOSE_APP,
    // endregion incoming message names
    // region outgoing message names
    ANSWER_TO_USER,
    POLICY_RUN_APP,
    NOTHING_FOUND,
    ERROR
    // endregion outgoing message names
}

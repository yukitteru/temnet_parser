package com.temnet.parser.domain;

public enum Status {
    in_progress("ЗАЯВКА В РАБОТЕ"),
    rejected("ЗАЯВКА ОТКЛОНЕНА"),
    close("ЗАКРЫТА ЗАЯВКА");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

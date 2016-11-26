package com.dvelopp.functional.utils;

import java.util.Objects;

import static java.util.UUID.randomUUID;

public class SubTestObject {

    private String id = randomUUID().toString();

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTestObject that = (SubTestObject) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}

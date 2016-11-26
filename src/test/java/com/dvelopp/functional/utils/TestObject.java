package com.dvelopp.functional.utils;

import java.util.Objects;

import static java.util.UUID.randomUUID;

public class TestObject {

    private String id = randomUUID().toString();

    private SubTestObject subTestObject;

    /**
     * State field. Can be changed.
     */
    private boolean active = true;

    public TestObject(SubTestObject subTestObject) {
        this.subTestObject = subTestObject;
    }

    public SubTestObject getSubTestObject() {
        return subTestObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestObject that = (TestObject) o;
        return isActive() == that.isActive() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getSubTestObject(), that.getSubTestObject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubTestObject(), isActive());
    }
}

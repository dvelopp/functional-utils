package com.dvelopp.functional.utils;

/**
 * Class is used to stub method calls and add explicitness into the test code when the real implementation of methods
 * is not important.
 */
public class Testee {

    public void voidMethod() {
    }

    public <T> T supplierMethod() {
        return null;
    }

    public <T> void consumerMethod(T object) {
    }

    public <T> T identityMethod(T object) {
        return object;
    }

}

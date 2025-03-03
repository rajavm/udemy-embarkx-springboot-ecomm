package com.ecommerce.project.exceptions;

public class APIException extends RuntimeException{

    /*
    serialVersionUID should be unique and generated by IDE or serialver tool as a best practise.
    used to provide a unique identifier for the version of the class.
    This is important for serialization and deserialization processes in Java.
    By defining this serialVersionUID, you ensure that the class structure remains consistent across different versions of the class,
    preventing any issues during serialization and deserialization operations.
    It helps in maintaining compatibility and ensuring that the class can be serialized and deserialized correctly.
     */
    //not best practice but understand why this is used
    private static final long  serialVersionUID=1L;

    private String message;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bern.norstienpaymentsapi;

/**
 *
 * @author unknown
 */
public class InvalidPayloadException extends RuntimeException {

    public static final String DEFAULT_MESSAGE_BODY = "Payload Must Contain Key";

    public InvalidPayloadException(String message) {
        super(message);
    }

    /* public InvalidPayloadException(String[] keys) {
    String message = "";
    
    if (keys.length > 1) {
    message += "s ";
    for (int i = 0; i < keys.length; i++) {
    if (i == keys.length - 1) {
    message += "and/or " + keys[i];
    }else{
    message += keys[i] + ", ";
    }
    
    }
    }
    else{
    message += keys[0];
    }
    super (DEFAULT_MESSAGE_BODY + message);
    }*/

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author unknown
 */
@Component
public class PropertyRecordsRoute extends RouteBuilder {

    @Value("${pathToCsvFile}")
    String pathToFile;


    @Override
    public void configure() throws Exception {


        String filePath = assembleCamelFilePath();

        errorHandler(deadLetterChannel("file:" + filePath + ".errors&fileExist=append&appendChars=\\"));

        from("file:" + filePath + "&readLock=changed")
                .routeId("Import Property Data Route")
                .log("New File Detected")
                .split(body().tokenize("\n", 1, true))
                .bean(CamelDataReader.class, "process")
                .to("log:newPropertyLogger");
    }

    private String assembleCamelFilePath() {
        String newString = new String();
        for (int i = 0; i < pathToFile.length(); i++) {

            newString += pathToFile.charAt(i);

            if (i == pathToFile.lastIndexOf("/")) {
                newString += "?fileName=";
            }
        }

        return newString;
    }

 

}

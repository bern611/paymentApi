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

    //@Value("${csvFileName}")
    String csvFileName = null;

    @Override
    public void configure() throws Exception {
        //"/home/unknown/Downloads/?fileName=propertyApi.csv"

        String filePath = assembleCamelFilePath();

        errorHandler(deadLetterChannel("file:" + filePath + ".errors&fileExist=append&appendChars=\\"));

        from("file:" + filePath + "&readLock=changed")
                .routeId("Import Property Data Route")
                .log("New File Detected")
                .split(body().tokenize("\n", 1, true))
                .bean(CamelDataReader.class, "process")
                .to("log:newPropertyLogger");
        /*from("timer:daTimer")
        .bean(CamelDataReader.class,"hello");*/
    }

    private String assembleCamelFilePath() {
        if (csvFileName == null) {
            return spliceString(pathToFile.lastIndexOf('/'));
        }

        if (pathToFile != null && pathToFile.endsWith("/")) {

            String filePath = pathToFile + "?fileName=" + csvFileName;

            System.out.println(filePath);

            return filePath;
        } else {
            throw new RuntimeException("pathToCsvFile must be specified");
        }

    }

    private String spliceString(int index) {
        String newString = new String();

        for (int i = 0; i < pathToFile.length(); i++) {

            newString += pathToFile.charAt(i);

            if (i == index) {

                newString += "?fileName=";
            }
        }

        return newString;
    }

}

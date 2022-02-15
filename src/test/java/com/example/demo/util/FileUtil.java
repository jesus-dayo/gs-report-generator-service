package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FileUtil {

    public String readResourceFileAsString(String filename) {
        try {
            InputStream inputStream = FileUtil.class.getClassLoader().getResourceAsStream(filename);
            if (inputStream == null) {
                log.warn("cannot find file with name: " + filename);
                return null;
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Fail to find the file", e);
            return null;
        }
    }
}

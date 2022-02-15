package com.example.demo.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilTest {

    @Test
    public void readResourceFile_whenValidFile_returnsContentAsString() {
        FileUtil fileUtil = new FileUtil();
        String filename = "data/sample.txt";
        String content = fileUtil.readResourceFileAsString(filename);
        assertNotNull(content);
        assertEquals("hello world", content);
    }

    @Test
    public void readResourceFile_whenInvalidFile_returnsNull() {
        FileUtil fileUtil = new FileUtil();
        String filename = "data/not_found.txt";
        String content = fileUtil.readResourceFileAsString(filename);
        assertNull(content);
    }

}

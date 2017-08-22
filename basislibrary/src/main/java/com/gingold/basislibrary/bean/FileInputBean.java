package com.gingold.basislibrary.bean;

import java.io.File;

/**
 *
 */

public class FileInputBean {
    public String key;
    public String fileName;
    public File file;

    public FileInputBean(String key, String fileName, File file) {
        this.key = key;
        this.fileName = fileName;
        this.file = file;
    }
}

package com.gingold.basislibrary.bean;

import java.io.File;

/**
 * 文件上传信息bean
 */

public class BasisFileInputBean {
    public String key;
    public String fileName;
    public File file;

    public BasisFileInputBean(String key, String fileName, File file) {
        this.key = key;
        this.fileName = fileName;
        this.file = file;
    }
}

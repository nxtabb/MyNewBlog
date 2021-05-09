package com.hrbeu.pojo.vo;

import com.hrbeu.pojo.File;

import java.io.Serializable;

public class File_Len implements Serializable {
    private File file;
    private Double length;
    public File_Len(){}

    public Double getLength() {
        return length;
    }

    public File getFile() {
        return file;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public void setFile(File file) {
        this.file = file;
    }
    public File_Len(File file,Double length){
        this.file = file;
        this.length = length;
    }
}

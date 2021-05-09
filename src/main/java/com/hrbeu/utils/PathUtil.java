package com.hrbeu.utils;

import java.io.File;

public class PathUtil {
    //获取待存储图片的位置
    public static String getBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if(os.toLowerCase().startsWith("win")){
            basePath = "F:/codes";
        }
        else {
            basePath = "/home/ningxitong";
        }
        basePath = basePath.replace("/", File.separator);
        return basePath;
    }

    public static String getUserPath(String username,String documentTitle){
        String userPath = "/"+username+"/"+documentTitle;
        userPath = userPath.replace("/",File.separator);
        return userPath;
    }


    //创建目标路径所涉及的目录
    public static void mkDirPath(String fileRealPath) {
        //获取该路径的文件对象
        File dirPath = new File(fileRealPath);
        //如果不存在
        if(!dirPath.exists()){
            //递归创建
            dirPath.mkdirs();
        }
    }

    //获取输入文件流的扩展名
    public static String getFileExtension(String fileName) {
        //根据最后一个.截取文件名字得到扩展名
        return fileName.substring(fileName.lastIndexOf("."));
    }


    public static void delFileOrPath(String storePath){
        //判断storePath是文件路径还是文件夹路径，如果是文件路径，则删除该文件，如果是文件夹，则删除下面的所有文件
        File fileOrPath = new File(PathUtil.getBasePath()+storePath);
        //判断是否存在
        if(fileOrPath.exists()){
            //判断是否是文件夹
            if(fileOrPath.isDirectory()){
                //获取文件夹下的所有文件
                File[] files = fileOrPath.listFiles();
                //遍历删除
                for(File f:files){
                    f.delete();
                }
            }
            //最后删除该文件夹或文件。
            fileOrPath.delete();
        }
    }
}


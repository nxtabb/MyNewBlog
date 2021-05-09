package com.hrbeu.utils;

import com.hrbeu.pojo.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUploadUtil {
    public static Map<String,List<String>> fileUpload(HttpServletRequest request, String documentTitle, String nowTimeStr) throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        Map<String, List<String>> fileInfo = new HashMap<>();
        List<String> fileNameList = new ArrayList<>();
        List<String> filePathList= new ArrayList<>();
        List<String> fileOriginNameList = new ArrayList<>();
        if (resolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> fileList = multipartHttpServletRequest.getFiles("codefile");
            for(MultipartFile file:fileList){
                String fileName = file.getOriginalFilename();
                if (fileName != null&&!fileName.equals("")) {
                    String fileWithoutExtensionName = fileName.substring(0,fileName.lastIndexOf("."));
                    String extendFileName = PathUtil.getFileExtension(fileName);
                    String basePath = PathUtil.getBasePath();
                    String username = ((User) request.getSession().getAttribute("user")).getUsername();
                    String savePath = basePath + PathUtil.getUserPath(username, documentTitle);
                    PathUtil.mkDirPath(savePath);
                    String filePath = savePath + File.separator + fileWithoutExtensionName+nowTimeStr+extendFileName;
                    OutputStream os = new FileOutputStream(filePath);
                    InputStream ins = file.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(ins);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(os);
                    byte[] bytes = new byte[1024];
                    int temp;
                    while ((temp = bufferedInputStream.read(bytes)) != -1) {
                        bufferedOutputStream.write(bytes, 0, temp);
                    }
                    fileNameList.add(fileWithoutExtensionName+nowTimeStr+extendFileName);
                    filePathList.add(PathUtil.getUserPath(username, documentTitle)+File.separator+fileWithoutExtensionName+nowTimeStr+extendFileName);
                    fileOriginNameList.add(fileName);
                    bufferedInputStream.close();
                    bufferedOutputStream.close();
                    os.close();
                    ins.close();
                }
            }
            fileInfo.put("fileNameList",fileNameList);
            fileInfo.put("filePathList",filePathList);
            fileInfo.put("fileOriginNameList",fileOriginNameList);
            return fileInfo;
        }
        else {
            return null;
        }
    }
}

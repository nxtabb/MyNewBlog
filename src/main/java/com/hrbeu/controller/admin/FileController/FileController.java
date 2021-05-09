package com.hrbeu.controller.admin.FileController;


import com.hrbeu.pojo.File;
import com.hrbeu.service.admin.DocumentService;
import com.hrbeu.service.admin.FileService;
import com.hrbeu.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RequestMapping("/admin")
@Controller
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private DocumentService documentService;
    @RequestMapping("/files/onlydeleteFile/{documentId}/{fileId}")
    public String onlyDeleteFile(@PathVariable("documentId")Long documentId,@PathVariable("fileId")Long fileId, HttpServletRequest request){
        fileService.onlydeleteFile(fileId);
        return "redirect:/admin/documents/updatedocument/"+documentId;

    }

    @GetMapping("/files/downloadFile/{fileId}")
    public void download(@PathVariable("fileId")Long fileId, HttpServletResponse response) throws IOException {
        File file = fileService.queryFile(fileId);
        String filePath = PathUtil.getBasePath()+file.getFilePath();
        java.io.File file_download = new java.io.File(filePath);
        if(!file_download.exists()){
            return;
        }
        else {
            String fileName = file.getFileOriginName();
            fileName = URLEncoder.encode(fileName,"UTF-8");
            response.addHeader("Content-Disposition","attachment;filename=" + fileName);
            response.setContentType("multipart/form-data");
            response.setContentLength((int)file_download.length());
            FileInputStream inputStream = new FileInputStream(filePath);
            OutputStream outputStream = response.getOutputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer))!=-1){
                bufferedOutputStream.write(buffer,0,len);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
            inputStream.close();
            outputStream.close();
        }
    }
}

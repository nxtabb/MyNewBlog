package com.hrbeu.controller.admin.DocumentController;

import com.hrbeu.pojo.*;
import com.hrbeu.pojo.vo.File_Len;
import com.hrbeu.service.CommentService;
import com.hrbeu.service.admin.*;
import com.hrbeu.utils.FileUploadUtil;
import com.hrbeu.utils.PageUtil;
import com.hrbeu.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class DocumentController {
    //格式化时间
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    @Autowired
    private UserService userService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CommentService commentService;

    //进入文件列表+页码
    @GetMapping("/documentsIndex/{pageIndex}")
    public String documents(@PathVariable("pageIndex")int pageIndex,Model model,HttpServletRequest request){
        int pageSize = 7;
        User user = (User)request.getSession().getAttribute("user");
        Document document = null;
        int maxCount = documentService.documentCount(document);
        List<Document> documentList = documentService.getDocumentList(pageIndex,pageSize,document);
        for (Document doc : documentList) {
            User userByQuery = userService.queryUserById(doc.getUser().getUserId());
            doc.setUser(userByQuery);
        }
        List<Type> typeList = typeService.queryAllType();
        Map<String,Integer> pageInfo = PageUtil.page(pageIndex,pageSize,maxCount);
        model.addAttribute("typeList",typeList);
        model.addAttribute("prePage",pageInfo.get("prePage"));
        model.addAttribute("nextPage",pageInfo.get("nextPage"));
        model.addAttribute("documentList",documentList);
        return "admin/document";
    }

    //删除指定的文档
    @GetMapping("/documents/deleteById/{documentId}")
    public String deleteById(@PathVariable("documentId")Long documentId,HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        documentService.deleteDocumentAndFileAndComment(documentId,user);
        return "redirect:/admin/documentsIndex/1";
    }

    //进入搜索文档后的列表
    @RequestMapping("/documents/search/{pageIndex}")
    public String searchDocuments(@PathVariable("pageIndex")int pageIndex, Model model, HttpServletRequest request){
        //判断request是否传值了
        //如果传值 就先清除session里的document//如果没传值
        List<Type> typeList = typeService.queryAllType();
        Document document = null;
        Type type = new Type();
        String title = null;
        title =request.getParameter("title");
        if(title!=null&&title.equals("")){
            title = null;
        }
        String type_idStr = request.getParameter("type");
        Long typeId = null;
        if(type_idStr!=null){
            typeId = Long.parseLong(type_idStr);
        }
        Integer recommend = 0;
        if(title!=null||typeId!=null){
            request.getSession().removeAttribute("document");
            document = new Document();
            type.setTypeId(typeId);
            document.setTitle(title);
            document.setType(type);
            request.getSession().setAttribute("deocument",document);
        }
        else {
            document = (Document) request.getSession().getAttribute("document");
        }
        int pageSize = 7;
        List<Document> documentList = documentService.getDocumentList(pageIndex,pageSize,document);
        for (Document doc : documentList) {
            User user = userService.queryUserById(doc.getUser().getUserId());
            doc.setUser(user);
        }
        int maxCount = documentService.documentCount(document);
        Map<String,Integer> pageInfo = PageUtil.page(pageIndex,pageSize,maxCount);
        model.addAttribute("currentPage",pageIndex);
        model.addAttribute("nextPage",pageInfo.get("nextPage"));
        model.addAttribute("prePage",pageInfo.get("prePage"));
        model.addAttribute("maxPage",pageInfo.get("maxPage"));
        model.addAttribute("typeList",typeList);
        model.addAttribute("document",document);
        model.addAttribute("maxCount",maxCount);
        model.addAttribute("currentPage",pageIndex);

        request.getSession().setAttribute("document",document);
        model.addAttribute("documentList",documentList);
        return "admin/searchresult";

    }
    //进入新增文档的页面
    @GetMapping("/documents/adddocument")
    public String addDocument(Model model){
        List<Type> typeList = typeService.queryAllType();
        List<Tag> tagList = tagService.getAllTags();
        model.addAttribute("typeList",typeList);
        model.addAttribute("tagList",tagList);
        return "admin/document-input";
    }


    @PostMapping("/documents/adddocument")
    public String changeDocument(HttpServletRequest request) throws IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String typeId = request.getParameter("typeId");
        String firstPicture = request.getParameter("firstPicture");
        String flag = request.getParameter("flag");
        String description = request.getParameter("description");
        String saveorpublic = request.getParameter("saveorpublic");
        Integer publish=0;
        if(saveorpublic.equals("1")){
            publish = 1;
        }
        Integer appreciate = 0;
        if (request.getParameter("appreciate")!=null&&request.getParameter("appreciate").equals("on")){
            appreciate =1;
        }
        Integer shareInfo = 0;
        if (request.getParameter("shareInfo")!=null&&request.getParameter("shareInfo").equals("on")){
            shareInfo =1;
        }
        Integer commentAble = 0;
        if (request.getParameter("commentAble")!=null&&request.getParameter("commentAble").equals("on")){
            commentAble =1;
        }
        Integer recommend = 0;
        if (request.getParameter("recommend")!=null&&request.getParameter("recommend").equals("on")){
            recommend =1;
        }
        Integer published = 0;
        if (request.getParameter("saveorpublic")!=null&&request.getParameter("saveorpublic").equals("1")){
            published =1;
        }
        Type type = typeService.queryType(Long.parseLong(request.getParameter("typeId")));
        User user = (User) request.getSession().getAttribute("user");
        String[] strings = request.getParameterValues("tagIdList");
        List<Tag> tagList = new ArrayList<>();
        String[] tags_Str = strings[0].split(",");
        for(String tag_Str:tags_Str){
            Tag tag = new Tag();
            tag.setTagId(Long.parseLong(tag_Str));
            tag.setTagName(tagService.queryTag(Long.parseLong(tag_Str)).getTagName());
            tagList.add(tag);
        }
        Document document = new Document(title,content,firstPicture,flag,0,appreciate,shareInfo,commentAble,published,recommend,new Date(),new Date(),type,tagList,user,description);
        documentService.saveDocument(document);
        String documentTitle = document.getTitle();
        String nowTimeStr = format.format(new Date());
        Map<String,List<String>>fileInfo = FileUploadUtil.fileUpload(request,documentTitle,nowTimeStr);
        //对文件的数据表进行添加
        if(fileInfo!=null){
            List<String> fileNameList = fileInfo.get("fileNameList");
            List<String> filePathList = fileInfo.get("filePathList");
            List<String> fileOriginNameList = fileInfo.get("fileOriginNameList");
            for(int i=0;i<fileNameList.size();i++){
                fileService.addFile(fileNameList.get(i),filePathList.get(i),user.getUserId(),document.getDocumentId(),fileOriginNameList.get(i),new Date(),new Date());
            }
        }
        return "redirect:/admin/documentsIndex/1";
    }


    @GetMapping("/documents/updatedocument/{documentId}")
    @ResponseBody
    public ModelAndView updatedDocument(@PathVariable("documentId") Long documentId, ModelAndView model, HttpServletRequest request){
        Document document = documentService.queryDetailedDocument(documentId);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        document.setUser(user);
        List<Long> taglist_docList = new ArrayList<>();
        for(Tag tag:document.getTagList()){
            taglist_docList.add(tag.getTagId());
        }
        List<Type> typeList = typeService.queryAllType();
        List<Tag> tagList = tagService.getAllTags();
        model.addObject("tagList",tagList);
        model.addObject("typeList",typeList);
        model.addObject("document",document);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<taglist_docList.size();i++){
            sb.append(taglist_docList.get(i));
            sb.append(",");
        }
        String taglist = sb.toString().substring(0,sb.toString().lastIndexOf(","));
        java.io.File fileOfFile =null;
        Double fileLength = null;
        //查询附属文件信息
        List<File> fileList = fileService.getFileListInfo(documentId,user.getUserId());
        List<File_Len> fileLenList = new ArrayList<>();
        if(fileList!=null){

            for(File file:fileList){
                File_Len file_len = new File_Len();
                fileOfFile = new java.io.File(PathUtil.getBasePath()+ java.io.File.separator +user.getUsername()+ java.io.File.separator+document.getTitle()+ java.io.File.separator+file.getFileName());
                fileLength = fileOfFile.length()/1024.0/1024.0;
                file_len.setFile(file);
                file_len.setLength(fileLength);
                fileLenList.add(file_len);
            }
        }
        model.addObject("fileLenList",fileLenList);
        model.addObject("taglist_doc",taglist);
        model.setViewName("admin/document-update");
        return model;
    }

    @PostMapping("/documents/updatedocument/{documentId}")
    public String updateDocument(@PathVariable("documentId") Long documentId,HttpServletRequest request) throws IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String typeId = request.getParameter("typeId");
        String firstPicture = request.getParameter("firstPicture");
        String flag = request.getParameter("flag");
        String saveorpublic = request.getParameter("saveorpublic");
        String description = request.getParameter("description");
        Integer publish=0;
        if(saveorpublic.equals("1")){
            publish = 1;
        }
        Integer appreciate = 0;
        if (request.getParameter("appreciate")!=null&&request.getParameter("appreciate").equals("on")){
            appreciate =1;
        }
        Integer shareInfo = 0;
        if (request.getParameter("shareInfo")!=null&&request.getParameter("shareInfo").equals("on")){
            shareInfo =1;
        }
        Integer commentAble = 0;
        if (request.getParameter("commentAble")!=null&&request.getParameter("commentAble").equals("on")){
            commentAble =1;
        }
        Integer recommend = 0;
        if (request.getParameter("recommend")!=null&&request.getParameter("recommend").equals("on")){
            recommend =1;
        }
        Integer published = 0;
        if (request.getParameter("saveorpublic")!=null&&request.getParameter("saveorpublic").equals("1")){
            published =1;
        }
        Type type = typeService.queryType(Long.parseLong(request.getParameter("typeId")));
        User user = (User) request.getSession().getAttribute("user");
        String[] strings = request.getParameterValues("tagIdList");
        List<Tag> tagList = new ArrayList<>();
        String[] tags_Str = strings[0].split(",");
        for(String tag_Str:tags_Str){
            Tag tag = new Tag();
            tag.setTagId(Long.parseLong(tag_Str));
            tag.setTagName(tagService.queryTag(Long.parseLong(tag_Str)).getTagName());
            tagList.add(tag);
        }
        Document document = new Document(title,content,firstPicture,flag,appreciate,shareInfo,commentAble,published,recommend,new Date(),type,tagList,user,description);
        document.setDocumentId(documentId);
        documentService.updateDocument(document);
        //更新文件
        String documentTitle = document.getTitle();
        String nowTimeStr = format.format(new Date());
        Map<String,List<String>>fileInfo = FileUploadUtil.fileUpload(request,documentTitle,nowTimeStr);
        //对文件的数据表进行添加
        if(fileInfo!=null){
            List<String> fileNameList = fileInfo.get("fileNameList");
            List<String> filePathList = fileInfo.get("filePathList");
            List<String> fileOriginNameList = fileInfo.get("fileOriginNameList");
            for(int i=0;i<fileNameList.size();i++){
                fileService.addFile(fileNameList.get(i),filePathList.get(i),user.getUserId(),document.getDocumentId(),fileOriginNameList.get(i),new Date(),new Date());
            }
        }
        return "redirect:/admin/documents/updatedocument/"+documentId;
    }
}






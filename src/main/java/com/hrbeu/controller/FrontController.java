package com.hrbeu.controller;

import com.hrbeu.pojo.*;
import com.hrbeu.pojo.vo.File_Len;
import com.hrbeu.pojo.vo.Tag_Count;
import com.hrbeu.pojo.vo.Type_Count;
import com.hrbeu.service.CommentService;
import com.hrbeu.service.LabDocumentService;
import com.hrbeu.service.admin.DocumentService;
import com.hrbeu.service.admin.FileService;
import com.hrbeu.service.admin.TypeService;
import com.hrbeu.service.admin.UserService;
import com.hrbeu.utils.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Controller
public class FrontController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private LabDocumentService labDocumentService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private RedisUtil redisUtil;
    @GetMapping("/plt")
    public String index(Model model,HttpServletRequest request){
        String guest = request.getParameter("guest");
        if(guest!=null&&guest.equals("1")){
            HttpSession session = request.getSession();
            session.removeAttribute("user");
        }
        int pageIndex =1;
        int pageSize = 5;
        List<Document> documentList = labDocumentService.getPublishedDocument(pageIndex,pageSize);
        int maxCount = labDocumentService.getPublishedDocumentCount();
        Integer typeCount = 6;
        List<Type_Count> typeCountList = labDocumentService.getTypeAndCount(typeCount);
        Integer tagCount =10;
        List<Tag_Count> tagCountList = labDocumentService.getTagAndCount(tagCount);
        Integer recommendDocumentCount = 10;
        List<Document> recommendDocumentList = labDocumentService.getRecommendDocumentList(recommendDocumentCount);
        Map<String,Integer> pageInfo = PageUtil.page(pageIndex,pageSize,maxCount);
        //viewCount
        List<Integer> viewCountList = new LinkedList<>();
        for (Document document : documentList) {
            Integer viewCount = 0;
            Object viewCountObject = redisUtil.hget("viewCount",document.getDocumentId()+"");
            if(viewCountObject!=null){
                viewCount = (Integer)viewCountObject;
            }
            viewCountList.add(viewCount);
        }
        model.addAttribute("viewCountList",viewCountList);
        model.addAttribute("recommendDocumentList",recommendDocumentList);
        model.addAttribute("tagCountList",tagCountList);
        model.addAttribute("currentPage",pageIndex);
        model.addAttribute("typeCountList",typeCountList);
        model.addAttribute("nextPage",pageInfo.get("nextPage"));
        model.addAttribute("prePage",pageInfo.get("prePage"));
        model.addAttribute("documentList",documentList);
        model.addAttribute("maxCount",maxCount);
        return "plt-index";
    }
    @GetMapping("/plt/{pageIndex}")
    public String indexInPage(@PathVariable("pageIndex")int pageIndex, Model model,HttpServletRequest request){
        String guest = request.getParameter("guest");
        if(guest!=null&&guest.equals("1")){
            HttpSession session = request.getSession();
            session.removeAttribute("user");
        }
        int pageSize = 5;
        List<Document> documentList = labDocumentService.getPublishedDocument(pageIndex,pageSize);
        int maxCount = labDocumentService.getPublishedDocumentCount();
        Integer typeCount = 6;
        List<Type_Count> typeCountList = labDocumentService.getTypeAndCount(typeCount);
        Integer tagCount =10;
        List<Tag_Count> tagCountList = labDocumentService.getTagAndCount(tagCount);
        Integer recommendDocumentCount = 10;
        List<Document> recommendDocumentList = labDocumentService.getRecommendDocumentList(recommendDocumentCount);
        Map<String,Integer> pageInfo = PageUtil.page(pageIndex,pageSize,maxCount);
        //viewCount
        List<Integer> viewCountList = new LinkedList<>();
        for (Document document : documentList) {
            Integer viewCount = 0;
            Object viewCountObject = redisUtil.hget("viewCount",document.getDocumentId()+"");
            if(viewCountObject!=null){
                viewCount = (Integer)viewCountObject;
            }
            viewCountList.add(viewCount);
        }
        model.addAttribute("viewCountList",viewCountList);
        model.addAttribute("recommendDocumentList",recommendDocumentList);
        model.addAttribute("tagCountList",tagCountList);
        model.addAttribute("currentPage",pageIndex);
        model.addAttribute("typeCountList",typeCountList);
        model.addAttribute("nextPage",pageInfo.get("nextPage"));
        model.addAttribute("prePage",pageInfo.get("prePage"));
        model.addAttribute("documentList",documentList);
        model.addAttribute("maxCount",maxCount);
        return "plt-index";
    }

    @RequestMapping("/search/{pageIndex}")
    public String search(@PathVariable("pageIndex")int pageIndex, Model model, HttpServletRequest request){
        String query = request.getParameter("query");
        HttpSession session = request.getSession();

        if(query!=null&&!query.equals("")){
            session.removeAttribute("query");
            session.setAttribute("query",query);
        }
        else {
            query = (String)session.getAttribute("query");
        }
        int pageSize = 4;
        List<Document> documentList = labDocumentService.queryBySearch(query,pageIndex,pageSize);
        int maxCount = labDocumentService.queryBySearchCount(query);
        Map<String,Integer> pageInfo = PageUtil.page(pageIndex,pageSize,maxCount);
        List<Integer> viewCountList = new LinkedList<>();
        for (Document document : documentList) {
            Integer viewCount = 0;
            Object viewCountObject = redisUtil.hget("viewCount",document.getDocumentId()+"");
            if(viewCountObject!=null){
                viewCount = (Integer)viewCountObject;
            }
            viewCountList.add(viewCount);
        }
        model.addAttribute("viewCountList",viewCountList);
        model.addAttribute("documentList",documentList);
        model.addAttribute("maxCount",maxCount);
        model.addAttribute("prePage",pageInfo.get("prePage"));
        model.addAttribute("nextPage",pageInfo.get("nextPage"));
        model.addAttribute("query",query);
        return "searchresult";
    }

    @GetMapping("/document/{documentId}")
    public String document(@PathVariable("documentId")Long documentId, Model model,HttpServletRequest request){
        //使用redis替换了document的viewCount功能
        Object viewCountObject = redisUtil.hget("viewCount", documentId + "");
        if(viewCountObject!=null){
            Integer viewCount = (Integer)viewCountObject+1;
            redisUtil.hset("viewCount",documentId+"",viewCount);
            model.addAttribute("viewCount",viewCount);
        }else {
            redisUtil.hset("viewCount",documentId+"",1);
            model.addAttribute("viewCount",1);
        }
        //documentService.createViewCount(documentId);
        Document document = documentService.getMostDetailDocument(documentId);
        if(document==null){
            return "redirect:/plt";
        }
        if(document.getPublished()!=1){
            User user = (User)request.getSession().getAttribute("user");
            if(user!=null){
                if(!user.getUserId().equals(document.getUser().getUserId())){
                    return "redirect:/plt";
                }
            }else {
                String errMsg = "该文件不能被访问";
                return "redirect:/";
            }
        }
        //查询附属文件信息
        List<File> fileList = fileService.getFileListInfo(documentId);
        List<File_Len> fileLenList = new ArrayList<>();
        if(fileList!=null){
            for(File file:fileList){
                File_Len file_len = new File_Len();
                java.io.File fileOfFile = new java.io.File(PathUtil.getBasePath()+file.getFilePath());
                Double fileLength = fileOfFile.length()/1024.0/1024.0;
                file_len.setFile(file);
                file_len.setLength(fileLength);
                fileLenList.add(file_len);
            }
        }
        String flagStr = "原创";
        if(document!=null&&document.getFlag()!=null){
            if(document.getFlag().equals("2")){
                flagStr = "转载";
            }
            if(document.getFlag().equals("3")){
                flagStr="翻译";
            }
            String contentHtml = Md2Html.md2htmlPro(document.getContent());
            model.addAttribute("contentHtml",contentHtml);
        }

        model.addAttribute("fileLenList",fileLenList);

        model.addAttribute("document",document);
        model.addAttribute("flagStr",flagStr);
        //评论显示
        //根评论
        List<Comment> commentList = commentService.listCommentByDocumentId(documentId);
        for(Comment comment:commentList){
            List<Comment> commentsofComment = comment.getCommnets();
            for(Comment child:commentsofComment){
                child.setParentComment(commentService.getParentComment(child));
            }
        }
        model.addAttribute("commentList",commentList);
        return "document";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null){
            return "redirect:/plt";
        }
        else {
            return "index";
        }
    }

    @PostMapping("/login")
    public String loginCheck(@Param("username")String username, @Param("password")String password, HttpServletRequest request,Model model){
        User user = userService.checkUser(username, password);
        if(user==null){
            model.addAttribute("errMsg","用户名不存在或密码错误");
            return "index";
        }else {
            user.setPassword(null);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
        }
        return "redirect:/plt";
    }

    @GetMapping("/")
    public String indexLogin(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null){
            return "redirect:/plt";
        }
        else {
            return "index";
        }
    }


    @PostMapping("/indexLogin")
    public String indexLogin(@Param("username")String username, @Param("password")String password, HttpServletRequest request,Model model){

        User user = userService.checkUser(username, password);
        if(user==null){
            model.addAttribute("errMsg","用户名不存在或密码错误");
            return "index";
        }else {
            user.setPassword(null);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
        }
        return "redirect:/plt";
    }

    @RequestMapping("/files/deleteFile/{documentId}/{fileId}")
    public String onlyDeleteFile(@PathVariable("documentId")Long documentId,@PathVariable("fileId")Long fileId, HttpServletRequest request){
        fileService.onlydeleteFile(fileId);
        return "redirect:/document/"+documentId;

    }
    @GetMapping("/plt-register")
    public String toRegister(){
        return "plt-register";
    }

    //注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@Param("username")String username, @Param("password")String password, @Param("email")String email, @Param("nickname")String nickname, ModelAndView model, HttpServletRequest request){
        String password_input = MD5Util.md5(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password_input);
        user.setEmail(email);
        user.setNickname(nickname);
        int effNum = userService.addUser(user);
        if(effNum<=0){
            model.addObject("essMsg","注册失败");
            return "redirect:/plt-register";
        }else {
            HttpSession session = request.getSession();
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/plt";
        }
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
        User user = (User)request.getSession().getAttribute("user");
        document.setUser(user);
        List<Document> documentList = documentService.getDocumentList(pageIndex,pageSize,document);
        for (Document doc : documentList) {
            user = userService.queryUserById(user.getUserId());
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
        return "mydocumentsearchresult";

    }
}

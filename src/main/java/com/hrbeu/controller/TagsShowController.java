package com.hrbeu.controller;

import com.hrbeu.pojo.Document;
import com.hrbeu.pojo.Tag;
import com.hrbeu.pojo.vo.Tag_Count;
import com.hrbeu.service.LabDocumentService;
import com.hrbeu.service.admin.DocumentService;
import com.hrbeu.service.admin.TagService;
import com.hrbeu.utils.PageUtil;
import com.hrbeu.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class TagsShowController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TagService tagService;
    @Autowired
    private LabDocumentService labDocumentService;
    @Autowired
    private DocumentService documentService;
    @GetMapping("/tags/{tagId}/{pageIndex}")
    public String tags(@PathVariable("tagId")Long tagId, @PathVariable("pageIndex")int pageIndex, Model model){
        List<Tag> tagList = tagService.getAllTags();
        List<Tag_Count> tagCountList = labDocumentService.getTagAndCountByTagId();
        int pageSize = 5;
        List<Document> documentList = null;
        if(tagId==-1){
            tagId = tagList.get(0).getTagId();
            documentList = documentService.queryDocumentByTagId(pageIndex,pageSize,tagId);

        }
        else {
            documentList = documentService.queryDocumentByTagId(pageIndex,pageSize,tagId);
        }
        List<Integer> viewCountList = new LinkedList<>();
        //查询该文件的所有tagList
        int count =documentService.queryDocumentCountByTagId(tagId);
        Map<String,Integer> pageInfo = PageUtil.page(pageIndex,pageSize,count);
        for (Document document : documentList) {
            Integer viewCount = 0;
            Object viewCountObject = redisUtil.hget("viewCount",document.getDocumentId()+"");
            if(viewCountObject!=null){
                viewCount = (Integer)viewCountObject;
            }
            viewCountList.add(viewCount);
        }
        model.addAttribute("viewCountList",viewCountList);
        model.addAttribute("tagList",tagList);
        model.addAttribute("tagCountList",tagCountList);
        model.addAttribute("prePage",pageInfo.get("prePage"));
        model.addAttribute("nextPage",pageInfo.get("nextPage"));
        model.addAttribute("count",count);
        model.addAttribute("currenttagId",tagId);
        model.addAttribute("documentList",documentList);
        model.addAttribute("maxPage",pageInfo.get("maxPage"));
        return "tags";
   }
}

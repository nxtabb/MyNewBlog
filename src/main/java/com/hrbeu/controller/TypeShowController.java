package com.hrbeu.controller;

import com.hrbeu.pojo.Document;
import com.hrbeu.pojo.Type;
import com.hrbeu.pojo.vo.Type_Count;
import com.hrbeu.service.LabDocumentService;
import com.hrbeu.service.admin.DocumentService;
import com.hrbeu.service.admin.TypeService;
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
public class TypeShowController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TypeService typeService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private LabDocumentService labDocumentService;

    @GetMapping("types/{typeId}/{pageIndex}")
    public String types(@PathVariable("typeId")Long typeId,@PathVariable("pageIndex") int pageIndex, Model model){
        List<Type> typeList = typeService.queryAllType();
        Integer pageSize = 5;
        List<Document> documentList= null;
        if(typeId==-1){
            typeId = typeList.get(0).getTypeId();
            documentList = documentService.queryDocumentByTypeId(pageIndex,pageSize,typeId);

        }
        else {
            documentList = documentService.queryDocumentByTypeId(pageIndex,pageSize,typeId);
        }
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
        int count =documentService.queryDocumentCountByTypeId(typeId);
        Map<String,Integer> pageInfo =  PageUtil.page(pageIndex,pageSize,count);
        List<Type_Count> typeCountList = labDocumentService.getTypeAndCountByTypeId();
        model.addAttribute("count",count);
        model.addAttribute("prePage",pageInfo.get("prePage"));
        model.addAttribute("nextPage",pageInfo.get("nextPage"));
        model.addAttribute("maxPage",pageInfo.get("maxPage"));
        model.addAttribute("typeList",typeList);
        model.addAttribute("typeCountList",typeCountList);
        model.addAttribute("documentList",documentList);
        model.addAttribute("currenttypeId",typeId);
        return "types";
    }
}

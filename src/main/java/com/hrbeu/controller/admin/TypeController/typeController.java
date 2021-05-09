package com.hrbeu.controller.admin.TypeController;

import com.hrbeu.pojo.Type;
import com.hrbeu.service.admin.TypeService;
import com.hrbeu.utils.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class typeController {
    @Autowired
    private TypeService typeService;
    @GetMapping(value = "/types/{pageIndex}")
    public String types(@PathVariable("pageIndex")int pageIndex, Model model) {
        int maxCount = typeService.typeCount();
        int pageSize = 10;
        List<Type> typeList = typeService.queryTypeList(pageIndex, pageSize);
        Map<String,Integer> pageInfo = PageUtil.page(pageIndex,pageSize,maxCount);
        model.addAttribute("nextPage",pageInfo.get("nextPage"));
        model.addAttribute("prePage",pageInfo.get("prePage"));
        model.addAttribute("maxPage",pageInfo.get("maxPage"));
        model.addAttribute("typeList", typeList);
        return "/admin/types";
    }

    @PostMapping("/types/addtype")
    public String addtype(@Param("name")String name, RedirectAttributes attributes){
        Type type = new Type();
        if(name==null||name.equals("")){
            attributes.addFlashAttribute("message","操作失败");
        }else {
            type.setTypeName(name);
            typeService.saveType(type);
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types/1";
    }

    @RequestMapping("/types/deleteById/{typeId}")
    public String deleteTypeById(@PathVariable("typeId")Long typeId){
        typeService.deleteType(typeId);
        return "redirect:/admin/types/1";
    }

    @RequestMapping("/types/checktype/{typeName}")
    @ResponseBody
    public Map<String,Object> checktype(@PathVariable("typeName")String typeName){
        Map<String,Object> modelMap = new HashMap<>();
        int count  = typeService.checkType(typeName);
        if(count>=1){
            modelMap.put("success",false);
        }
        else {
            modelMap.put("success",true);
        }
        return modelMap;
    }

    @GetMapping("/types/updateType/{typeId}")
    public String updateTypeView(@PathVariable("typeId")Long typeId, Model model){
        model.addAttribute("type",typeService.queryType(typeId));
        return "admin/type-update";

    }

    @PostMapping("/types/updateType/{typeId}")
    public String updateType(@PathVariable("typeId")Long typeId, @RequestParam("name")String typeName){
        Type type = new Type();
        type.setTypeName(typeName);
        typeService.updateType(typeId,type);
        return "redirect:/admin/types/1";

    }

}

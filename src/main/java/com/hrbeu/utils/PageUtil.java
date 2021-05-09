package com.hrbeu.utils;

import java.util.HashMap;
import java.util.Map;

public class PageUtil {
    public static Map<String,Integer> page(Integer pageIndex,Integer pageSize,Integer countForPage){
        Map<String,Integer> pageInfo = new HashMap<>();
        int maxPage = 0;
        if(countForPage<pageSize){
            maxPage = 1;
        }
        else if(countForPage%pageSize==0) {
            maxPage = countForPage/pageSize;
        }
        else {
            maxPage = countForPage/pageSize+1;
        }
        int prePage = 0;
        int nextPage = 0;
        if(pageIndex>=maxPage){
            nextPage = maxPage;
            prePage = pageIndex-1;
        }
        else if(pageIndex<=1){
            prePage = 1;
            nextPage = pageIndex+1;
        }
        else {
            prePage = pageIndex-1;
            nextPage = pageIndex+1;
        }
        if(maxPage==1){
            prePage=1;
            nextPage =1;
        }
        pageInfo.put("nextPage",nextPage);
        pageInfo.put("prePage",prePage);
        pageInfo.put("maxPage",maxPage);

        return pageInfo;
    }
}

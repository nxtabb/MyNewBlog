package com.hrbeu.service.admin;

import com.hrbeu.pojo.Tag;

import java.util.List;

public interface TagService {
    int saveTag(Tag tag);
    Tag queryTag(Long tagId);
    List<Tag> queryTagList(int pageIndex, int pageSize);
    int updateTag(Long tagId,Tag tag);
    void deleteTag(Long tagId);
    int tagCount();
    int checkTag(String tagName);
    List<Tag> getAllTags();
}

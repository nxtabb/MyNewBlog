package com.hrbeu.controller;

import com.hrbeu.pojo.Comment;
import com.hrbeu.pojo.User;
import com.hrbeu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public void sendComment(Comment comment, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            comment.setAdminComment(1);
        }else {
            comment.setAdminComment(0);
        }
        int effNum = commentService.saveComment(comment);
    }

    @GetMapping("/deletecomment/{commentId}")
    public String deleteComment(@PathVariable("commentId")Long commentId){
        Long documentId = commentService.queryDocumentOfComment(commentId);
        commentService.deleteComment(commentId);
        return "redirect:/document/"+documentId;
    }
}

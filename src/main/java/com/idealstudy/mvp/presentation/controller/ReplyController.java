package com.idealstudy.mvp.presentation.controller;

import com.idealstudy.mvp.application.dto.ReplyDto;
import com.idealstudy.mvp.application.dto.ReplyPageResultDto;
import com.idealstudy.mvp.application.service.ReplyService;
import com.idealstudy.mvp.security.annotation.ForUser;
import com.idealstudy.mvp.util.TryCatchControllerTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    @Autowired
    private final ReplyService replyService;

    @ForUser
    @PostMapping("/api/inquiries/comments")
    public ResponseEntity<ReplyDto> createReply(@RequestBody ReplyDto dto) {

        return TryCatchControllerTemplate.execute(() -> replyService.create(dto.getContent(),
            dto.getVisibility(), null, dto.getClassInquiryId(), dto.getPostId(),
                dto.getCreatedBy()));
    }

    @ForUser
    @PostMapping("/api/inquiries/comments/{commentId}/replies")
    public ResponseEntity<ReplyDto> createNestedReply(@RequestBody ReplyDto dto, @PathVariable Long commentId) {


        return TryCatchControllerTemplate.execute(() -> replyService.create(dto.getContent(), dto.getVisibility(),
                commentId, dto.getClassInquiryId(), dto.getPostId(), dto.getCreatedBy()));
    }

    @GetMapping("/inquiries/{inquiryId}/comments")
    public ResponseEntity<List<ReplyDto>> findList(@PathVariable Long inquiryId, @RequestParam int page) {

        // 현재 빠른 개발을 위해서 페이징 기능은 제외
        return TryCatchControllerTemplate.execute(() -> {
            ReplyPageResultDto dto = replyService.findListInClassInquiry(inquiryId, page);
            return dto.getDtoList();
        });
    }

    @PatchMapping("/api/inquiries/comments/{commentId}")
    public ResponseEntity<ReplyDto> update(@PathVariable Long commentId, @RequestBody ReplyDto dto) {

        return TryCatchControllerTemplate.execute(() -> replyService.update(commentId, dto.getCreatedBy(),
                dto.getContent(), dto.getVisibility()));
    }

    @DeleteMapping("/api/inquiries/comments/{commentId}")
    public ResponseEntity<Object> delete(@PathVariable Long commentId, @RequestBody ReplyDto dto) {

        return TryCatchControllerTemplate.execute(() -> {
            replyService.delete(commentId, dto.getCreatedBy());
            return null;
        });
    }
}

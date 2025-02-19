package com.example.newsfeed.friendapply.controller;

import com.example.newsfeed.friendapply.dto.FriendApplyRequest;
import com.example.newsfeed.friendapply.dto.FriendApplyResponse;
import com.example.newsfeed.friendapply.service.FriendApplyService;
import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.request.PageRequest;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.MessageResponse;
import com.example.newsfeed.common.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/apply")
@RequiredArgsConstructor
public class FriendApplyController {

    private final FriendApplyService applyService;

    @PostMapping
    public MessageResponse apply(@Login LoginUser loginUser,
                                 @Valid @RequestBody FriendApplyRequest applyRequest){

        String message = applyService.apply(loginUser.getUserId(), applyRequest.getUserId());
        return MessageResponse.of(message);
    }


    @GetMapping
    public ResponseEntity<Response<FriendApplyResponse>> getApplyList(@RequestParam(name = "direct") String direct,
                                                                          @Login LoginUser loginUser,
                                                                          @Valid @ModelAttribute PageRequest page){

        Page<FriendApplyResponse> applyList = applyService.getApplyList(ApplicationDirection.of(direct), loginUser.getUserId(),page.getPage(), page.getSize());
        return new ResponseEntity<>(Response.fromPage(applyList), HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public MessageResponse cancelApplication(@Login LoginUser loginUser, @PathVariable Long userId){

        applyService.cancelApplication(loginUser.getUserId(), userId);
        return MessageResponse.of("친구 신청을 취소하였습니다.");
    }
}

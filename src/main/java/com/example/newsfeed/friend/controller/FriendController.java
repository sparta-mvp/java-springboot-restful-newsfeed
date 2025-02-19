package com.example.newsfeed.friend.controller;

import com.example.newsfeed.auth.dto.LoginUser;
import com.example.newsfeed.common.request.PageRequest;
import com.example.newsfeed.common.resolvers.Login;
import com.example.newsfeed.common.response.MessageResponse;
import com.example.newsfeed.common.response.Response;
import com.example.newsfeed.friend.dto.FriendRequest;
import com.example.newsfeed.friend.dto.FriendResponse;
import com.example.newsfeed.friend.dto.TagUserResponse;
import com.example.newsfeed.friend.service.FriendService;
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
@RequestMapping("/v1/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;


    @PostMapping
    public MessageResponse addFriendOrRefuse(@Login LoginUser loginUser, @Valid @RequestBody FriendRequest friendRequest) {
        String responseMessage = friendService.addFriendOrRefuse(loginUser.getUserId(), friendRequest.getUser(), friendRequest.getStatus());
        return MessageResponse.of(responseMessage);
    }

    // 특정 태그를 찾거나 같은 태그인 사람을 검색
    @GetMapping("/search")
    public ResponseEntity<Response<TagUserResponse>> findByTag(@RequestParam(required = false) String tag,
                                                               @Login LoginUser loginUser,
                                                               @Valid @ModelAttribute PageRequest page) {

        Page<TagUserResponse> tagList = friendService.findByTag(tag, loginUser.getUserId(), page.getPage(), page.getSize());
        return new ResponseEntity<>(Response.fromPage(tagList), HttpStatus.OK);
    }

    // 친구 검색
    @GetMapping("/{id}")
    public ResponseEntity<Response<FriendResponse>> getFriend(@Login LoginUser loginUser, @PathVariable Long id) {
        FriendResponse myFriend = friendService.getFriend(loginUser.getUserId(), id);
        return new ResponseEntity<>(Response.of(myFriend), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Response<FriendResponse>> findMyFriends(@Login LoginUser loginUser, @Valid @ModelAttribute PageRequest page) {
        Page<FriendResponse> friendsList = friendService.findByFriends(loginUser.getUserId(), page.getPage(), page.getSize());
        return new ResponseEntity<>(Response.fromPage(friendsList), HttpStatus.OK);


    }

    @DeleteMapping
    public MessageResponse deleteFriend(@Login LoginUser loginUser, @RequestParam(name = "id") Long deleteId) {
        friendService.deleteFriend(loginUser.getUserId(), deleteId);
        return MessageResponse.of("삭제되었습니다.");
    }

}

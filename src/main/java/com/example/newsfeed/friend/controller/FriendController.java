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
import lombok.extern.slf4j.Slf4j;
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


    // 특정 태그를 찾거나 같은 태그인 사람을 검색
    @GetMapping("/search")
    public ResponseEntity<Response<TagUserResponse>> findByTag(@RequestParam(required = false) String tag,
                                                               @Login LoginUser loginUser,
                                                               @Valid @ModelAttribute PageRequest page) {

        Page<TagUserResponse> tagList = friendService.findByTag(tag, loginUser.getUserId(), page.getPage(), page.getSize());
        return new ResponseEntity<>(Response.fromPage(tagList), HttpStatus.OK);
    }


    // 내가 팔로우 하는 사람들(following) 호출 / 나를 팔로우 하는 사람들(follower) 호출
    @GetMapping
    public ResponseEntity<Response<FriendResponse>> getFollow(@RequestParam(name = "type") String type,
                                                              @Login LoginUser loginUser,
                                                              @Valid @ModelAttribute PageRequest page) {

        Page<FriendResponse> myFollow = friendService.getFollowByType(FollowType.of(type), loginUser.getUserId(), page.getPage(), page.getSize());
        return new ResponseEntity<>(Response.fromPage(myFollow), HttpStatus.OK);
    }


    // 친구 검색
    @GetMapping("/{id}")
    public ResponseEntity<Response<FriendResponse>> getFriend(@Login LoginUser loginUser, @PathVariable Long id) {
        FriendResponse myFriend = friendService.getFriend(loginUser.getUserId(), id);
        return new ResponseEntity<>(Response.of(myFriend), HttpStatus.OK);
    }


    @PostMapping
    public MessageResponse addFollow(@Login LoginUser loginUser, @RequestBody FriendRequest friendRequest) {
        friendService.addFollow(loginUser.getUserId(), friendRequest.getFollowing());
        return MessageResponse.of("팔로우에 성공하였습니다.");
    }


    @DeleteMapping
    public MessageResponse deleteFollow(@Login LoginUser loginUser, @RequestParam Long deleteId) {
        friendService.deleteFollow(loginUser.getUserId(), deleteId);
        return MessageResponse.of("삭제되었습니다.");
    }

}

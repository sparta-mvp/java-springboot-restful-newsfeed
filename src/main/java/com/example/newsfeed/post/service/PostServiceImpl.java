package com.example.newsfeed.post.service;

import com.example.newsfeed.post.dto.PostRequestDto;
import com.example.newsfeed.post.dto.PostResponseDto;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    @Override
    public PostResponseDto save(Long userId, String title, String contents, String keywords) {
        Post post = new Post(title, contents, keywords);

        Member member = memberRepository.findMemberById(userId);

        post.setUser(member);

        Post saved = postRepository.save(post);
        return new PostRequestDto(post.getTitle(), member.getName(), post.getContents(),
                post.getKeyword(), post.getCreatedAt(), post.getUpdatedAt);
    }

    @Override
    public PostResponseDto update(Long userId, Long id, String title, String contents, String keyword) {
        Post post = postRepository.findByIdOrElseThrow(id);

        Member member = memberRepository.findMemberById(userId);

        if(!post.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정권한이 없습니다.");
        }
        post.updatePost(title, contents, keyword);

        return new PostResponseDto(post.getTitle(), post.getContents(), post.getUser().getName(),
                post.getKeyword(), post.getCreatedAt(), post.getUpdatedAt());
    }

    @Override
    public void delete(Long userId, Long id) {
        Post post = postRepository.findByIdOrElseThrow(id);
        if(!post.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.")
        }

        postRepository.delete(post);
    }
}

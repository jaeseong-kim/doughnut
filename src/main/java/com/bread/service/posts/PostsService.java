package com.bread.service.posts;

import com.bread.domain.posts.Posts;
import com.bread.domain.posts.PostsRepository;
import com.bread.web.dto.PostListResponseDto;
import com.bread.web.dto.PostsResponseDto;
import com.bread.web.dto.PostsSaveRequestDto;
import com.bread.web.dto.PostsUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
  서비스 단은 트랜잭션과 도메인 간의 순서를 보장하는 역할을 하고 비즈니스 로직은 도메인에서.
 */
@RequiredArgsConstructor
@Service
public class PostsService {

  private final PostsRepository postsRepository;

  public Long save(PostsSaveRequestDto requestDto) {
    return postsRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, PostsUpdateRequestDto requestDto) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

    posts.update(requestDto.getTitle(), requestDto.getContent());

    //save 부분이 없음 왜? @Transactional 안에서 실행되기 때문에 posts는 영속성이 유지됨.
    //트랜잭션이 시작 되고 값이 바뀌면 @Transactional이 끝나는 시점에 테이블 변경 반영됨.
    //더티 체킹

    return id;
  }

  @Transactional
  public void delete(Long id) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

    postsRepository.delete(posts);

  }

  public PostsResponseDto findById(Long id) {
    Posts posts = postsRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

    return new PostsResponseDto(posts);
  }

  @Transactional(readOnly = true)
  public List<PostListResponseDto> findAllDesc() {
    return postsRepository.findAllDesc().stream()
        .map(PostListResponseDto::new)
        .collect(Collectors.toList());
  }
}

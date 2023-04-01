package com.bread.domain.posts;

import com.bread.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
  엔티티는 생성자를 통해 값 삽입 -> 필요한 경우 메소드를 통해 값 변경 -> DB 저장
  Setter annotation은 하지 않음
  생성자로 값으 넣는 경우 위치가 헷갈릴 수 있기때문에 builder로 생성함
 */

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 500, nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content;

  private String author;

  @Builder //생성자에 있는 필드만 설정 가능
  public Posts(String title, String content, String author) {
    this.title = title;
    this.content = content;
    this.author = author;
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }
}

package com.bread.domain;


import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass //이 클래스를 상속할 엔티티들에게 이 클래스의 필드들도 상속받는 클래스의 필드가 되도록 하는 어노테이션
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  @CreatedDate //생성될 때 자동 저장
  private LocalDateTime createdDate;

  @LastModifiedDate //마지막으로 변경한 시간으로 자동 저장
  private LocalDateTime modifiedDate;

}
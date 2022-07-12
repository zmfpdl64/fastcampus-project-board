package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index( columnList = "title") ,
        @Index( columnList = "hashtag") ,
        @Index( columnList = "createdAt") ,
        @Index( columnList = "createdBy") ,

})
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter @Column(nullable = false) private String title;   //제목
    @Setter @Column(nullable = false, length = 10000) private String content; //본문

    @Setter private String hashtag; //해시태그

    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude //양방향 참조에서는 서로 연쇄되서 무한으로 참조하는 현상이 일어나 이것을 해결하기 위해 한쪽이
    //참조를 끊어줘야한다.
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

//    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;    //생성시간
//    @CreatedBy @Column(nullable = false, length = 100) private String createdBy;           //생성자
//    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;   //수정시간
//    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;          //수정자

//    @MappedSuperclass
//    @Embedded AAA aa;
//    class AAA {}
    protected Article() {
    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    public static Article of (String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override   //지금 막 만든 영속화 되지 않은 entity는 모두 동등성 검사는 탈락한다.
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

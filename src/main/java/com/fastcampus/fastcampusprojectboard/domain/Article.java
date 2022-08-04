package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
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
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount;
    @Setter @Column(nullable = false) private String title;   //제목
    @Setter @Column(nullable = false, length = 10000) private String content; //본문

    @Setter private String hashtag; //해시태그

    @OrderBy("createdAt desc")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude //양방향 참조에서는 서로 연쇄되서 무한으로 참조하는 현상이 일어나 이것을 해결하기 위해 한쪽이
    //참조를 끊어줘야한다.
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    protected Article() {
    }

    private Article(UserAccount userAccount ,String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    public static Article of (UserAccount userAccount ,String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
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

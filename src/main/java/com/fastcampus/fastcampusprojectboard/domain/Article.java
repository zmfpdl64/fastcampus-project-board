package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.*;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index( columnList = "title") ,
        @Index( columnList = "createdAt") ,
        @Index( columnList = "createdBy") ,

})
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;
    @Setter
    @Column(nullable = false)
    private String title;   //제목
    @Setter
    @Column(nullable = false, length = 10000)
    private String content; //본문

    @ToString.Exclude
    @JoinTable(
            name = "article_hashtag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //생성, 업데이트 가능
    private Set<Hashtag> hashtags = new LinkedHashSet<>();

    @OrderBy("createdAt desc")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude //양방향 참조에서는 서로 연쇄되서 무한으로 참조하는 현상이 일어나 이것을 해결하기 위해 한쪽이
    //참조를 끊어줘야한다.
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    protected Article() {
    }

    private Article(UserAccount userAccount ,String title, String content) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }
    public static Article of (UserAccount userAccount ,String title, String content) {
        return new Article(userAccount, title, content);
    }

    @Override   //지금 막 만든 영속화 되지 않은 entity는 모두 동등성 검사는 탈락한다.
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return this.getId() != null && this.getId().equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    public void addHashtag(Hashtag hashtag) {
        this.getHashtags().add(hashtag);
    }

    public void addHashtags(Collection<Hashtag> hashtags){
        this.getHashtags().addAll(hashtags);
    }

    public void clearHashtags() {
        this.getHashtags().clear();
    }

}

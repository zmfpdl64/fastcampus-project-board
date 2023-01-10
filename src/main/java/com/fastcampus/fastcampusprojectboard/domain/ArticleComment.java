package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index( columnList = "content") ,
        @Index( columnList = "createdAt") ,
        @Index( columnList = "createdBy") ,

})
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    UserAccount userAccount;

    @Setter
    @Column(updatable = false)
    private Long parentCommentId; //부모 댓글 ID

    @ToString.Exclude
    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private Set<ArticleComment> childComments = new LinkedHashSet<>();

    @Setter
    @ManyToOne(optional = false)
    private Article article;    // 게시글 (id)
    @Setter
    @Column(nullable = false, length = 500)
    private String content;     // 본문

    protected ArticleComment() {
    }

    private ArticleComment(UserAccount userAccount, Article article, Long parentCommentId, String content) {
        this.userAccount = userAccount;
        this.article = article;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }
    public static ArticleComment of(UserAccount userAccount,
                                    Article article,
                                    String content) {
        return new ArticleComment(userAccount, article, null, content);
    }
    public static ArticleComment of(UserAccount userAccount,
                                    Article article,
                                    Long parentCommentId,
                                    String content) {
        return new ArticleComment(userAccount, article, parentCommentId, content);
    }

    public void addChildComment(ArticleComment child) {
        child.setParentCommentId(this.getId());
        this.getChildComments().add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment articleComment)) return false;
        return this.getId() != null && this.getId().equals(articleComment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}

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
import java.util.Objects;


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

    @Setter @ManyToOne(optional = false) UserAccount userAccount;

    @Setter @ManyToOne(optional = false) private Article article;    // 게시글 (id)
    @Setter @Column(nullable = false, length = 500) private String content;     // 본문

    protected ArticleComment() {
    }

    private ArticleComment(UserAccount userAccount, Article article, String content) {
        this.userAccount = userAccount;
        this.article = article;
        this.content = content;
    }
    public static ArticleComment of(UserAccount userAccount,
                                    Article article,
                                    String content) {
        return new ArticleComment(userAccount, article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment articleComment)) return false;
        return id != null && id.equals(articleComment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

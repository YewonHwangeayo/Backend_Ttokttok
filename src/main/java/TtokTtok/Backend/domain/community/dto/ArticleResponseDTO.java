package TtokTtok.Backend.domain.community.dto;

import TtokTtok.Backend.common.enums.ArticleCategory;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ArticleResponseDTO {
    private Long articleId;
    private String title;
    private String content;
    private ArticleCategory category;
    private Long authorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    // 댓글 수, 좋아요 등 추가 기능 구현 예정

    public ArticleResponseDTO(Long articleId, String title, String content, ArticleCategory category, Long authorId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Getter 및 기본 생성자 (Jackson 역직렬화용)
    public ArticleResponseDTO() {}
    public Long getArticleId() { return articleId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public ArticleCategory getCategory() { return category; }
    public Long getAuthorId() { return authorId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }
}

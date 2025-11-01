package TtokTtok.Backend.domain.community.dto;

import TtokTtok.Backend.common.enums.ArticleCategory;

public class ArticleCreateDTO {
    private Long userId; // 작성자 ID
    private ArticleCategory category; // 카테고리 ENUM
    private String title;
    private String content;
    private String hashtag; // 선택 사항

    // 기본 생성자 (Jackson 역직렬화용)
    public ArticleCreateDTO() {}

    // Getter
    public Long getUserId() { return userId; }
    public ArticleCategory getCategory() { return category; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getHashtag() { return hashtag; }

    //Setter
    public void setUserId(Long userId) { this.userId = userId; }
    public void setCategory(ArticleCategory category) { this.category = category; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}

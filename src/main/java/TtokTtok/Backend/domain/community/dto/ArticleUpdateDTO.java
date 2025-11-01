package TtokTtok.Backend.domain.community.dto;

import TtokTtok.Backend.common.enums.ArticleCategory;

public class ArticleUpdateDTO {
    private ArticleCategory category;
    private String title;
    private String content;
    private String hashtag;

    // 기본 생성자 (Jackson 역직렬화용)
    public ArticleUpdateDTO() {}

    // Getter
    public ArticleCategory getCategory() { return category; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getHashtag() { return hashtag; }

    //Setter
    public void setCategory(ArticleCategory category) { this.category = category; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setHashtag(String hashtag) { this.hashtag = hashtag; }
}

package TtokTtok.Backend.domain.community.repository;

import TtokTtok.Backend.common.enums.ArticleCategory;
import TtokTtok.Backend.domain.community.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    //카테고리별 목록 조회]
    List<Article> findByCategoryOrderByModifiedAtDesc(ArticleCategory category);

    //전체 카테고리 조회
    List<Article> findAllByOrderByModifiedAtDesc();
}

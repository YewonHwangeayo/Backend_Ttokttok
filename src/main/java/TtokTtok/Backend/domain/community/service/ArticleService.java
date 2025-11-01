package TtokTtok.Backend.domain.community.service;

import TtokTtok.Backend.common.enums.ArticleCategory;
import TtokTtok.Backend.domain.community.dto.ArticleCreateDTO;
import TtokTtok.Backend.domain.community.dto.ArticleResponseDTO;
import TtokTtok.Backend.domain.community.dto.ArticleUpdateDTO;
import TtokTtok.Backend.domain.community.entity.Article;
import TtokTtok.Backend.domain.community.repository.ArticleRepository;
import TtokTtok.Backend.domain.user.User;
import TtokTtok.Backend.domain.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    private ArticleResponseDTO mapToResponseDTO(Article article) {
        return new ArticleResponseDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCategory(),
                article.getUser().getId(), // 작성자 ID
                article.getCreatedAt(),
                article.getModifiedAt()
        );
    }

    //카테고리별 목록 조회
    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> getArticlesByCategory(String categoryName) {
        List<Article> articles;

        if ("ALL".equalsIgnoreCase(categoryName)) {
            // "ALL" (전체 카테고리) 버튼 클릭 시: 모든 게시글을 수정 시간 내림차순으로 정렬
            articles = articleRepository.findAllByOrderByModifiedAtDesc();
        } else {
            // 특정 카테고리 버튼 클릭 시: 해당 카테고리 게시글만 조회
            ArticleCategory category = ArticleCategory.valueOf(categoryName.toUpperCase());
            articles = articleRepository.findByCategoryOrderByModifiedAtDesc(category);
        }

        return articles.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }


    //게시글 생성 기능
    public ArticleResponseDTO createArticle(ArticleCreateDTO dto) {
        // 1. User 엔티티 조회
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. Article 엔티티 생성 및 필드 설정
        Article article = new Article();
        article.setUser(user); // user_id (작성자)
        article.setCategory(dto.getCategory());
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setHashtag(dto.getHashtag());

        // createdAt, modifiedAt은 BaseEntity Auditing 기능으로 자동 생성

        Article savedArticle = articleRepository.save(article);

        return mapToResponseDTO(savedArticle); // ResponseDTO 반환 (createdAt, modifiedAt 포함)
    }



    //게시글 편집
    public ArticleResponseDTO updateArticle(Long articleId, ArticleUpdateDTO dto) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 🚨 [핵심] 소음일기장과 동일한 방식: DTO에서 값이 null이 아닌 경우에만 수정
        if (dto.getCategory() != null) {
            article.setCategory(dto.getCategory());
        }
        if (dto.getTitle() != null) {
            article.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null) {
            article.setContent(dto.getContent());
        }
        if (dto.getHashtag() != null) {
            article.setHashtag(dto.getHashtag());
        }

        // modifiedAt은 Auditing 기능으로 자동 업데이트

        Article updatedArticle = articleRepository.save(article);

        return mapToResponseDTO(updatedArticle); // ResponseDTO 반환 (modifiedAt 포함)
    }




    //게시글 삭제
    public Long deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // isDeleted 플래그만 true로 변경하고 저장
        article.setDeleted(true);

        articleRepository.save(article); // 변경사항 DB에 반영 (modifiedAt 자동 업데이트)

        return articleId; // 삭제된 게시글 ID 반환
    }

    //게시글 상세 조회
    @Transactional(readOnly = true)
    public ArticleResponseDTO getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return mapToResponseDTO(article);
    }

}

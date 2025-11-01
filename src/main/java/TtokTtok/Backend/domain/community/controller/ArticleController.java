package TtokTtok.Backend.domain.community.controller;

import TtokTtok.Backend.domain.community.dto.ArticleCreateDTO;
import TtokTtok.Backend.domain.community.dto.ArticleResponseDTO;
import TtokTtok.Backend.domain.community.dto.ArticleUpdateDTO;
import TtokTtok.Backend.domain.community.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    //카테고리 별 조회
    @GetMapping
    public ResponseEntity<?> getArticlesByCategory(
            @RequestParam(name = "category", defaultValue = "ALL") String categoryName) {

        try {
            List<ArticleResponseDTO> articles = articleService.getArticlesByCategory(categoryName);

            Map<String, Object> response = new HashMap<>();
            response.put("isSuccess", true);
            response.put("code", "COMMON200");
            response.put("message", "게시글 목록 조회 성공");
            response.put("result", articles);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "ANALYSIS4001");
            error.put("message", "올바르지 않은 카테고리 형식입니다.");
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "SERVER500");
            error.put("message", "게시글 목록 조회 중 서버 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(error);
        }
    }


    //게시글 생성
    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody ArticleCreateDTO dto) {
        try {
            ArticleResponseDTO createdArticle = articleService.createArticle(dto);

            Map<String, Object> response = new HashMap<>();
            response.put("isSuccess", true);
            response.put("code", "COMMON201");
            response.put("message", "게시글 작성 성공");
            response.put("result", createdArticle);

            return ResponseEntity.status(201).body(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "MEMBER4001"); // 회원 ID 오류
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "SERVER500");
            error.put("message", "게시글 작성 중 서버 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(error);
        }
    }

    //게시글 상세 조회
    @GetMapping("/{articleId}")
    public ResponseEntity<?> getArticle(@PathVariable Long articleId) {
        try {
            ArticleResponseDTO article = articleService.getArticle(articleId);

            Map<String, Object> response = new HashMap<>();
            response.put("isSuccess", true);
            response.put("code", "COMMON200");
            response.put("message", "게시글 상세 조회 성공");
            response.put("result", article);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "POST4004");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "SERVER500");
            error.put("message", "게시글 상세 조회 중 서버 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(error);
        }
    }


    //게시글 편집
    @PatchMapping("/{articleId}")
    public ResponseEntity<?> updateArticle(@PathVariable Long articleId, @RequestBody ArticleUpdateDTO dto) {
        try {
            ArticleResponseDTO updatedArticle = articleService.updateArticle(articleId, dto);

            Map<String, Object> response = new HashMap<>();
            response.put("isSuccess", true);
            response.put("code", "COMMON200");
            response.put("message", "게시글 수정 성공");
            response.put("result", updatedArticle);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "POST4005");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "SERVER500");
            error.put("message", "게시글 수정 중 서버 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(error);
        }
    }



    //게시글 삭제
    @DeleteMapping("/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable("articleId") Long articleId) {
        try {
            Long deletedArticleId = articleService.deleteArticle(articleId);

            Map<String, Object> response = new HashMap<>();
            response.put("isSuccess", true);
            response.put("code", "COMMON200");
            response.put("message", "게시글 삭제 성공 (Soft Delete)");
            response.put("result", Map.of("postId", deletedArticleId));

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "POST4004");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("isSuccess", false);
            error.put("code", "SERVER500");
            error.put("message", "게시글 삭제 중 서버 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(error);
        }
    }


}

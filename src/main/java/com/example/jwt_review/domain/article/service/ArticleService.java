package com.example.jwt_review.domain.article.service;

import com.example.jwt_review.domain.article.entity.Article;
import com.example.jwt_review.domain.article.repository.ArticleRepository;
import com.example.jwt_review.domain.member.entity.Member;
import com.example.jwt_review.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public RsData<Article> write(Member author, String subject, String content) {
        Article article = Article.builder()
                .author(author)
                .subject(subject)
                .content(content)
                .build();

        articleRepository.save(article);

        return RsData.of(
                "S-3",
                "게시물이 생성되었습니다.",
                article
        );
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }
}

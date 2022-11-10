package com.articleboard.controller;

import com.articleboard.domain.ArticleDto;
import com.articleboard.domain.entity.Article;
import com.articleboard.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/new")
    public String createArticlePage(){
        return "new";
    }

    @GetMapping("/{id}")
    public String selectSingle(@PathVariable Long id, Model model){
        Optional<Article> optArticle = articleRepository.findById(id);

        if(!optArticle.isEmpty()){
            model.addAttribute("article", optArticle.get());
            return "show";
        }
        else{
            return "error";
        }
    }

    @GetMapping("/list")
    public String selectAll(Model model){
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articles",articleList);
        return "list";
    }

    @PostMapping("")
    public String createArticle(ArticleDto articleDto){
        log.info(articleDto.getTitle());
        Article savedArticle = articleRepository.save(articleDto.toEntity());
        log.info("generatedID {}",savedArticle.getId());
        return String.format("redirected:/aritcles/%d",savedArticle.getId());
    }
}

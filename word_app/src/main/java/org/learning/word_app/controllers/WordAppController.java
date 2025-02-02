package org.learning.word_app.controllers;

import org.learning.word_app.entity.FrenchWord;
import org.learning.word_app.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class WordAppController {

    private final WordService wordService;

    @Autowired
    public WordAppController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping("/fr_words/add_word")
    public FrenchWord addWord(@RequestBody FrenchWord word) {
        return wordService.save(word);
    }

    @GetMapping("/fr_words")
    public List<FrenchWord> getAllWords() {
        return wordService.findAll();
    }

    @GetMapping("/fr_words/{id}")
    public FrenchWord getWordById(@PathVariable Long id) {
        return wordService.findById(id);
    }

    @PostMapping("/fr_words/delete_word")
    public void deleteWord(@RequestBody FrenchWord word) {
        wordService.delete(word.getId());
    }

    @PostMapping("/fr_words/update_word")
    public FrenchWord updateWord(@RequestBody FrenchWord word) {
        return wordService.update(word);
    }

    @GetMapping("/fr_words/similar_words")
    public List<FrenchWord> getSimilarWords(@RequestParam String word) {
        return wordService.findSimilarWords(word);
    }

}

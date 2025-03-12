package org.learning.word_app.controllers;

import org.learning.word_app.entity.FrenchWord;
import org.learning.word_app.entity.Gender;
import org.learning.word_app.entity.Topic;
import org.learning.word_app.entity.Type;
import org.learning.word_app.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5180")
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

    @GetMapping("/fr_words/all_words")
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

    @GetMapping("/enums/all")
    public Map<String, Object> getAllEnums() {
        Map<String, Object> enums = new HashMap<>();
        enums.put("types", Type.values());
        enums.put("genders", Gender.values());
        enums.put("topics", Topic.values());
        return enums; // Returns a JSON object with all enums
    }

}

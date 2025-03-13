package org.learning.word_app.service;

import org.learning.word_app.entity.FrenchWord;

import java.util.List;

public interface WordService {
    FrenchWord save(FrenchWord word);

    List<FrenchWord> findAll();

    FrenchWord findById(Long id);

    FrenchWord update(FrenchWord word);

    void delete(Long id);

    List<FrenchWord> findSimilarWords(String word);
}

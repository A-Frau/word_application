package org.learning.word_app.service;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.learning.word_app.entity.FrenchWord;
import org.learning.word_app.repository.FrenchWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordServiceAction implements WordService {

    private final FrenchWordsRepository frenchWordsRepository;

    @Autowired
    public WordServiceAction(FrenchWordsRepository frenchWordsRepository) {
        this.frenchWordsRepository = frenchWordsRepository;
    }

    @Override
    public FrenchWord save(FrenchWord word) {
        return frenchWordsRepository.save(word);
    }

    @Override
    public List<FrenchWord> findAll() {
        return frenchWordsRepository.findAll();
    }

    @Override
    public FrenchWord findById(Long id) {
        return frenchWordsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("French word not found with id: " + id));
    }

    @Override
    public FrenchWord update(FrenchWord word) {
        if (!frenchWordsRepository.existsById(word.getId())) {
            throw new RuntimeException("French word not found with id: " + word.getId());
        }
        return frenchWordsRepository.save(word);
    }

    @Override
    public void delete(Long id) {
        if (!frenchWordsRepository.existsById(id)) {
            throw new RuntimeException("French word not found with id: " + id);
        }
        frenchWordsRepository.deleteById(id);
    }

    @Override
    public List<FrenchWord> findSimilarWords(String word) {
        String lowerCaseWord = word.toLowerCase();
        List<FrenchWord> similarWords = frenchWordsRepository.findSimilarWords(lowerCaseWord);
        return similarWords.stream()
                .sorted(Comparator.comparingInt(f -> 
                    calculateLevenshteinDistance(lowerCaseWord, f.getFrench().toLowerCase())
                    ))
                .limit(10)
                .collect(Collectors.toList());
    }

    private int calculateLevenshteinDistance(String word1, String word2) {
        return LevenshteinDistance.getDefaultInstance().apply(word1, word2);
    }


}

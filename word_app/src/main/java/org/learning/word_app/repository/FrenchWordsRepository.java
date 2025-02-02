package org.learning.word_app.repository;

import org.learning.word_app.entity.FrenchWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FrenchWordsRepository extends JpaRepository<FrenchWord, Long> {
    @Query("SELECT f FROM FrenchWord f WHERE f.french LIKE %:word%")
    List<FrenchWord> findSimilarWords(@Param("word") String word);
}

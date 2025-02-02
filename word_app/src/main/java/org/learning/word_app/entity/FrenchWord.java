package org.learning.word_app.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "dictionary_french")
public class FrenchWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "french")
    private String french;

    @Column(name = "pronunciation")
    private String pronunciation;

    @Column(name = "english")
    private String english;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "word_type")
    private WordType wordType;

    @Enumerated(EnumType.STRING)
    @Column(name = "topic")
    private Topic topic;

    @Column(name = "notes")
    private String notes;

    public FrenchWord() {
    }

    public FrenchWord(String french, String pronunciation, String english, Gender gender, WordType wordType, Topic topic, String notes) {
        this.french = french;
        this.pronunciation = pronunciation;
        this.english = english;
        this.gender = gender;
        this.wordType = wordType;
        this.topic = topic;
        this.notes = notes;
    }

    // Getters and setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrench() {
        return french;
    }

    public void setFrench(String french) {
        this.french = french;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public WordType getWordType() {
        return wordType;
    }

    public void setWordType(WordType wordType) {
        this.wordType = wordType;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FrenchWord that)) return false;
        return Objects.equals(getFrench(), that.getFrench());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFrench());
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", french='" + french + '\'' +
                ", pronunciation='" + pronunciation + '\'' +
                ", english='" + english + '\'' +
                ", gender=" + gender +
                ", wordType=" + wordType +
                ", topic=" + topic +
                ", notes='" + notes + '\'' +
                '}';
    }
}

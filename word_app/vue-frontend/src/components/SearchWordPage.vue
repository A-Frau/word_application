<script setup>
import { ref, onMounted, watch } from "vue";
import axios from "axios";
import { ArrowLeft } from 'lucide-vue-next';
import { useRouter } from 'vue-router';

const router = useRouter();

const goBack = () => {
  router.go(-1);
};

const words = ref([]);
const allWords = ref([]);
const searchWords = ref("");

const formatEnum = (value) => {
  return value
    .toLowerCase()             // Step 1: Convert the whole string to lowercase
    .replace(/_/g, ' ')        // Step 2: Replace underscores with spaces
    .replace(/\b\w/g, char => char.toUpperCase()); // Step 3: Capitalize each word
};

const fetchWords = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/fr_words/all_words');
    const formattedWords = response.data.map(word => ({
      word: word.french,
      translation: word.english,
      pronunciation: word.pronunciation || "No pronunciation available.",
      type: word.type,
      topic: word.topic,
      gender: word.gender,
      example: word.note,
      showDetails: false
    }));
    words.value = formattedWords;
    allWords.value = formattedWords;
  } catch (error) {
    console.error('Error fetching words:', error);
    alert('Failed to fetch words. Please try again.');
  }
};

const toggleDetails = (index) => {
  if (words.value[index]) {
    words.value[index].showDetails = !words.value[index].showDetails;
  } else {
    console.error(`Error: Invalid index ${index}`);
  }
};

const searchWord = async() => {
  if (!searchWords.value) {
    // If search is empty, show all words
    words.value = allWords.value;
    return;
  }

  try {
    const response = await axios.get('http://localhost:8080/api/fr_words/similar_words', {params: { word: searchWords.value }});
    console.log('Response:', response.data);
    words.value = response.data.map(word => ({
      word: word.french,
      translation: word.english,
      pronunciation: word.pronunciation || "No pronunciation available.",
      type: word.type,
      topic: word.topic,
      gender: word.gender,
      example: word.note,
      showDetails: false
    }));
  } catch(error) {
    console.error('Error adding word:',error);
    alert('Failed to fetch similar words. Please try again.');
  }
}

// Automatically search when typing
watch(searchWords, searchWord);

onMounted(() => {
  fetchWords();
});

</script>

<template>
  <div class="container">
    <div class="back-button" @click="goBack">
      <ArrowLeft size="24" stroke-width="2" />
      <span>Back</span>
    </div>
    <h2>Search</h2>
    <div class="search-box">
      <input v-model="searchWords" placeholder="Search word..." type="text">
    </div>

    <ul id="wordList" class="word-list">
      <li v-for="(word,index) in words" :key="word.word" @click="toggleDetails(index)">
        {{ word.word }}
        <div v-if="word.showDetails" class="details">
          <div v-if="word.translation"><strong>Translation:</strong> {{ word.translation }}</div>
          <div v-if="word.pronunciation"><strong>Pronunciation:</strong> {{ word.pronunciation }}</div>
          <div v-if="word.type"><strong>Type:</strong> {{ formatEnum(word.type) }}</div>
          <div v-if="word.topic"><strong>Topic:</strong> {{ formatEnum(word.topic) }}</div>
          <div v-if="word.gender"><strong>Gender:</strong> {{ formatEnum(word.gender) }}</div>
          <div v-if="word.example"><strong>Example:</strong> {{ word.example }}</div>
        </div>
      </li>
      <li v-if="words.length === 0 && searchWords">
        No words found.
      </li>
    </ul>
  </div>
</template>

<style scoped>

.container {
  width: 300px;
  height: 700px;
  margin: 0 auto;
  border: 1px solid #ccc;
  background-color: #ffd1dc;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}

.search-box {
  margin-bottom: 10px;
}

.search-box input {
  width: 100%;
  padding: 10px;
  margin: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.word-list {
  margin-top: 10px;
  list-style-type: none;
  padding: 0;
}

.word-list li {
  padding: 8px 10px;
  border-bottom: 1px solid #ddd;
  cursor: pointer;
  margin: 5px;
  background-color: #ff7998eb;
  border-radius: 5px;
}

.details {
  margin-top: 5px;
  padding: 5px;
  background-color: #f9f9f9;
  border: 1px solid #eee;
  border-radius: 5px;
}

.back-button {
  display: flex;
  align-items: center;
  cursor: pointer;
  color: #ff69b4;
  font-weight: 500;
  gap: 5px;
}

.back-button:hover {
  color: #ff1493;
}
</style>

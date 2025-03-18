<script setup>
import {ref, onMounted, computed} from "vue";
import axios from "axios"
import { ArrowLeft } from 'lucide-vue-next';
import { useRouter } from 'vue-router';

const router = useRouter();
const apiKey = import.meta.env.VITE_DEEPL_API_KEY;
const goBack = () => {
  router.go(-1);
};

const formData = ref({
  french: "",
  pronunciation: "",
  english: "",
  type: "",
  topic: "",
  gender: "",
  notes: ""
});

// Suggest translation state
const suggestedTranslation = ref("");

const translateToEnglish = async () => {
  if (!formData.value.french.trim()) {
    alert("Please enter a French word first.");
    return;
  }
  try {
    const response = await axios.post('https://api-free.deepl.com/v2/translate', null, {
      params: {
        auth_key: apiKey,
        text: formData.value.french,
        source_lang: 'FR',
        target_lang: 'EN'
      }
    });
    suggestedTranslation.value = response.data.translations[0].text;
  } catch (error) {
    console.error("Error translating word:", error);
    alert("Failed to fetch translation. Please check your network or API key.");
  }
};

const useSuggestedTranslation = () => {
  formData.value.english = suggestedTranslation.value;
  suggestedTranslation.value = "";
};

const types = ref([]);
const genders = ref([]);
const topics = ref([]);

// Validation: Check if French & English fields are filled
const isFormValid = computed(() => {
  return formData.value.french.trim() !== "" && formData.value.english.trim() !== "";
});

// Fetch all enums in one request
const fetchEnums = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/enums/all');
    console.log('Fetched Enums:', response.data);
    types.value = response.data.types || [];
    genders.value = response.data.genders || [];
    topics.value = response.data.topics || [];

    // Set default values
    formData.value.type =  '';
    formData.value.gender =  '';
    formData.value.topic = '';
  } catch (error) {
    console.error('Error fetching enums:', error);
    alert('Failed to fetch data. Please try again.');
  }
};

onMounted(() => {
  fetchEnums();
});

const formatEnum = (value) => {
  return value
    .toLowerCase()             // Step 1: Convert the whole string to lowercase
    .replace(/_/g, ' ')        // Step 2: Replace underscores with spaces
    .replace(/\b\w/g, char => char.toUpperCase()); // Step 3: Capitalize each word
};


const submitForm = async () => {
  try {
    const formattedData = {
      ...formData.value,
      topic: formData.value.topic ? formData.value.topic.replace(/ /g, '_').toUpperCase() : null,
      type: formData.value.type ? formData.value.type.toString().toUpperCase() : null,
      gender: formData.value.gender ? formData.value.gender.toString().toUpperCase() : null    };

    const response = await axios.post("http://localhost:8080/api/fr_words/add_word", formattedData);
    console.log('Response:', response.data);
    alert('Word added successfully!');

    // Clear form data after successful submission
    formData.value = {
      french: "",
      pronunciation: "",
      english: "",
      type: '',
      topic: '',
      gender: '',
      notes: ""
    };

  } catch (error) {
    console.error('Error adding word:',error);
    if (error.response) {
      alert(`Failed to add word: ${error.response.data.message}`);
    } else {
      alert('Failed to add the word. Please try again.');
    }
  }
};
</script>

<template>
  <div class="form-container">
    <div class="back-button" @click="goBack">
      <ArrowLeft size="24" stroke-width="2" />
      <span>Back</span>
    </div>
    <h2>Add Vocabulary</h2>
    <form @submit.prevent="submitForm">
      <label for="french">French</label>
      <input v-model="formData.french" @blur="translateToEnglish" placeholder="Enter French word">

      <label for="pronunciation">Pronunciation</label>
      <input v-model="formData.pronunciation" placeholder="Enter pronunciation">

      <label for="english">English</label>
      <input v-model="formData.english" placeholder="Enter English meaning">

      <!-- Suggested Translation Section -->
      <div v-if="suggestedTranslation" class="suggestion">
        <p><strong>Suggested Translation:</strong> {{ suggestedTranslation }}</p>
        <button type="button" @click="useSuggestedTranslation">Use this suggestion</button>
      </div>

      <label for="type">Type</label>
      <select v-model="formData.type">
        <option v-for="type in types" :key="type" :value="type">
          {{ formatEnum(type) }}
        </option>
      </select>

      <label for="topic">Topic</label>
      <select v-model="formData.topic" id="topic">
        <option v-for="topic in topics" :key="topic" :value="topic">
          {{ formatEnum(topic) }}
        </option>
      </select>

      <label for="gender">Gender</label>
      <select v-model="formData.gender" id="gender">
        <option v-for="gender in genders" :key="gender" :value="gender">
          {{ formatEnum(gender) }}
        </option>
      </select>

      <label for="notes">Note</label>
      <input v-model="formData.notes" placeholder="Additional note..." >

      <button type="submit" :disabled="!isFormValid">Add</button>
    </form>
  </div>
</template>

<style scoped>
h2 { color: rgba(152, 10, 126, 0.952);}
label {color:  rgba(152, 10, 126, 0.952);}

.form-container {
  width: 300px;
  height: 700px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  background-color: #ffd1dc;
  box-shadow: 2px 2px 5px lightpink;
  border-radius: 8px;
}

.form-container h2 {
  text-align: center;
}

.form-container label {
  display: block;
  margin-top: 10px;
}

.form-container input,
.form-container select {
  background-color: white;
  width: 90%;
  padding: 8px;
  margin-top: 5px;
  border: 2px #c614b7;
  border-radius: 4px;
  color: #010001;
}

.form-container button {
  margin-top: 15px;
  width: 100%;
  padding: 10px;
  background-color: hotpink;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.form-container button:hover {
  background-color: deeppink;
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

button:disabled {
  background-color: #d3d3d3; /* Grey for disabled */
  cursor: not-allowed;      /* Cursor indicates the button is disabled */
  opacity: 0.6;             /* Faded look */
}
</style>

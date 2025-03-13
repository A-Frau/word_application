import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../components/HomePage.vue';
import AddWordPage from '../components/AddWordPage.vue';
import SearchWordPage from '../components/SearchWordPage.vue';

const routes = [
  { path: '/', component: HomePage },
  { path: '/add_word', component: AddWordPage },
  { path: '/search_word', component: SearchWordPage }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;

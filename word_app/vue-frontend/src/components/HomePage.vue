<script setup>

</script>

<template>
  <div class="container">
    <h1>Bonjour!!!</h1>
    <h2>🌟 Bienvenue dans votre voyage linguistique ! 🌍</h2>
    <div class="buttons">
      <button><a href="/add_word">Add</a></button>
      <button><a href="/search_word">Search</a></button>
    </div>
  </div>
</template>

<style scoped>
h2 {  color:  rgba(152, 10, 126, 0.952);}
h1 {  color:  rgba(152, 10, 126, 0.952);}


.container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 700px;
  background-color: #ffd1dc;
  flex-direction: column;
  border-radius: 8px;
  width: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  box-shadow: 2px 2px 5px lightpink;
}

.buttons {
  display: flex;
  justify-content: space-between;
  width: 200px;
}

.buttons button {
  width: 80px;
  height: 40px;
  font-size: 16px;
  background-color: #ff69b4;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition:  0.3s ease;
}

.buttons button:hover {
  background-color: #ff1493;
}

.buttons a {
  text-decoration: none;
  color: white;
  display: block;
  width: 100%;
  height: 100%;
  line-height: 40px;
}

</style>

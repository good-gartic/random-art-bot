<template>
  <div class="card">
    <h3 class="card-title">Import links from JSON</h3>
    <form @submit="upload()">
      <div class="custom-file">
        <input type="file" accept="application/json" required>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "Import",
  methods: {
    upload(event) {
      event.preventDefault()
    }
  }
}
</script>
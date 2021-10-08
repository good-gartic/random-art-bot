<template>
  <div class="card">
    <h3 class="card-title">Import links from JSON</h3>
    <form @submit="upload($event)">
      <div class="row">
        <div class="col-sm-12 mb-10" v-if="message !== null">
          <div :class="`alert ${error ? 'alert-danger' : 'alert-success'}`">
            <h4 class="alert-heading">{{ error ? "Import failed" : "Import successful" }}</h4>
            {{ message }}
          </div>
        </div>
        <div class="col-sm-12 col-md-2">
          <div class="custom-file">
            <input type="file" accept="application/json" required id="file-input" @change="fileChanged($event)">
            <label for="file-input">Choose a file</label>
          </div>
        </div>
        <div class="col-sm-12 col-md-10">
          <button class="btn btn-primary" @click="upload($event)" :disabled="file === null">Import</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import api from "@/api"

export default {
  name: "Import",
  data: () => ({
    file: null,
    error: null,
    message: null,
  }),
  methods: {
    fileChanged(event) {
      const files = event.target.files || event.dataTransfer.files;

      this.file = null;

      if (files.length > 0) {
        this.file = files[0]
      }
    },
    async upload(event) {
      event.preventDefault()
      if (this.file !== null) {
        const {error, message} = await api.importJson(this.file)

        this.error = error;
        this.message = message;
      }
    }
  }
}
</script>
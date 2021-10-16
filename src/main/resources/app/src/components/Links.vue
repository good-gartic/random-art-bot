<template>
  <div class="card">
    <div class="card-title">
      <h1>Image links</h1>
    </div>
    <div class="row">
      <div class="col-sm-12">
        <button class="btn" @click="loadEntries()">Reload</button>
        <button class="ml-20 btn btn-primary" @click="approveAll()">Approve all</button>
      </div>
      <div class="col-sm-12 col-md-4 col-lg-3" v-for="(item, i) in images" :key="i">
        <div class="card p-0">
          <div class="h-250" :style="`background: url('${item.image}'); background-size: contain; background-repeat: no-repeat`"></div>
          <div class="p-10">
            <div v-if="item.approved">
              <div class="pb-10">
                <small class="text-success">Approved</small>
              </div>
              <button class="ml-5 btn btn-danger" @click="this.delete(item.id)">Delete</button>
            </div>
            <div v-else>
              <div class="pb-10">
                <small class="text-danger">Awaiting approval</small>
              </div>
              <button class="btn btn-success" @click="this.approve(item.id)">Approve</button>
              <button class="ml-5 btn btn-danger" @click="this.delete(item.id)">Delete</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api"

export default {
  name: "Links",
  data: () => ({
    images: [],
    loading: false
  }),
  async mounted() {
    await this.loadEntries()
  },
  methods: {
    async loadEntries() {
      this.loading = true;

      this.images = await api.fetchLinksPage();
      this.loading = false;
    },
    async approve(id) {
      await api.approve(id);
      await this.loadEntries();
    },
    async delete(id) {
      await api.delete(id);
      await this.loadEntries();
    },
    async approveAll() {
      await api.approveAll();
      await this.loadEntries();
    }
  }
}
</script>

<style scoped>

</style>
<template>
  <div class="card">
    <div class="card-title">
      <h1>Image links</h1>
    </div>
    <div class="row">
      <div class="col-sm-12">
        <button class="btn" :disabled="page === 0" @click="prevPage()">&laquo;</button>
        <div class="btn disabled">Page {{ page + 1 }}</div>
        <div class="btn" @click="reloadPage()">Reload</div>
        <button class="btn" @click="nextPage()">&raquo;</button>
      </div>
      <div class="col-sm-12 col-md-4 col-lg-3" v-for="(item, i) in images" :key="i">
        <div class="card p-0">
          <div class="h-250" :style="`background: url('${item.image}'); background-size: cover`"></div>
          <div class="p-10">
            <div v-if="item.approved">
              <div class="pb-10">
                <small class="text-success">Approved</small>
              </div>
              <button class="ml-5 btn btn-danger" @click="this.delete(item.id)">Delete</button>
            </div>
            <div>
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
    page: 0,
    images: [],
    loading: false
  }),
  async mounted() {
    await this.reloadPage()
  },
  methods: {
    async prevPage() {
      this.page--;
      await this.reloadPage();
    },
    async nextPage() {
      this.page++;
      await this.reloadPage();
    },
    async reloadPage() {
      this.loading = true;

      const response = await api.fetchLinksPage(this.page)

      this.images = response.content;
      this.loading = false;
    },
    async approve(id) {
      console.log(id);
      await this.reloadPage();
    },
    async delete(id) {
      console.log(id);
      await this.reloadPage();
    }
  }
}
</script>

<style scoped>

</style>
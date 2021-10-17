<template>
  <div class="card">
    <div class="card-title">
      <h1>Image links</h1>
    </div>
    <div class="row">
      <div class="col-sm-12">
        <button class="btn" :disabled="page === 0" @click="prevPage()">&laquo; Page {{ page }}</button>
        <div class="btn" @click="reloadPage()">Reload</div>
        <button class="ml-20 btn btn-primary" @click="approveAll()">Approve all</button>
      </div>
      <div id="top"></div>
      <div class="col-sm-12" v-if="images.length === 0">
        <h2 class="text-muted">There are no images imported yet</h2>
      </div>
      <div class="col-sm-12 col-md-4 col-lg-3" v-else v-for="(item, i) in images" :key="i">
        <div class="card p-0">
          <div class="h-250"
               :style="`background: url('${item.image}'); background-size: contain; background-repeat: no-repeat`"></div>
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
      <div class="col-sm-12">
        <button class="btn" @click="nextPage()">&raquo; Page {{ page + 2 }}</button>
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
      await document.getElementById("top").scrollIntoView({ behavior: "smooth" });
    },
    async reloadPage() {
      this.loading = true;

      const response = await api.fetchLinksPage(this.page)

      this.images = response.content;
      this.loading = false;
    },
    async approve(id) {
      await api.approve(id);
      await this.reloadPage();
    },
    async delete(id) {
      await api.delete(id);
      await this.reloadPage();
    },
    async approveAll() {
      await api.approveAll();
      await this.reloadPage();
    }
  }
}
</script>

<style scoped>

</style>
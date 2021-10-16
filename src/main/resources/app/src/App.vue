<template>
  <div>
    <div class="container">
      <h1>Gartic art links management</h1>
    </div>
    <Import/>
    <Links/>
  </div>
</template>

<script>

import Import from "@/components/Import";
import Links from "@/components/Links";
export default {
  name: 'App',
  components: {Links, Import},
}
</script>

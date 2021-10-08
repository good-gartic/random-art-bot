<template>
  <div>
    <h1>Gartic art links management</h1>
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

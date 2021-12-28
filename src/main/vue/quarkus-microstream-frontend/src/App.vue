<template>
  <persons-table />
</template>

<script lang="ts">
import PersonsTable from './components/PersonsTable.vue';
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

import { defineComponent } from "vue";
export default defineComponent({
  components: {
    PersonsTable
  },
  name: "App"
});

</script>
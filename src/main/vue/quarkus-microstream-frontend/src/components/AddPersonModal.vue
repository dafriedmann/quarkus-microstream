<template>
    <form>
        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" v-model="name">
            <label for="name" class="form-label">Prename</label>
            <input type="text" class="form-control" id="prename" v-model="prename">
        </div>
        <!-- <div class="mb-3">
            <label for="name" class="form-label">Adresse</label>
            <input type="text" class="form-control" id="name">
        </div> -->
        <button type="submit" class="btn btn-primary" @click="submit">Submit</button>
    </form>
</template>
<script lang="ts">
import {PersonService} from "../services/PersonService"
import { defineComponent } from "vue";
export default defineComponent({
    data() {
        return {
            prename: '',
            name: ''
        }
    },
    methods: {
        submit() {
            let service = new PersonService();
            service.addPerson({"prename" : this.prename, "name" : this.name})
        }
    }
})
</script>
<style>

</style>
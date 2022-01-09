<template>
    <form>
        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" v-model="person.name">
            <label for="name" class="form-label">Prename</label>
            <input type="text" class="form-control" id="prename" v-model="person.prename">
        </div>
        <!-- <div class="mb-3">
            <label for="name" class="form-label">Adresse</label>
            <input type="text" class="form-control" id="name">
        </div> -->
        <button type="button" class="btn btn-primary" @click="submit">Submit</button>
    </form>
</template>
<script lang="ts">
import {PersonService} from "../services/PersonService"
import { defineComponent } from "vue";
import { Person } from "@/data/Person";
import { closeModal } from "jenesius-vue-modal";
export default defineComponent({
    data() {
        return {
            person : {} as any,
        }
    },
    methods: {
        submit() {
            let service = new PersonService();
            service.addPerson(this.person as Person).then(res => closeModal());
        }
    }
})
</script>
<style>

</style>
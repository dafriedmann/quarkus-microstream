<template>
  <div class="container">
    <button class="btn btn-primary" @click="addPerson">Add new person</button>
    <table class="table" name="personstable">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Prename</th>
          <th>Address</th>
          <th>Date of birth</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="person in persons" :key="person.id">
            <td>{{ person['id'] }}</td>
            <td>{{ person['name'] }}</td>
            <td>{{ person['prename'] }}</td>
            <td>{{ person['address'] }}</td>
            <td>{{ person['dateOfBirth'] }}</td>
            <td><button class="btn btn-danger" @click="removePerson(person['id'])">X</button></td>
        </tr>
      </tbody>
    </table>

  <!-- Modal - Add Person -->
  <widget-container-modal />
  </div>
</template>
<script lang="ts">
import { defineComponent } from 'vue'
import {openModal} from "jenesius-vue-modal";
import AddPersonModal from "./AddPersonModal.vue";
import {container} from "jenesius-vue-modal";
import {PersonService} from "../services/PersonService"

export default defineComponent({
  components: {WidgetContainerModal: container},
  data() {
    return {
      persons: [],
      showModal: false
    }
  },
  methods: {
    fetchData(){
       let service = new PersonService();
       service.getPersons().then(a => this.persons = a);
    },
    addPerson() {
      openModal(AddPersonModal, {title: "Hello"})
    },
    removePerson(id : number) {
      let service = new PersonService();
      service.removePersonById(id);
      this.$forceUpdate();
    }
  },
  mounted() {
    this.fetchData()
  },
});
</script>
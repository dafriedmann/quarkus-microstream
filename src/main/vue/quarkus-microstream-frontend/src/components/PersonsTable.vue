<template>
  <div class="container" style="margin-top:1em">
    <button class="btn btn-primary" @click="addPerson()">Add new person</button>
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
import {closeModal, openModal} from "jenesius-vue-modal";
import AddPersonModal from "./AddPersonModal.vue";
import {container} from "jenesius-vue-modal";
import {PersonService} from "../services/PersonService"
import {Person} from '../data/Person';

const SERVICE = new PersonService();

export default defineComponent({
  components: { WidgetContainerModal: container },
  properties: { service: new PersonService() },
  data() {
    return {
      showModal: false,
      persons: [] as Person[]
    }
  },
  mounted() {
     this.fetchData();
  },
  methods: {
    async addPerson() {
      let modal = await openModal(AddPersonModal);
      modal.onclose = () => {
          this.fetchData();
          return true;
      }
    },
    removePerson(id : number) {
      SERVICE.removePersonById(id).then( res => {
        this.persons = this.persons.filter(p => p.id !== id);
      }).catch(err => console.error(err.status));
    },
    fetchData() {
      SERVICE.getPersons().then(p => this.persons = p).catch(err => console.error(err));
    }
  }
});
</script>
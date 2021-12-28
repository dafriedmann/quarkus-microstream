import axios from "axios";
import {Person} from '../data/Person';

export class PersonService {
    
    public async getPersons() {
        return await axios.get("http://localhost:8080/persons/all").then((response) => response.data).catch(error => console.log(error.reponse));
    }

    public async addPerson(person : Person) {
        await axios.post("http://localhost:8080/persons/add", person).catch(error => console.log(error.reponse));
    }

    public async removePersonById(id : number) {
        await axios.delete("http://localhost:8080/persons/delete/" + id).catch(error => console.log(error.reponse));
    }

}

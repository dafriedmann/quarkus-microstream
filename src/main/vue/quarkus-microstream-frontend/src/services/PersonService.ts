import { Config } from "@/data/Config";
import axios, { AxiosResponse } from "axios";
import {Person} from '../data/Person';

export class PersonService {

    public static readonly CONFIG : Config = require('/public/config.json');
    
    public async getPersons() : Promise<Person[]> {
        return axios.get(PersonService.CONFIG.quarkusBackendURL +  "persons/all").then((response) => response.data).catch(error => console.log(error.reponse));
    }

    public async addPerson(person : Person) : Promise<Person> {
        await axios.post(PersonService.CONFIG.quarkusBackendURL + "persons/add", person).catch(error => console.log(error.reponse));
        return person;
    }

    public async removePersonById(id : number) {
        let status = 500;
        await axios.delete(PersonService.CONFIG.quarkusBackendURL + "persons/delete/" + id).then(res => status = res.status).catch(error => console.log(error.reponse));
        return status;
    }

}

import axios from "axios";

class CityService {

    url = "http://localhost:8080/api/v1/city";

    fetchCountries() {
        return (axios.get(`${this.url}/all-countries`));
    }

    fetchCities(country) {
        return (axios.get(`${this.url}/${country}`));
    }
}

const citiesService = new CityService();
export default citiesService;
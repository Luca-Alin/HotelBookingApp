class CitiesReader {
    constructor() {
        console.log("OK");

        const fileInput = document.createElement("input");
        fileInput.type = "file";
        fileInput.accept = ".csv";
        fileInput.addEventListener("change", (e) => {
            const file = e.target.files[0];

            const reader = new FileReader();
            reader.readAsText(file);
        });
    }


}

const citiesReader = new CitiesReader();
export default citiesReader;
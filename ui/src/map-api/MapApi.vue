<script setup>
import {onMounted, ref, watch} from "vue";
import cityService from "@/map-api/CityService.js";

var canvas = null;


function latLngToCoordinates(latitude, longitude, width, height) {
  const x = ((longitude + 180) / 360) * width;
  const y = ((90 - latitude) / 180) * height;
  const roundedX = Math.round(x);
  const roundedY = Math.round(y);
  console.log(`X: ${roundedX}, Y: ${roundedY}`);


  canvas = document.getElementById("world-map");
  var ctx = canvas.getContext("2d");
  ctx.fillRect(roundedX - 5, roundedY - 5, 10, 10);
}

function coordinatesToLatLng(x, y, width, height) {
  const longitude = (x / width) * 360 - 180;
  const latitude = 90 - (y / height) * 180;
  const roundedLongitude = longitude.toFixed(2);
  const roundedLatitude = latitude.toFixed(2);
  console.log(`Latitude: ${roundedLatitude}, Longitude: ${roundedLongitude}`);

}


var countries = ref(null);
var cities = ref(null);

onMounted(() => {
  var map = document.getElementById("world-map");
  map.addEventListener("click", function (evt) {
    coordinatesToLatLng(evt.offsetX, evt.offsetY, map.offsetWidth, map.offsetHeight);
  });

  cityService.fetchCountries()
      .then(res => countries.value = res.data);

  canvas = document.getElementById("world-map");
  var ctx = canvas.getContext("2d");

  var img = new Image();
  img.src = "/src/assets/world.svg";
  console.log(img);
  img.onload = function () {
    ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
  };


});


var currentCountry = ref(null);
watch(currentCountry, _ => {
  cityService.fetchCities(currentCountry.value)
      .then(res => cities.value = res.data);
});

var currentCity = ref(null);
watch(currentCity, _ => {
  latLngToCoordinates(currentCity.value.lat, currentCity.value.lng, canvas.width, canvas.height);
});
</script>

<template>

  <div>
    <div class="d-flex border border-1 border-dark">
      <div v-if="countries">
        <select name="countries" id="countries" v-model="currentCountry">
          <option>none</option>
          <option v-for="country in countries" :value="country">{{ country }}</option>
        </select>
      </div>
      <div>
        <select name="cities" id="cities" v-model="currentCity">
          <option>none</option>
          <option v-for="city in cities" :value="city">{{ city.city }}</option>
        </select>
      </div>
      <input type="range" min="10" max="120">
    </div>
    <div>
      <canvas id="world-map" width="1200" height="600" class="border border-1"></canvas>
    </div>
  </div>


</template>

<style scoped>

</style>
<script setup>
import {onMounted} from "vue";

function latLngToCoordinates(latitude, longitude, width, height) {
  const x = ((longitude + 180) / 360) * width;
  const y = ((90 - latitude) / 180) * height;
  const roundedX = Math.round(x);
  const roundedY = Math.round(y);
  console.log(`X: ${roundedX}, Y: ${roundedY}`);
}

function coordinatesToLatLng(x, y, width, height) {
  const longitude = (x / width) * 360 - 180;
  const latitude = 90 - (y / height) * 180;
  const roundedLongitude = longitude.toFixed(2);
  const roundedLatitude = latitude.toFixed(2);
  console.log(`Latitude: ${roundedLatitude}, Longitude: ${roundedLongitude}`);

  latLngToCoordinates(longitude, latitude, width, height);
}

onMounted(() => {
  var map = document.getElementById("world-map");
  map.addEventListener("click", function (evt) {
    coordinatesToLatLng(evt.offsetX, evt.offsetY, map.offsetWidth, map.offsetHeight);
  });
});
</script>

<template>
  <div style="width: 800px; height: 400px" class="overflow-scroll">
    <img id="world-map" src="../assets/world.svg"
         class="border border-1 border-dark m-0 p-0"
         alt="" style="width: 16000px; height: 8000px">
  </div>
</template>

<style scoped>

</style>
<template>
  <div class="slider">
    <div class="container-slider">
      <div class="img-box"></div>
      <p class="paragraph-box h1"></p>
    </div>
  </div>
</template>

<script>
import { onMounted, watch, nextTick } from "vue";
import { setPathFile } from "../../util/Utilities.js";
export default {
  name: "Cuentos",
  props: {
    model: {
      type: Object,
      required: true,
    },
    level: {
      type: Number,
      required: true,
    },
    timeStart: {
      type: Boolean,
    },
    movimientos: {
      type: Number,
    },
  },
  emits: [
    "startTime",
    "movesCounter",
    "displayModal",
    "stopTime",
    "resetEverything",
    "movValid",
  ],
  setup(props, context) {
    const story = [];
    var i = 0;
    var cont = 0;
    var slider_img;
    let paragraphbox;
    let imgbox;

    watch(
      () => props.movimientos,
      (movimientos, movimientosprev) => {
        if (movimientosprev !== 0 && movimientos === 0) {
          init();
          return;
        }

        if (movimientos < movimientosprev) {
          prev();
        } else if (movimientos > movimientosprev) {
          next();
        }
      }
    );

    onMounted(async () => {
      await nextTick();
      for (const iterator of props.model.detalles) {
        story.push({
          image: setPathFile(iterator.image),
          paragraph: iterator.paragraph,
        });
      }
      init();
    });

    function prev() {
      if (cont === story.length) {
        return win();
      } else {
        cont--;
        if (i <= 0) i = story.length;
        i--;
        return setImg(), paragraph();
      }
    }

    function next() {
      if (cont === story.length) {
        return win();
      } else {
        cont++;
        if (i >= story.length - 1) i = -1;
        i++;
        return setImg(), paragraph();
      }
    }

    function setImg() {
      if (story[i] === story[0]) {
        i++;
      }
      return slider_img.setAttribute("src", story[i].image);
    }

    function paragraph() {
      return (paragraphbox.textContent = story[i].paragraph);
    }
    function init() {
      i = 0;
      cont = 0;
      imgbox = document.querySelector(".img-box");
      imgbox.innerHTML = "";
      paragraphbox = document.querySelector(".paragraph-box");
      const addImage = document.createElement("IMG");
      addImage.classList.add("slider-img");
      addImage.setAttribute("src", story[0].image);
      imgbox.appendChild(addImage);
      paragraphbox.textContent = story[0].paragraph;
      slider_img = document.querySelector(".slider-img");
    }

    function win() {
      context.emit("displayModal");
    }
  },
};
</script>

<style>
.slider {
}

.container-slider {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  margin-top: 0.5em;
}
.paragraph-box {
  margin-left: 2em;
}

.img-box img {
  width: 100%;
}
</style>
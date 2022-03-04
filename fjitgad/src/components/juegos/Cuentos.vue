<template>
  <div class="container">
    <div class="row">
      <div class="col-7">
        <p
          id="parrafo_cuento"
          class="h1"
          style="color: black; white-space: pre-line"
        ></p>
      </div>
      <div class="col-5">
        <div class="row">
          <img
            id="imagen_cuento"
            alt="..."
            style="width: 100%; max-height: 100%"
          />
        </div>
        <div class="row">
          <video
            id="video_cuento"
            style="width: 50%"
            controls="controls"
            autoplay
          >
            <source src="" type="video/*" />
            Your browser does not support HTML5 video.
          </video>
        </div>
      </div>
    </div>
    <div class="row">
      <audio
        id="audio_cuento"
        style="width: 100%"
        controls="controls"
        autoplay
        controlsList="nodownload"
      >
        <source src="" type="audio/*" />
      </audio>
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
    var parrafoActual = 0;
    var audio_cuento;
    var video_cuento;
    let parrafo_cuento;
    let imagen_cuento;

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
          audio: setPathFile(iterator.audio_parag),
          video: setPathFile(iterator.video_parag),
        });
      }
      imagen_cuento = document.getElementById("imagen_cuento");
      audio_cuento = document.getElementById("audio_cuento");
      parrafo_cuento = document.getElementById("parrafo_cuento");
      video_cuento = document.getElementById("video_cuento");
      init();
    });

    function prev() {
      if (parrafoActual > 0) {
        parrafoActual--;
        setParagraph(story[parrafoActual]);
      }
    }

    function next() {
      if (parrafoActual + 1 === story.length) {
        return win();
      }
      parrafoActual++;
      setParagraph(story[parrafoActual]);
    }

    function init() {
      parrafoActual = 0;

      setParagraph(story[parrafoActual]);

      context.emit("startTime");
    }

    function setParagraph(paragraph) {
      if (paragraph.image && paragraph.image.length > 0) {
        imagen_cuento.setAttribute("src", paragraph.image);
        imagen_cuento.style.display = "block";
      } else {
        imagen_cuento.setAttribute("src", "");
        imagen_cuento.style.display = "none";
      }

      if (paragraph.audio && paragraph.audio.length > 0) {
        audio_cuento.setAttribute("src", paragraph.audio);
        audio_cuento.style.display = "block";
      } else {
        audio_cuento.setAttribute("src", "");
        audio_cuento.style.display = "none";
      }

      if (paragraph.video && paragraph.video.length > 0) {
        video_cuento.setAttribute("src", paragraph.video);
        video_cuento.style.display = "block";
      } else {
        video_cuento.setAttribute("src", "");
        video_cuento.style.display = "none";
      }
      parrafo_cuento.textContent = paragraph.paragraph;
    }

    function win() {
      context.emit("displayModal");
    }
  },
};
</script>
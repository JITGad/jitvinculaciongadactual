<template>
  <main-layout-juego :backgroundBlanc="true">
    <div class="dashboard-juego">
      <my-loading v-if="Loading" />
      <div v-else class="container" style="margin-top: 10vh">
        <encabezado-juego :routes="Routes" />
        <div class="page-current-juego">
          <div class="row">
            <informacion-juego
              :minutos="minutes"
              :movimientos="movimientos"
              :puntaje="puntaje"
              :segundos="seconds"
              :titulo="model.name"
            />
          </div>
          <div class="row pt-4">
            <rompecabezas
              v-if="model.shortname == 'rompecabezas'"
              :model="model"
              :level="Nivel"
              :timeStart="timeStart"
              @startTime="timer"
              @movesCounter="movesCounter"
              @displayModal="displayModal"
              @stopTime="stopTime"
              @resetEverything="resetEverything"
            />
            <memoria
              v-if="model.shortname == 'memoria'"
              :model="model"
              :level="Nivel"
              :timeStart="timeStart"
              @startTime="timer"
              @movesCounter="movesCounter"
              @displayModal="displayModal"
              @stopTime="stopTime"
              @resetEverything="resetEverything"
            />
            <emparejar
              v-if="model.shortname == 'emparejar'"
              :model="model"
              :level="Nivel"
              :timeStart="timeStart"
              @startTime="timer"
              @movesCounter="movesCounter"
              @displayModal="displayModal"
              @stopTime="stopTime"
              @resetEverything="resetEverything"
            />
          </div>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <section class="win-game-modal">
      <div class="modal fade" ref="ModalVictoria">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">Enhorabuena</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <p>Has ganado el juego completando el rompecabeza</p>
              <p>
                Tiempo en completar: {{ minutes }} Minutos y
                {{ seconds }} Segundos
              </p>
              <p>Movimientos {{ movimientos }}</p>
              <img
                class="modal-img"
                src="../assets/image/ganaste.png"
                style="width: 100%; height: 100%"
              />
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Cerrar
              </button>
              <button type="button" class="btn btn-primary">
                ¿Volver a jugar?
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main-layout-juego>
</template>

<script>
import { useRoute } from "vue-router";
import InformacionJuego from "../components/InformacionJuego.vue";
import { onMounted, ref, reactive, nextTick } from "vue";
import JuegosService from "../api/JuegosService";
import { message_error } from "../util/Messages.js";
import Rompecabezas from "../components/juegos/Rompecabezas.vue";
import Emparejar from "../components/juegos/Emparejar.vue";
import Memoria from "../components/juegos/Memoria.vue";
import * as bootstrap from "bootstrap";

export default {
  name: "Jugar",
  components: {
    InformacionJuego,
    Rompecabezas,
    Emparejar,
    Memoria,
  },
  setup(props, context) {
    document.body.style.backgroundColor = "#f8f9fa";
    document.body.style.background = "";
    let time;
    let minutes = ref(0);
    let seconds = ref(0);
    let timeStart = ref(false);
    let movimientos = ref(0);
    let puntaje = ref(0);
    const ModalVictoria = ref(null);
    let ModalBootstrap;
    const route = useRoute();
    const JuegoId = route.params["id"];
    const Nivel = ref(parseInt(route.params["nivel"]));
    const InitialState = {
      idgame: 0,
      idactivitiestype: 0,
      idgametype: 0,
      name: "",
      state: true,
      nameactivities: "",
      namegametype: "",
      level: 1,
      image: null,
      detalles: [],
    };
    const Routes = ref([]);
    const Loading = ref(true);
    const model = reactive({ ...InitialState });
    onMounted(async () => {
      const response = await JuegosService.getJuego(JuegoId);
      if (!response.status.error) {
        Object.assign(model, response.data);
        Loading.value = false;
        Routes.value = [
          "Actividad",
          model.nameactivities,
          model.namegametype,
          model.name,
        ];
      } else {
        message_error(response.status.message);
      }
      await nextTick();
      ModalBootstrap = new bootstrap.Modal(ModalVictoria.value);
    });
    function movesCounter() {
      movimientos.value++;
    }
    function timer() {
      // Actualizar el recuento cada 1 segundo
      timeStart.value = true;
      time = setInterval(function () {
        seconds.value++;
        if (seconds.value === 60) {
          minutes.value++;
          seconds.value = 0;
        }
      }, 1000);
    }
    function stopTime() {
      clearInterval(time);
    }
    function displayModal() {
      ModalBootstrap.show();
    }

    function resetEverything() {
      // Detener el tiempo, restablecer los minutos y los segundos actualizar la hora interna HTML
      stopTime();
      timeStart.value = false;
      seconds.value = 0;
      minutes.value = 0;
      movimientos.value = 0;
    }

    return {
      Routes,
      Loading,
      model,
      Nivel,
      minutes,
      seconds,
      puntaje,
      movimientos,
      timeStart,
      timer,
      stopTime,
      movesCounter,
      displayModal,
      ModalVictoria,
      resetEverything,
    };
  },
};
</script>

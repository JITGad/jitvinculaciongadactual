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
              :titulo="Juego.name"
              :nivel="Nivel"
            />
          </div>
          <div class="row pt-4">
            <rompecabezas
              v-if="Juego.shortname == 'rompecabezas'"
              :model="Juego"
              :level="Nivel"
              :timeStart="timeStart"
              @startTime="timer"
              @movesCounter="movesCounter"
              @displayModal="displayModalVictoria"
              @stopTime="stopTime"
              @resetEverything="resetEverything"
            />
            <memoria
              v-if="Juego.shortname == 'memoria'"
              :model="Juego"
              :level="Nivel"
              :timeStart="timeStart"
              @startTime="timer"
              @movesCounter="movesCounter"
              @displayModal="displayModalVictoria"
              @stopTime="stopTime"
              @resetEverything="resetEverything"
            />
            <emparejar
              v-if="Juego.shortname == 'emparejar'"
              :model="Juego"
              :level="Nivel"
              :timeStart="timeStart"
              @startTime="timer"
              @movesCounter="movesCounter"
              @displayModal="displayModalVictoria"
              @stopTime="stopTime"
              @resetEverything="resetEverything"
            />
          </div>
        </div>
        <div class="row pt-4">
          <div class="btn-group" role="group">
            <button
              @click="BeforeAction"
              type="button"
              class="btn btn-outline-primary"
            >
              <i class="fas fa-arrow-left"></i>
              Anterior
            </button>
            <button
              @click="displayModalInstrucciones"
              type="button"
              class="btn btn-outline-primary"
            >
              Instrucciones
              <i class="fas fa-align-justify"></i>
            </button>
            <button type="button" class="btn btn-outline-primary">
              Reiniciar
              <i class="fas fa-play"></i>
            </button>
            <button
              @click="AfterAction"
              type="button"
              class="btn btn-outline-primary"
            >
              Siguiente
              <i class="fas fa-arrow-right"></i>
            </button>
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
    <section>
      <div class="modal fade" ref="ModalJuegoTerminado">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel">
                Juego terminado
              </h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
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
                Regresar a menu
              </button>
              <button type="button" class="btn btn-primary">
                ¿Volver a jugar?
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section>
      <div class="modal" ref="ModalInstrucciones" tabindex="-1">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title">Instrucciones</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
              <textarea
                class="form-control"
                rows="3"
                readonly
                v-model="TipoJuego.text_instructions"
              >
              </textarea>
              <my-prev-file
                type="audio"
                v-model="TipoJuego.audio_instructions"
              />
              <my-prev-file
                type="video"
                v-model="TipoJuego.video_instructions"
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
            </div>
          </div>
        </div>
      </div>
    </section>
  </main-layout-juego>
</template>

<script>
import { useRoute, useRouter } from "vue-router";
import InformacionJuego from "../components/InformacionJuego.vue";
import { onMounted, ref, reactive, nextTick, watch } from "vue";
import JuegosService from "../api/JuegosService";
import { message_error } from "../util/Messages.js";
import Rompecabezas from "../components/juegos/Rompecabezas.vue";
import Emparejar from "../components/juegos/Emparejar.vue";
import Memoria from "../components/juegos/Memoria.vue";
import * as bootstrap from "bootstrap";
import TipoJuegosService from "../api/TipoJuegosService.js";

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
    const ModalJuegoTerminado = ref(null);
    const ModalVictoria = ref(null);
    const ModalInstrucciones = ref(null);
    let ModalBootstrapVictoria;
    let ModalBootstrapInstrucciones;
    let ModalBootstrapJuegoTerminado;
    const route = useRoute();
    const Router = useRouter();
    const JuegoId = route.params["id"];
    const Nivel = ref(parseInt(route.params["nivel"]));
    const InitialStateJuego = {
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
    const InitialStateTipoJuego = {
      idgametype: 0,
      name: "",
      shortname: "",
      image: null,
      audio_instructions: null,
      text_instructions: "",
      video_instructions: null,
      state: true,
    };
    const Routes = ref([]);
    const Loading = ref(true);
    const TipoJuego = reactive({ ...InitialStateTipoJuego });
    const Juego = reactive({ ...InitialStateJuego });
    watch(
      () => route.params.nivel,
      (nivel, prevNivel) => {
        Nivel.value = parseInt(nivel);
      }
    )
    onMounted(async () => {
      const responseJuego = await JuegosService.getJuego(JuegoId);
      if (!responseJuego.status.error) {
        Object.assign(Juego, responseJuego.data);
        Loading.value = false;
        Routes.value = [
          "Actividad",
          Juego.nameactivities,
          Juego.namegametype,
          Juego.name,
        ];
        const responseTipoJuego = await TipoJuegosService.getTipoJuego(
          Juego.idgametype
        );
        if (!responseTipoJuego.status.error) {
          Object.assign(TipoJuego, responseTipoJuego.data);
        } else {
          message_error(responseTipoJuego.status.message);
        }
      } else {
        message_error(responseJuego.status.message);
      }
      await nextTick();
      ModalBootstrapVictoria = new bootstrap.Modal(ModalVictoria.value);
      ModalBootstrapInstrucciones = new bootstrap.Modal(
        ModalInstrucciones.value
      );
      ModalBootstrapJuegoTerminado = new bootstrap.Modal(
        ModalJuegoTerminado.value
      );
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
    function displayModalVictoria() {
      ModalBootstrapVictoria.show();
    }

    function displayModalInstrucciones() {
      ModalBootstrapInstrucciones.show();
    }

    function AfterAction() {
      //despues
      if (Nivel.value >= Juego.level) {
        ModalBootstrapJuegoTerminado.show();
        return;
      }

      Router.push({
        name: "JugarJuego",
        params: { id: JuegoId, nivel: Nivel.value + 1 },
      });
    }

    function BeforeAction() {
      if (Nivel.value <= 1) {
        Router.push({ name: "NivelesJuego", params: { id: JuegoId } });
        return;
      }

      Router.push({
        name: "JugarJuego",
        params: { id: JuegoId, nivel: Nivel.value - 1 },
      });
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
      AfterAction,
      BeforeAction,
      Routes,
      Loading,
      Juego,
      TipoJuego,
      Nivel,
      minutes,
      seconds,
      puntaje,
      movimientos,
      timeStart,
      timer,
      stopTime,
      movesCounter,
      displayModalVictoria,
      displayModalInstrucciones,
      ModalVictoria,
      ModalInstrucciones,
      ModalJuegoTerminado,
      resetEverything,
    };
  },
};
</script>

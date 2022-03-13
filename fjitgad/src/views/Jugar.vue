<template>
  <main-layout-juego :backgroundBlanc="true">
    <div class="mx-3 mt-5">
      <my-loading v-if="Loading" />
      <div v-else class="card">
        <div class="card-body">
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
                :tipo="TipoJuego.shortname"
              />
            </div>
            <div class="row pt-4">
              <rompecabezas-v-4
                v-if="TipoJuego.shortname == 'rompecabezas'"
                :model="Juego"
                :level="Nivel"
                :timeStart="timeStart"
                @startTime="timer"
                @movesCounter="movesCounter"
                @displayModal="displayModalVictoria"
                @stopTime="stopTime"
                @resetEverything="resetEverything"
                @movValid="NuevoPuntaje"
              />
              <memoria
                v-if="TipoJuego.shortname == 'memoria'"
                :model="Juego"
                :level="Nivel"
                :timeStart="timeStart"
                @startTime="timer"
                @movesCounter="movesCounter"
                @displayModal="displayModalVictoria"
                @stopTime="stopTime"
                @resetEverything="resetEverything"
                @movValid="NuevoPuntaje"
              />
              <emparejar
                v-if="TipoJuego.shortname == 'emparejar'"
                :model="Juego"
                :level="Nivel"
                :timeStart="timeStart"
                @startTime="timer"
                @movesCounter="movesCounter"
                @displayModal="displayModalVictoria"
                @stopTime="stopTime"
                @resetEverything="resetEverything"
                @movValid="NuevoPuntaje"
              />
              <cuento
                v-if="TipoJuego.shortname == 'cuento'"
                :model="Juego"
                :level="Nivel"
                :timeStart="timeStart"
                :movimientos="movimientos"
                @startTime="timer"
                @movesCounter="movesCounter"
                @displayModal="displayModalVictoria"
                @stopTime="stopTime"
                @resetEverything="resetEverything"
                @movValid="NuevoPuntaje"
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
              <button
                @click="resetEverything"
                type="button"
                class="btn btn-outline-primary"
              >
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
    </div>
    <!-- Modal -->
    <section class="win-game-modal">
      <div class="modal fade" ref="ModalVictoria">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-body text-center">
              <h5 class="modal-title h2">
                {{ MensajesVictoria }}
              </h5>
              <span class="badge bg-light text-dark" style="font-size: 1.1em"
                >Tiempo: {{ minutes }} minutos y {{ seconds }} segundos</span
              >
              <br />
              <span class="badge bg-light text-dark" style="font-size: 1.1em"
                >Movimientos: {{ movimientos }}</span
              >
              <div
                :style="{
                  'background-image': 'url(' + ImagenesVictoria + ')',
                  'background-position': 'center',
                  'background-size': '100% 100%',
                  'background-repeat': 'no-repeat',
                  'text-align': 'center',
                  height: '60vh',
                }"
              ></div>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Cerrar
              </button>
              <button
                @click="AfterAction"
                data-bs-dismiss="modal"
                type="button"
                class="btn btn-primary"
              >
                Siguiente nivel
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section>
      <div class="modal fade" ref="ModalJuegoTerminado">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-body">
              <h5 class="modal-title text-center">Juego terminado</h5>
              <div
                :style="{
                  'background-image': 'url(' + ImagenesVictoria + ')',
                  'background-position': 'center',
                  'background-size': '100% 100%',
                  'background-repeat': 'no-repeat',
                  'text-align': 'center',
                  height: '60vh',
                }"
              ></div>
            </div>
            <div class="modal-footer">
              <button
                @click="RegresarMenu"
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Regresar a menú
              </button>
              <button
                @click="VolverAJugar"
                data-bs-dismiss="modal"
                type="button"
                class="btn btn-primary"
              >
                ¿Volver a jugar?
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section>
      <div class="modal" ref="ModalInstrucciones" tabindex="-1">
        <div class="modal-dialog modal-lg modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-body">
              <h5 class="modal-title text-center">Instrucciones</h5>
              <textarea
                class="form-control"
                rows="3"
                readonly
                v-model="TipoJuego.text_instructions"
              >
              </textarea>
              <my-prev-file
                v-if="
                  TipoJuego.audio_instructions &&
                  TipoJuego.audio_instructions.length > 0
                "
                type="audio"
                v-model="TipoJuego.audio_instructions"
                :download="false"
              />
              <my-prev-file
                v-if="
                  TipoJuego.video_instructions &&
                  TipoJuego.video_instructions.length > 0
                "
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
import * as bootstrap from "bootstrap";
import { useRoute, useRouter } from "vue-router";
import { onMounted, ref, reactive, nextTick, watch } from "vue";
import { message_error } from "../util/Messages.js";
import {
  getRandomInt,
  idiomaVozDisponible,
  reproducirTextoAVoz,
} from "../util/Utilities.js";
import InformacionJuego from "../components/InformacionJuego.vue";
import JuegosService from "../api/JuegosService";
import TipoJuegosService from "../api/TipoJuegosService.js";
import EstadisticasService from "../api/EstadisticasService.js";
import RompecabezasV4 from "../components/juegos/RompecabezasV4.vue";
import Emparejar from "../components/juegos/Emparejar.vue";
import Memoria from "../components/juegos/Memoria.vue";
import Cuento from "../components/juegos/Cuentos.vue";
import Route from "../util/Route.js";

export default {
  name: "Jugar",
  components: {
    InformacionJuego,
    RompecabezasV4,
    Emparejar,
    Memoria,
    Cuento,
  },
  setup(props, context) {
    document.body.style.backgroundColor = "#f8f9fa";
    document.body.style.background = "";
    let time;
    let minutes = ref(0);
    let seconds = ref(0);
    let timeStart = ref(false);
    let movimientos = ref(0);
    let puntaje = ref(5);
    const JuegoGanado = ref(false);
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
    const MensajesVictoria = ref("");
    const IdiomaVoz = idiomaVozDisponible();
    let mensajes = [
      "Felicidades lo has hecho bien",
      "En hora buena vas ganando",
      "Te felicito por tu esfuerzo",
      "Felicidades por lograr ganar",
      "Lo hiciste excelente",
      "Felicidades por tu progreso",
      "Felicidades lo estas logrando",
    ];
    const imagenes = [
      require("../assets/image/ganaste0.png"),
      require("../assets/image/ganaste1.png"),
      require("../assets/image/ganaste2.png"),
      require("../assets/image/ganaste3.png"),
      require("../assets/image/ganaste4.png"),
      require("../assets/image/ganaste5.png"),
      require("../assets/image/ganaste6.png"),
      require("../assets/image/ganaste7.png"),
      require("../assets/image/ganaste8.png"),
      require("../assets/image/ganaste9.png"),
      require("../assets/image/ganaste10.png"),
    ];

    const ImagenesVictoria = ref(getImagenVictoria());
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
    const Routes = ref([new Route("/", "Actividad")]);
    const Loading = ref(true);
    const TipoJuego = reactive({ ...InitialStateTipoJuego });
    const Juego = reactive({ ...InitialStateJuego });
    const SonidoAcierto = new Audio(
      require("../assets/coloca la pieza de forma correcta.mp3")
    );
    const SonidoError = new Audio(require("../assets/error.mp3"));
    const GanadorJuego = new Audio(require("../assets/ganador.mp3"));
    watch(
      () => route.params.nivel,
      (nivel, prevNivel) => {
        resetEverything();
        Nivel.value = parseInt(nivel);
        Routes.value[
          Routes.value.length - 1
        ].route = `/jugar/${JuegoId}/${Nivel.value}`;
      }
    );
    onMounted(async () => {
      const responseJuego = await JuegosService.getJuego(JuegoId);
      if (!responseJuego.status.error) {
        Object.assign(Juego, responseJuego.data);
        Loading.value = false;
        Routes.value.push(
          new Route(
            `/juegos-por-actividad/${Juego.idactivitiestype}`,
            Juego.nameactivities
          )
        );

        if (Juego.level > 1) {
          Routes.value.push(
            new Route(`/niveles-Juego/${JuegoId}`, `Niveles ${Juego.name}`)
          );
        }

        Routes.value.push(
          new Route(`/jugar/${JuegoId}/${Nivel.value}`, `${Juego.name}`)
        );
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
    function getImagenVictoria() {
      return `'${imagenes[getRandomInt(0, imagenes.length)]}'`;
    }
    function movesCounter() {
      movimientos.value++;
    }
    function timer() {
      // Actualizar el recuento cada 1 segundo
      JuegoGanado.value = false;
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
    async function displayModalVictoria() {
      JuegoGanado.value = true;
      stopTime();
      MensajesVictoria.value = mensajes[getRandomInt(0, mensajes.length)];
      ImagenesVictoria.value = getImagenVictoria();
      ModalBootstrapVictoria.show();
      GanadorJuego.volume = 0.2;
      GanadorJuego.play();
      reproducirTextoAVoz(MensajesVictoria.value, IdiomaVoz);
      const Response = await EstadisticasService.postEstadisticas({
        idgame: JuegoId,
        movements: movimientos.value,
        minutes: minutes.value,
        seconds: seconds.value,
        score: puntaje.value,
        stars: puntaje.value > 5 ? 5 : puntaje.value < 1 ? 1 : puntaje.value,
        lvl: Nivel.value,
        state: true,
      });
    }

    function displayModalInstrucciones() {
      ModalBootstrapInstrucciones.show();
    }

    function displayModalJuegoTerminado() {
      ImagenesVictoria.value = getImagenVictoria();
      ModalBootstrapJuegoTerminado.show();
    }

    function AfterAction() {
      //despues
      if (TipoJuego.shortname == "cuento") {
        movimientos.value = movimientos.value + 1;
        return;
      }

      if (Nivel.value >= Juego.level && JuegoGanado.value) {
        MensajesVictoria.value = mensajes[getRandomInt(0, mensajes.length)];
        ImagenesVictoria.value = getImagenVictoria();
        GanadorJuego.volume = 0.2;
        GanadorJuego.play();
        reproducirTextoAVoz(
          "Felicidades, has completado todo el juego",
          IdiomaVoz
        );
        reproducirTextoAVoz("!!Bien hecho¡¡", IdiomaVoz);
        ModalBootstrapJuegoTerminado.show();
        return;
      }

      if (JuegoGanado.value) {
        Router.push({
          name: "JugarJuego",
          params: { id: JuegoId, nivel: Nivel.value + 1 },
        });
      }
    }

    function BeforeAction() {
      if (TipoJuego.shortname == "cuento") {
        movimientos.value = movimientos.value - 1;
        return;
      }

      if (Nivel.value <= 1) {
        VolverAJugar();
        return;
      }

      Router.push({
        name: "JugarJuego",
        params: { id: JuegoId, nivel: Nivel.value - 1 },
      });
    }

    function VolverAJugar() {
      Router.push({ name: "NivelesJuego", params: { id: JuegoId } });
    }

    function RegresarMenu() {
      Router.push("/");
    }

    function resetEverything() {
      // Detener el tiempo, restablecer los minutos y los segundos actualizar la hora interna HTML
      stopTime();
      timeStart.value = false;
      seconds.value = 0;
      minutes.value = 0;
      movimientos.value = 0;
      puntaje.value = 5;
    }

    function NuevoPuntaje(MovValid) {
      if (MovValid) {
        SonidoAcierto.play();
        puntaje.value = puntaje.value + 1;
      } else {
        SonidoError.play();
        puntaje.value = puntaje.value - 1;
      }
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
      displayModalJuegoTerminado,
      ModalVictoria,
      ModalInstrucciones,
      ModalJuegoTerminado,
      resetEverything,
      NuevoPuntaje,
      VolverAJugar,
      RegresarMenu,
      MensajesVictoria,
      ImagenesVictoria,
    };
  },
};
</script>

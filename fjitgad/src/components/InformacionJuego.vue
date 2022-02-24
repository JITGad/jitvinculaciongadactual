<template>
  <div>
    <h1 class="row" style="text-align: center; justify-content: center">
      {{ titulo }} - Nivel {{ nivel }}
    </h1>
    <div class="row" style="text-align: center; justify-content: center">
      <div class="card col text-white bg-info p-2 m-2" id="ctrtiempo">
        <div id="ctrbarra">
          <i class="fas fa-clock" aria-hidden="true">&nbsp;</i>
          Tiempo:&nbsp;
          <span id="minutos" class="minutos">{{ minutos }}</span>
          <span id="dospuntos">:</span>
          <span id="segundos" class="segundos">{{ segundos }}</span>
        </div>
      </div>
      <div class="card col text-white bg-info p-2 m-2" id="ctrmovimiento">
        <div id="ctrbarra">
          <i class="fas fa-exchange-alt" aria-hidden="true">&nbsp;</i>
          <span>Movimientos:&nbsp;</span>
          <span id="movimiento">{{ movimientos }}</span>
        </div>
      </div>
      <div class="card col text-white bg-info p-2 m-2" id="ctrpuntaje">
        <div id="ctrbarra">
          <div style="display: flex;" ref="ContenedorEstrellas"></div>
          <span>Puntaje:&nbsp;</span>
          <span id="puntaje">{{ CantidadEstrellas }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watch, onMounted, nextTick } from "vue";
export default {
  name: "InformacionJuego",
  props: {
    titulo: {
      type: String,
      required: true,
    },
    minutos: {
      type: Number,
    },
    segundos: {
      type: Number,
    },
    movimientos: {
      type: Number,
    },
    puntaje: {
      type: Number,
    },
    nivel: {
      type: Number,
    },
  },
  setup(props, context) {
    const CantidadEstrellas = ref(5);
    const ContenedorEstrellas = ref(null);

    onMounted(async () => {
      await nextTick();
      PintarEstrellas();
    });

    function PintarEstrellas() {
      ContenedorEstrellas.value.innerHTML = "";
      var htmlestrellas = "";
      for (let index = 0; index < CantidadEstrellas.value; index++) {
        htmlestrellas += '<i class="far fa-star" aria-hidden="true">&nbsp;</i>';
      }
      ContenedorEstrellas.value.innerHTML = htmlestrellas;
    }

    watch(
      () => props.puntaje,
      (puntaje, puntajeprev) => {
        CantidadEstrellas.value = parseInt(
          puntaje > 5 ? 5 : puntaje < 1 ? 1 : puntaje
        );
        PintarEstrellas();
      }
    );

    return {
      CantidadEstrellas,
      ContenedorEstrellas,
    };
  },
};
</script>
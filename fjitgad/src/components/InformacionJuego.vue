<template>
  <div>
    <h1 class="row" style="text-align: center; justify-content: center">
      {{ titulo }} - Nivel {{ nivel }}
    </h1>
    <div class="row" style="text-align: center; justify-content: center">
      <div class="card col-6 text-white bg-info p-2 m-2" id="ctrtiempo">
        <div id="ctrbarra">
          <i class="fas fa-clock" aria-hidden="true">&nbsp;</i>
          Tiempo:&nbsp;
          <span id="minutos" class="minutos">{{ minutos }}</span>
          <span id="dospuntos">:</span>
          <span id="segundos" class="segundos">{{ segundos }}</span>
        </div>
      </div>
      <div class="card col-6 text-white bg-info p-2 m-2" id="ctrmovimiento">
        <div id="ctrbarra">
          <i class="fas fa-exchange-alt" aria-hidden="true">&nbsp;</i>
          <span>Movimientos:&nbsp;</span>
          <span id="movimiento">{{ movimientos }}</span>
        </div>
      </div>
      {{ CantidadEstrellas }}
      <div class="card col-6 text-white bg-info p-2 m-2" id="ctrpuntaje">
        <div id="ctrbarra">
          <template v-for="n in CantidadEstrellas" :key="n">
            <i class="far fa-star" aria-hidden="true">&nbsp;</i>
          </template>
          <span>Puntaje:&nbsp;</span>
          <span id="puntaje">{{ CantidadEstrellas }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed, ref } from "vue";
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
    const CantidadEstrellas = computed(() =>
      props.puntaje > 5 ? 5 : props.puntaje < 1 ? 1 : props.puntaje
    );

    watch(
      () => props.puntaje,
      (puntaje, puntajeprev) => {
        if (timestart != timestarprev) {
          if (!timestart) return;
        }
        InitGame();
      }
    );

    return {
      CantidadEstrellas,
    };
  },
};
</script>
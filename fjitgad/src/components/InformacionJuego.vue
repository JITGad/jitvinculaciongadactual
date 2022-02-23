<template>
  <div>
    <h1 class="row" style="text-align: center; justify-content: center">
      {{ titulo }} - Nivel {{nivel}}
    </h1>
    <div class="row" style="text-align: center; justify-content: center">
      <div class="card col-6 text-white bg-info p-2" id="ctrtiempo">
        <div id="ctrbarra">
          <i class="fas fa-clock" aria-hidden="true">&nbsp;</i>
          Tiempo:&nbsp;
          <span id="minutos" class="minutos">{{minutos}}</span>
          <span id="dospuntos">:</span>
          <span id="segundos" class="segundos">{{segundos}}</span>
        </div>
      </div>
      <div class="card col-6 text-white bg-info p-2" id="ctrmovimiento">
        <div id="ctrbarra">
          <i class="fas fa-exchange-alt" aria-hidden="true">&nbsp;</i>
          <span>Movimientos:&nbsp;</span>
          <span id="movimiento">{{movimientos}}</span>
        </div>
      </div>
      <div class="card col-6 text-white bg-info p-2" id="ctrpuntaje">
        <div id="ctrbarra">
          <i class="far fa-star" aria-hidden="true">&nbsp;</i>
          <span>Puntaje:&nbsp;</span>
          <span id="puntaje">{{puntaje}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "InformacionJuego",
  props: {
    titulo: {
      type: String,
      required: true,
    },
    minutos: {
      type: Number
    },
    segundos: {
      type: Number
    },
    movimientos: {
      type: Number
    },
    puntaje: {
      type: Number
    },
    nivel: {
      type: Number
    },
  },
  setup(props, context) {},
};
</script>
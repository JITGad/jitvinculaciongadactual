<template>
  <div id="informacion">
    <div class="card col-6 text-white bg-info p-2" id="ctrtiempo">
      <div id="ctrbarra">
        <i class="fas fa-clock" aria-hidden="true">&nbsp;</i>
        Tiempo:&nbsp;
        <span id="minutos" class="minutos">00</span>
        <span id="dospuntos">:</span>
        <span id="segundos" class="segundos">00</span>
      </div>
    </div>
    <div class="card col-6 text-white bg-info p-2" id="ctrmovimiento">
      <div id="ctrbarra">
        <i class="fas fa-exchange-alt" aria-hidden="true">&nbsp;</i>
        <span>Movimientos:&nbsp;</span>
        <span id="movimiento">0</span>
      </div>
    </div>
    <div class="card col-6 text-white bg-info p-2" id="ctrpuntaje">
      <div id="ctrbarra">
        <i class="far fa-star" aria-hidden="true">&nbsp;</i>
        <span>Puntaje:&nbsp;</span>
        <span id="puntaje">0</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
    name: "InformacionJuego",
    setup() {
        
    },
}
</script>
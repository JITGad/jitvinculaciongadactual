<template>
    <div class="container">
        <header class="header">
            <h1>Juego de memoria</h1>
        </header>
        <section class="score-panel">
            <h3>Panel de puntuación</h3>
            <ul id="star-rating" class="star-rating">
                <li class="star"><i class="fa fa-star"></i></li>
                <li class="star"><i class="fa fa-star"></i></li>
                <li class="star"><i class="fa fa-star"></i></li>
            </ul>
            <span class="moves-counter">0</span> Movimientos
            <div class="timer-container">
                <span class="timer"> <i class="fa fa-hourglass-start"></i> Tiempo: 00:00</span>
            </div>
            <div class="buttonse" style="display: flex;">
                <div class="start">
                    <button class="btn start-btn">Empezar <i class="fa fa-play"></i></button>
                </div>
                <div class="reset">
                    <button class="btn reset-btn">Reiniciar <i class="fa fa-repeat"></i></button>
                </div>
            </div>

        </section>
        <ul class="deck"></ul>
    </div>
</template>

<script>
export default {
    name: "EstadisticasJuego",
    props: {
        
    },
    setup(props, contex) {
        
    },
}
</script>
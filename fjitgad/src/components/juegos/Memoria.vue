<template>
  <ul ref="Deck" class="deck"></ul>
</template>

<script>
import { useRoute } from "vue-router";
import InformacionJuego from "../components/InformacionJuego.vue";
import { onMounted, ref, reactive, nextTick } from "vue";
import JuegosService from "../api/JuegosService";
import { message_error } from "../util/Messages.js";
import { setPathFile, shuffle } from "../util/Utilities";

export default {
  name: "Jugar",
  components: {
    InformacionJuego,
  },
  setup(props, context) {
    let opened = [];
    let matched = [];
    const deckCards = [];
    const Deck = ref(null);
    const Routes = ref([]);
    const Loading = ref(true);
    onMounted(async () => {
      await startGame();
    });
    function removeCard() {
      // Mientras deck <ul> tenga un nodo hijo, elimínalo
      while (deck.hasChildNodes()) {
        deck.removeChild(deck.firstChild);
      }
    }

    function timer() {
      // Actualizar el recuento cada 1 segundo
      time = setInterval(function () {
        seconds++;
        if (seconds === 60) {
          minutes++;
          seconds = 0;
        }
        // Actualizar el temporizador en HTML con el tiempo que tarda el usuario en jugar.
        timeCounter.innerHTML =
          "<i class='fa fa-hourglass-start'></i>" +
          " Timer: " +
          minutes +
          " Mins " +
          seconds +
          " Secs";
      }, 1000);
    }

    function stopTime() {
      clearInterval(time);
    }

    function resetEverything() {
      // Detener el tiempo, restablecer los minutos y los segundos actualizar la hora interna HTML
      //console.log(deckCards);
      stopTime();
      timeStart = false;
      seconds = 0;
      minutes = 0;
      timeCounter.innerHTML =
        "<i class='fa fa-hourglass-start'></i>" + " Timer: 00:00";
      // Restablecer el recuento de estrellas y volver a añadir la clase para mostrar las estrellas
      star[1].firstElementChild.classList.add("fa-star");
      star[2].firstElementChild.classList.add("fa-star");
      starCount = 3;
      // Restablecer el recuento de movimientos y restablecer su HTML interno
      moves = 0;
      movesCount.innerHTML = 0;
      // Borra las dos matrices que contienen las cards abiertas y emparejadas
      matched = [];
      opened = [];
      // limpiar la baraja (deck)
      removeCard();
      // Crear una nueva baraja (deck)
      startGame();
    }

    function movesCounter() {
      // Actualizar el html del contador de movimientos
      movesCount.innerHTML++;
      // Lleva la cuenta del número de movimientos de cada par comprobado
      moves++;
    }

    function starRating() {
      if (moves === 14) {
        // El primer elemento hijo es el <i> dentro del <li>
        star[2].firstElementChild.classList.remove("fa-star");
        starCount--;
      }
      if (moves === 18) {
        star[1].firstElementChild.classList.remove("fa-star");
        starCount--;
      }
    }

    function compareTwo() {
      // Cuando hay 2 cards en la matriz abierta
      if (opened.length === 2) {
        // Desactivar cualquier otro clic del ratón en otras cards
        document.body.style.pointerEvents = "none";
      }
      // Compara las dos imágenes src
      if (opened.length === 2 && opened[0].src === opened[1].src) {
        // Si coincide, llame a match()
        match();
        // console.log("¡Es una coincidencia!");
      } else if (opened.length === 2 && opened[0].src != opened[1].src) {
        // Si no hay coincidencia, llame a noMatch()
        noMatch();
        // console.log("no Match!");
      }
    }

    function match() {
      /* Accede a las dos cards en array abierto y añade la 
     clase de match al padre de las imágenes: la etiqueta <li>.*/
      setTimeout(function () {
        opened[0].parentElement.classList.add("match");
        opened[1].parentElement.classList.add("match");
        // añade las cards emparejadas a la array emparejada
        matched.push(...opened);
        // Permitir más clics del ratón en las tarjetas
        document.body.style.pointerEvents = "auto";
        // Comprueba si se ha ganado la partida con las parejas detectadas.
        winGame();
        // Borrar la array abierta
        opened = [];
      }, 600);
      // Llama a movesCounter para incrementar en uno
      movesCounter();
      starRating();
    }

    function noMatch() {
      /* Después de 700 milisegundos las dos cards abiertas 
     tendrán la clase de volteo eliminada del elemento padre de las 
     imágenes <li>*/
      setTimeout(function () {
        // Eliminar el giro de clase en el elemento padre de las imágenes
        opened[0].parentElement.classList.remove("flip");
        opened[1].parentElement.classList.remove("flip");
        // Permitir más clics del ratón en las cards
        document.body.style.pointerEvents = "auto";
        // Retirar las tarjetas de la array abierta
        opened = [];
      }, 700);
      // Llama a movesCounter para incrementar en uno
      movesCounter();
      starRating();
    }

    function AddStats() {
      // Acceder al div de contenido modal
      const stats = document.querySelector(".modal-content");
      // Crear tres párrafos diferentes
      for (let i = 1; i <= 3; i++) {
        // Crear un nuevo párrafo
        const statsElement = document.createElement("p");
        // Añadir una clase al nuevo párrafo
        statsElement.classList.add("stats");
        // Añade la nueva etiqueta <p> creada al contenido del modal
        stats.appendChild(statsElement);
      }
      // Seleccione todas las etiquetas p con la clase de estadísticas y actualice el contenido
      let p = stats.querySelectorAll("p.stats");
      // Establecer el nuevo <p> para tener el contenido de las estadísticas (tiempo, movimientos y calificación de estrellas)

      p[0].innerHTML =
        "Tiempo en completar: " +
        minutes +
        " Minutos y " +
        seconds +
        " Segundos";
      p[1].innerHTML = "Movimientos realizados: " + moves;
      p[2].innerHTML =
        "Su clasificación por estrellas es:  " + starCount + " de 3";
    }

    function displayModal() {
      // Accede al elemento modal <span> (x) que cierra el modal
      const modalClose = document.getElementsByClassName("close")[0];
      // Cuando se gana el juego se establece el bloqueo modal para mostrarlo
      modal.style.display = "block";

      // Cuando el usuario hace clic en <span> (x), cierra el modal
      modalClose.onclick = function () {
        modal.style.display = "none";
      };
      // Cuando el usuario haga clic en cualquier lugar fuera del modal, ciérrelo
      window.onclick = function (event) {
        if (event.target == modal) {
          modal.style.display = "none";
        }
      };
    }

    function winGame() {
      if (matched.length === deckCards.length) {
        stopTime();
        AddStats();
        displayModal();
      }
    }

    async function startGame() {
      await nextTick();
      const shuffledDeck = shuffle(deckCards);
      // Iterar sobre deck (baraja de cartas)
      for (let i = 0; i < shuffledDeck.length; i++) {
        // Crear las etiquetas <li>.
        const liTag = document.createElement("LI");
        // Dar <li> clase de card
        liTag.classList.add("card-puzzle");
        // Crear las etiquetas <img>.
        const addImage = document.createElement("IMG");
        // Añadir <img> a <li>
        liTag.appendChild(addImage);
        // Establecer la ruta img src con el mazo deck
        // en caso de que sea local, se descomenta la linea de abajo y se comenta la que le sigue
        //  addImage.setAttribute("src", "img/" + shuffledDeck[i]);
        addImage.setAttribute("src", shuffledDeck[i]);
        // Añadir una etiqueta alt a la imagen
        addImage.setAttribute("alt", "image of vault boy from fallout");
        // Actualiza el nuevo <li> a deck <ul>
        Deck.value.appendChild(liTag);
      }
      Deck.value.addEventListener("click", function (evt) {
        if (evt.target.nodeName === "LI") {
          // Empezar el temporizador tras el primer clic de una card
          // Ejecuta la función timer()
          if (timeStart === false) {
            timeStart = true;
            timer();
          }
          // Llamar a la función flipCard()
          flipCard();
        }

        //Voltear la card y mostrar las card img
        function flipCard() {
          // Cuando se hace clic en <li> se añade la clase .flip para mostrar img
          evt.target.classList.add("flip");
          // Llamar a la función addToOpened()
          addToOpened();
        }

        //Añade las cards al array vacio de cards abiertas
        function addToOpened() {
          /* Si el array abierto tiene cero o un img más empuja otro img en 
         el array para que podamos comparar estos dos para ser emparejados */
          if (opened.length === 0 || opened.length === 1) {
            // Empujar ese img a la matriz abierta
            opened.push(evt.target.firstElementChild);
          }
          // Llamar a la función compareTwo()
          compareTwo();
        }
      });
    }

    return {
      Routes,
      Loading,
      model,
      Deck,
    };
  },
};
</script>

<style>
.deck {
  background: linear-gradient(to bottom, #5cf, #33ffd1);
  border-radius: 1.5em;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  align-items: center;
  height: 35em;
  margin-bottom: 6em;
  padding: 0.5em;
  width: 35em;
}

.deck,
.card-puzzle {
  box-shadow: 5px 2px 20px 0 rgba(46, 61, 73, 0.5);
}

.card-puzzle {
  background: #fc4;
  border-radius: 0.5em;
  height: 7em;
  width: 7em;
}

img {
  user-select: none;
  width: 6em;
}

.deck img {
  visibility: hidden;
}

.deck>li {
    list-style: none;
}

.deck .card-puzzle.flip-puzzle {
  background: #fff;
  cursor: default;
  transform: rotateY(180deg);
  transition: transform 0.3s linear;
  pointer-events: none;
}

.flip-puzzle img {
  background: #fff;
  visibility: visible;
}

.deck .card-puzzle.match-puzzle {
  background: #39d;
  visibility: visible;
  cursor: default;
  animation: pulse 1s;
}

.match-puzzle img {
  background: #39d;
}
@media screen and (min-width: 550px) {
  .deck {
    height: 50em;
    padding: 2em;
    width: 50em;
  }

  .card-puzzle {
    height: 10em;
    width: 10em;
  }

  .deck img {
    width: 9em;
  }

  .modal-content h2 {
    font-size: 4em;
  }

  .play-again-btn {
    font-size: 1.8em;
  }
}
/*---------------------------------- 
        Escritorio - responsive
        ------------------------------------*/
@media screen and (min-width: 800px) {
  h1 {
    font-size: 6em;
  }

  p {
    font-size: 2.3em;
  }

  .deck {
    height: 70em;
    width: 70em;
  }

  .card-puzzle {
    height: 15em;
    width: 15em;
  }

  .deck img {
    width: 13em;
  }

  .reset-btn {
    font-size: 0.8em;
  }

  .footer {
    font-size: 1.2em;
  }

  .modal-content h2 {
    font-size: 5em;
  }

  /* Imagen modal*/
  .modal-img {
    width: 30em;
  }

  .play-again-btn {
    font-size: 2em;
  }
}
</style>
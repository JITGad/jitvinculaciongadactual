<template>
  <ul class="deck"></ul>
</template>

<script>
import { onMounted, ref, reactive, nextTick, watch } from "vue";
import { setPathFile, shuffle, getRandomInt } from "../../util/Utilities.js";

export default {
  name: "Memoria",
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
    let opened = [];
    let matched = [];
    const deckCards = [];
    var deck;

    watch(
      () => [props.level, props.timeStart],
      ([nivel, prevNivel], [timestart, timestarprev]) => {
        if (timestart != timestarprev) {
          if (!timestart) return;
        }
        startGame();
      }
    );

    onMounted(async () => {
      await startGame();
    });

    function starRating() {
      if (props.movimientos === 14) {
        // El primer elemento hijo es el <i> dentro del <li>
        star[2].firstElementChild.classList.remove("fa-star");
        starCount--;
      }
      if (props.movimientos === 18) {
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
        context.emit("movValid", true);
      } else if (opened.length === 2 && opened[0].src != opened[1].src) {
        // Si no hay coincidencia, llame a noMatch()
        noMatch();
        context.emit("movValid", false);
      }
    }

    function match() {
      /* Accede a las dos cards en array abierto y añade la 
     clase de match al padre de las imágenes: la etiqueta <li>.*/
      setTimeout(function () {
        opened[0].parentElement.classList.add("match-memoria");
        opened[1].parentElement.classList.add("match-memoria");
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
      context.emit("movesCounter");
      starRating();
    }

    function noMatch() {
      /* Después de 700 milisegundos las dos cards abiertas 
     tendrán la clase de volteo eliminada del elemento padre de las 
     imágenes <li>*/
      const opencart1 = opened[0];
      const opencart2 = opened[1];
      setTimeout(function () {
        // Eliminar el giro de clase en el elemento padre de las imágenes
        opencart1.parentElement.classList.remove("flip-memoria");
        opencart2.parentElement.classList.remove("flip-memoria");

        opencart1.parentElement.classList.add("no-match-memoria");
        opencart2.parentElement.classList.add("no-match-memoria");

        setTimeout(() => {
          opencart1.parentElement.classList.remove("no-match-memoria");
          opencart2.parentElement.classList.remove("no-match-memoria");
        }, 300);

        // Permitir más clics del ratón en las cards
        document.body.style.pointerEvents = "auto";
        // Retirar las tarjetas de la array abierta
      }, 700);
      // Llama a movesCounter para incrementar en uno
      opened = [];
      context.emit("movesCounter");
      starRating();
    }

    function winGame() {
      if (matched.length === deckCards.length) {
        context.emit("stopTime");
        context.emit("displayModal");
      }
    }

    async function startGame() {
      await nextTick();
      deckCards.clear();
      var brandsShorted = shuffle(props.model.detalles);

      for (let index = 0; index < brandsShorted.length; index++) {
        const element = brandsShorted[index];
        const newcard = setPathFile(element.image);
        deckCards.push(newcard);
        deckCards.push(newcard);
        if (deckCards.length >= props.level * 2) {
          break;
        }
      }

      while (deckCards.length <= props.level * 2) {
        const rnd = getRandomInt(0, brandsShorted.length);
        const newcard = setPathFile(brandsShorted[rnd].image);
        deckCards.push(newcard);
        deckCards.push(newcard);
      }
      
      if (props.timeStart === false) {
        context.emit("startTime");
      }
      deck = document.querySelector(".deck");
      deck.innerHTML = "";
      matched = [];
      opened = [];
      const shuffledDeck = shuffle(deckCards);
      // Iterar sobre deck (baraja de cartas)
      for (let i = 0; i < shuffledDeck.length; i++) {
        // Crear las etiquetas <li>.
        const liTag = document.createElement("LI");
        // Dar <li> clase de card
        liTag.classList.add("card-memoria", "mb-4");
        // Crear las etiquetas <img>.
        const addImage = document.createElement("IMG");
        // Añadir <img> a <li>
        // Establecer la ruta img src con el mazo deck
        // en caso de que sea local, se descomenta la linea de abajo y se comenta la que le sigue
        //  addImage.setAttribute("src", "img/" + shuffledDeck[i]);
        addImage.setAttribute("src", shuffledDeck[i]);
        // Añadir una etiqueta alt a la imagen
        addImage.setAttribute("alt", "...");
        addImage.style.width = "100%";
        addImage.style.height = "100%";
        addImage.style.transform = "rotateY(180deg)";
        // Actualiza el nuevo <li> a deck <ul>
        liTag.appendChild(addImage);
        liTag.addEventListener("click", function (evt) {
          flipCard(evt);
        });
        liTag.addEventListener("touchstart", function (evt) {
          flipCard(evt);
        });
        deck.appendChild(liTag);
      }

      //Voltear la card y mostrar las card img
      function flipCard(evt) {
        // Cuando se hace clic en <li> se añade la clase .flip para mostrar img
        evt.target.classList.add("flip-memoria");
        // Llamar a la función addToOpened()
        addToOpened(evt);
      }

      //Añade las cards al array vacio de cards abiertas
      function addToOpened(evt) {
        /* Si el array abierto tiene cero o un img más empuja otro img en 
         el array para que podamos comparar estos dos para ser emparejados */
        if (opened.length === 0 || opened.length === 1) {
          // Empujar ese img a la matriz abierta
          opened.push(evt.target.firstElementChild);
        }
        // Llamar a la función compareTwo()
        compareTwo();
      }
    }
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
  height: 100%;
  margin-bottom: 6em;
  padding: 0.5em;
  width: 35em;
}

.deck,
.card-memoria {
  box-shadow: 5px 2px 20px 0 rgba(46, 61, 73, 0.5);
}

.card-memoria {
  background: #fc4;
  border-radius: 0.5em;
  height: 7em;
  width: 7em;
  cursor: pointer;
}

.deck img {
  user-select: none;
  width: 6em;
  visibility: hidden;
}

.deck li {
  list-style: none;
}

.deck .card-memoria.flip-memoria {
  background: #fff;
  cursor: default;
  transform: rotateY(180deg);
  transition: transform 0.3s linear;
  pointer-events: none;
}

.flip-memoria img {
  background: #fff;
  visibility: visible;
}

.deck .card-memoria.match-memoria {
  background: #39d;
  visibility: visible;
  cursor: default;
  animation: pulse 1s;
}

.deck .card-memoria.no-match-memoria {
  transform: rotateY(360deg);
  transition: transform 0.3s linear;
}

.no-match-memoria img {
  background: #fff;
  visibility: visible;
}

.match-memoria img {
  background: #39d;
}
@media screen and (min-width: 550px) {
  .deck {
    padding: 2em;
    width: 50em;
  }

  .card-memoria {
    height: 10em;
    width: 10em;
  }

  .deck img {
    width: 9em;
  }

  .play-again-btn {
    font-size: 1.8em;
  }
}
/*---------------------------------- 
        Escritorio - responsive
        ------------------------------------*/
@media screen and (min-width: 800px) {
  .deck {
    width: 70em;
  }

  .card-memoria {
    height: 15em;
    width: 15em;
  }

  .deck img {
    width: 13em;
  }
}
</style>
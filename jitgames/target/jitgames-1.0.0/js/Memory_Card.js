const fm = new FetchMaster();
const deckCards = [];
var urlApi = "webresources/game";
var lvl = 4;

const encodeQueryString = (params = {}) => {
    const keys = Object.keys(params);
    return keys.length
            ? "?" + keys
            .map(key => encodeURIComponent(key)
                        + "=" + encodeURIComponent(params[key]))
            .join("&")
            : "";
};

async function getdata() {
    const response = await getJuego(79);
    var deckc= [];
    if (!response.status.error) {
        console.log(response.data);
        response.data.detalles = shuffle(response.data.detalles)
       //rellenararray();
       //deckCards = rellenararray(response.data.detalles);

        for (i in response.data.detalles)
            {
              deckc.push(GlobalImageLocation + response.data.detalles[i].image); 
            }
             
        
        while(deckCards.length <= lvl){
            console.log("hola");
            var rnd = getRandomInt(0,deckc.length);
            deckCards.push(deckc[rnd]);
            deckCards.push(deckc[rnd]);
        }     
        console.log(deckc)
        console.log(deckCards)
    } else {
        alert("ERROR");
    }

    /* for (i in deckCards)
        deckCards.push(deckCards[i]) */

}


function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
  }
  
  function rellenararray(){
    console.log(getRandomInt(0,deckCards.length));
    console.log(deckCards.length);
      while(deckCards.length < lvl){
        var rnd = getRandomInt(0,deckCards.length);
        deckCards.push(deckCards[rnd]);
        deckCards.push(deckCards[rnd]);
      }
  }

function getJuego(gameid = 0) {
    return new Promise((resolve) => {
        fm.get(`${urlApi}/getGamebyid${encodeQueryString({'idgame': gameid})}`,
                (data) => resolve(data), true);
    });
}

// SECCIÓN JUEGO

const deck = document.querySelector(".deck");
let opened = [];
let matched = [];
const modal = document.getElementById("modal");
const startb = document.querySelector(".start-btn");
const reset = document.querySelector(".reset-btn");
const playAgain = document.querySelector(".play-again-btn");
const movesCount = document.querySelector(".moves-counter");
let moves = 0;
const star = document.getElementById("star-rating").querySelectorAll(".star");
let starCount = 3;
const timeCounter = document.querySelector(".timer");
let time;
let minutes = 0;
let seconds = 0;
let timeStart = false;

function shuffle(array) {
    let currentIndex = array.length, temporaryValue, randomIndex;
    while (currentIndex !== 0) {
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }
    return array;
}



function startGame() {

    // repetir las cartas de deckCards




    // Invocar la función de store y almacenar en la variable
    const shuffledDeck = shuffle(deckCards);
    // Iterar sobre deck (baraja de cartas)
    for (let i = 0; i < shuffledDeck.length; i++) {
        // Crear las etiquetas <li>.
        const liTag = document.createElement('LI');
        // Dar <li> clase de card
        liTag.classList.add('card');
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
        deck.appendChild(liTag);
    }
}
startGame();

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
        timeCounter.innerHTML = "<i class='fa fa-hourglass-start'></i>" + " Timer: " + minutes + " Mins " + seconds + " Secs";
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
    timeCounter.innerHTML = "<i class='fa fa-hourglass-start'></i>" + " Timer: 00:00";
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

    p[0].innerHTML = "Tiempo en completar: " + minutes + " Minutos y " + seconds + " Segundos";
    p[1].innerHTML = "Movimientos realizados: " + moves;
    p[2].innerHTML = "Su clasificación por estrellas es:  " + starCount + " de 3";
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

deck.addEventListener("click", function (evt) {
    if (evt.target.nodeName === "LI") {
        // Para consolar si estaba haciendo clic en el elemento correcto 
        console.log(evt.target.nodeName + " Was clicked");
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

reset.addEventListener('click', resetEverything);
startb.addEventListener('click', resetEverything);

playAgain.addEventListener('click', function () {
    modal.style.display = "none";
    resetEverything();
});


getdata();

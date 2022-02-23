const fm = new FetchMaster();
const brands = [];
var urlApi = "webresources/game";


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
    const response = await getJuego(77);
    if (!response.status.error) {
        console.log(response.data.detalles);
         // setLoading(false);
        lvl = response.data.level;
        for (i in response.data.detalles)
        if(deckCards.length <= lvl)
            {
              brands.push({ iconName: GlobalImageLocation + response.data.detalles[i].image,
                brandName: response.data.detalles[i].color, 
                color: response.data.detalles[i].html}); 
            }else{
                break;
            }
       //  console.log(brands);    
        process();
    } else {
        alert("ERROR");
    }

}

function getJuego(gameid = 0) {
    return new Promise((resolve) => {
      fm.get(`${urlApi}/getGamebyid${encodeQueryString({'idgame': gameid })}`,
        (data) => resolve(data), true);
    });
  }

function process() {

    let correct = 0;
    let total = 0;
    const modal = document.getElementById("modal");
    const timeCounter = document.querySelector(".timer");
    let time;
    let minutes = 0;
    let seconds = 0;
    let timeStart = false;
    const totalDraggableItems = brands.length;
    const totalMatchingPairs = brands.length; // Should be <= totalDraggableItems
    const reset = document.querySelector(".reset-btn");
    const scoreSection = document.querySelector(".score");
    const correctSpan = scoreSection.querySelector(".correct");
    const totalSpan = scoreSection.querySelector(".total");
    const playAgainBtn = document.querySelector(".play-again-btn");

    const draggableItems = document.querySelector(".draggable-items");
    const matchingPairs = document.querySelector(".matching-pairs");
    let draggableElements;
    let droppableElements;

    initiateGame();


    function timer() {
        // Actualizar el recuento cada 1 segundo
        time = setInterval(function() {
          seconds++;
            if (seconds === 60) {
              minutes++;
              seconds = 0;
            }
          // Actualizar el temporizador en HTML con el tiempo que tarda el usuario en jugar.
          timeCounter.innerHTML = "<i class='fa fa-hourglass-start'></i>" + " Timer: " + minutes + " Mins " + seconds + " Secs" ;
        }, 1000);
      }
    
      function stopTime() {
        clearInterval(time);
      }

      function resetEverything() {
        // Detener el tiempo, restablecer los minutos y los segundos actualizar la hora interna HTML
        stopTime();
        timeStart = false;
        seconds = 0;
        minutes = 0;
        timeCounter.innerHTML = "<i class='fa fa-hourglass-start'></i>" + " Timer: 00:00";
      }

    function initiateGame() {
        const randomDraggableBrands = generateRandomItemsArray(totalDraggableItems, brands);
        const randomDroppableBrands = totalMatchingPairs < totalDraggableItems ? generateRandomItemsArray(totalMatchingPairs, randomDraggableBrands) : randomDraggableBrands;
        const alphabeticallySortedRandomDroppableBrands = [...randomDroppableBrands].sort((a, b) => a.brandName.toLowerCase().localeCompare(b.brandName.toLowerCase()));

       /*  // Create "draggable-items" and append to DOM
        for (let i = 0; i < randomDraggableBrands.length; i++) {
            draggableItems.insertAdjacentHTML("beforeend", `
            <img src="${randomDraggableBrands[i].iconName}" style="color: ${randomDraggableBrands[i].color}; width:100px; " id="${randomDraggableBrands[i].iconName}"></img>`);
        } */

        for (let i = 0; i < randomDraggableBrands.length; i++) {
            draggableItems.insertAdjacentHTML("beforeend", `
            <div class="draggable" draggable="true" style="background-color:${randomDraggableBrands[i].color}; width:100px; height:100px;font-size: 20px;" id="${randomDraggableBrands[i].color}">${randomDraggableBrands[i].brandName}</div>`);
        }


        /*  // Create "matching-pairs" and append to DOM
         for (let i = 0; i < alphabeticallySortedRandomDroppableBrands.length; i++) {
            matchingPairs.insertAdjacentHTML("beforeend", `
            <div class="matching-pair">
              <span class="label">${alphabeticallySortedRandomDroppableBrands[i].brandName}</span>
              <span class="droppable" data-brand="${alphabeticallySortedRandomDroppableBrands[i].iconName}"></span>
            </div>
          `);
        } */


        // Create "matching-pairs" and append to DOM
        for (let i = 0; i < alphabeticallySortedRandomDroppableBrands.length; i++) {
            matchingPairs.insertAdjacentHTML("beforeend", `
            <div class="matching-pair">
              <span class="label"><img draggable="false" src="${alphabeticallySortedRandomDroppableBrands[i].iconName}" style="color: ${randomDraggableBrands[i].color}; width:100px; "></img></span>
              <span class="droppable"data-brand="${alphabeticallySortedRandomDroppableBrands[i].color}"></span>
            </div>
          `);
        }


        draggableElements = document.querySelectorAll(".draggable");
        droppableElements = document.querySelectorAll(".droppable");

        draggableElements.forEach(elem => {
            elem.addEventListener("dragstart", dragStart);
            // elem.addEventListener("drag", drag);
            // elem.addEventListener("dragend", dragEnd);
        });

        droppableElements.forEach(elem => {
            elem.addEventListener("dragenter", dragEnter);
            elem.addEventListener("dragover", dragOver);
            elem.addEventListener("dragleave", dragLeave);
            elem.addEventListener("drop", drop);
        });
    }

    // Drag and Drop Functions

    //Events fired on the drag target

    function dragStart(event) {
        // transfiere texto que en este caso es el html del color (ID)
        event.dataTransfer.setData("text", event.target.id); // or "text/plain"
       // console.log("que será estoxd");
        
    }

    //Events fired on the drop target

    function dragEnter(event) {
        if (event.target.classList && event.target.classList.contains("droppable") && !event.target.classList.contains("dropped")) {
            event.target.classList.add("droppable-hover");
            
        }
    }

    function dragOver(event) {
        if (event.target.classList && event.target.classList.contains("droppable") && !event.target.classList.contains("dropped")) {
          //  console.log("arrastrando");
            event.preventDefault();
            
        }
    }

    function dragLeave(event) {
        if (event.target.classList && event.target.classList.contains("droppable") && !event.target.classList.contains("dropped")) {
            event.target.classList.remove("droppable-hover");
        }
    }

    function drop(event) {
        event.preventDefault();

       // console.log("llegaste al drop");

        if (timeStart === false) {
            timeStart = true; 
            timer();
          }

        event.target.classList.remove("droppable-hover");
       // timer();
       // transfiere texto que en este caso es el html del color
        const draggableElementBrand = event.dataTransfer.getData("text");
      //  console.log(draggableElementBrand);
        const droppableElementBrand = event.target.getAttribute("data-brand");
        const isCorrectMatching = draggableElementBrand === droppableElementBrand;
      //  console.log(droppableElementBrand);
      //  console.log(isCorrectMatching);
        total++;
        if (isCorrectMatching) {
            const draggableElement = document.getElementById(draggableElementBrand);
            event.target.classList.add("dropped");
             draggableElement.classList.add("dragged");
           /* draggableElement.setAttribute("draggable", "false"); */
        //   console.log(draggableElementBrand);
           
           event.target.innerHTML = `<div style="background-color:${draggableElementBrand}; width:100px; height:100px;"></div>`;
           // event.target.innerHTML = `<img src="${draggableElementBrand}" style="width:100px;"></img>`;
            correct++;
        }
        scoreSection.style.opacity = 0;
        setTimeout(() => {
            correctSpan.textContent = correct;
            totalSpan.textContent = total;
            scoreSection.style.opacity = 1;
        }, 200);
      //  console.log(Math.min(totalMatchingPairs, totalDraggableItems));
      //  console.log(correct);
        if (correct === Math.min(totalMatchingPairs, totalDraggableItems)) { // Game Over!!
            playAgainBtn.style.display = "block";
            winGame();
            setTimeout(() => {
                playAgainBtn.classList.add("play-again-btn-entrance");
            }, 200);
        }
    }

    // Other Event Listeners
   // playAgainBtn.addEventListener("click", playAgainBtnClick);
    playAgainBtn.addEventListener('click',function() {
        modal.style.display = "none";
        playAgainBtnClick();
      });

      reset.addEventListener('click',function() {
        playAgainBtnClick();
      });

    function playAgainBtnClick() {
        playAgainBtn.classList.remove("play-again-btn-entrance");
        correct = 0;
        total = 0;
        draggableItems.style.opacity = 0;
        matchingPairs.style.opacity = 0;
        setTimeout(() => {
            scoreSection.style.opacity = 0;
        }, 100);
        setTimeout(() => {
            rein();
        }, 500);
    }

    function rein(){
        playAgainBtn.style.display = "none";
            while (draggableItems.firstChild) draggableItems.removeChild(draggableItems.firstChild);
            while (matchingPairs.firstChild) matchingPairs.removeChild(matchingPairs.firstChild);
            resetEverything();
            initiateGame();
            correctSpan.textContent = correct;
            totalSpan.textContent = total;
            draggableItems.style.opacity = 1;
            matchingPairs.style.opacity = 1;
            scoreSection.style.opacity = 1;
    }

    // Auxiliary functions
    function generateRandomItemsArray(n, originalArray) {
        let res = [];
        let clonedArray = [...originalArray];
        if (n > clonedArray.length) n = clonedArray.length;
        for (let i = 1; i <= n; i++) {
            const randomIndex = Math.floor(Math.random() * clonedArray.length);
            res.push(clonedArray[randomIndex]);
            clonedArray.splice(randomIndex, 1);
        }
        return res;
    }


    // añadir estadisticas

    function AddEstadisticas() {
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
            // Establecer el nuevo <p> para tener el contenido de las estadísticas (tiempo, movimientos)
          
          p[0].innerHTML = "Tiempo en completar: " + minutes + " Minutos y " + seconds + " Segundos";
          p[1].innerHTML = "Puntuación: " + correct + "/" + total;
      }

    function displayModal() {
    // Accede al elemento modal <span> (x) que cierra el modal
    const modalClose = document.getElementsByClassName("close")[0];
        // Cuando se gana el juego se establece el bloqueo modal para mostrarlo
        modal.style.display= "block";
    
        // Cuando el usuario hace clic en <span> (x), cierra el modal
        modalClose.onclick = function() {
        modal.style.display = "none";
        };
    // Cuando el usuario haga clic en cualquier lugar fuera del modal, ciérrelo
        window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
        };
    }

    function winGame() {
          stopTime();
          AddEstadisticas();
          displayModal();
      }
}

getdata();


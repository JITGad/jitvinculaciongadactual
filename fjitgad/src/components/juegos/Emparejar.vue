<template>
  <div class="card">
    <section class="matching-pairs card-body row"></section>
  </div>
  <div class="card">
    <section class="draggable-items card-body"></section>
  </div>
</template>

<script>
import { nextTick, onMounted, watch } from "vue";
import {
  generateRandomItemsArray,
  setPathFile,
  shuffle,
  getRandomInt,
} from "../../util/Utilities.js";
export default {
  name: "Emparejar",
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
    const brands = [];
    var dragableactual;

    watch(
      () => [props.level, props.timeStart],
      ([nivel, prevNivel], [timestart, timestarprev]) => {
        if (timestart != timestarprev) {
          if (!timestart) return;
        }
        InitGame();
      }
    );

    onMounted(async () => {
      await nextTick();
      InitGame();
    });

    function InitGame() {
      brands.clear();
      var brandsShorted = shuffle(props.model.detalles);
      for (const element of brandsShorted) {
        if (brands.length <= props.level) {
          brands.push({
            iddetail: element.idgameimage,
            idcolortype: element.idcolortype,
            iconName: setPathFile(element.image),
            iconName2: setPathFile(element.imagefigure),
            brandName: element.color,
            color: element.html,
          });
        } else {
          break;
        }
      }
      process();
    }

    function rellenararray() {
      while (brands.length <= props.level) {
        var rnd = getRandomInt(0, brands.length);
        brands.push(brands[rnd]);
      }
    }

    function process() {
      rellenararray();
      let correct = 0;
      const totalDraggableItems = brands.length;
      const totalMatchingPairs = brands.length;
      const draggableItems = document.querySelector(".draggable-items");
      const matchingPairs = document.querySelector(".matching-pairs");
      draggableItems.innerHTML = "";
      matchingPairs.innerHTML = "";
      let draggableElements;
      let droppableElements;

      initiateGame();

      function initiateGame() {
        const randomDraggableBrands = generateRandomItemsArray(
          totalDraggableItems,
          brands
        );
        const randomDroppableBrands = generateRandomItemsArray(
          totalMatchingPairs,
          randomDraggableBrands
        );

        for (const element of randomDraggableBrands) {
          let _draggableElement = "";
          if (element.idcolortype > 0) {
            _draggableElement = `<div class="draggable" draggable="true" style="background-color:${element.color}; width:100px; height:100px;font-size: 20px;" 
                                  data-id="${element.iddetail}" data-color="${element.color}">
                                    <span class="badge rounded-pill bg-light text-dark">${element.brandName}</span>
                                </div>`;
          } else if (element.iconName2.length > 0) {
            _draggableElement = `<img src="${element.iconName2}" class="draggable" draggable="true" width:100px; height:100px;" 
                                  data-id="${element.iddetail}" data-image="${element.iconName2}"></img>`;
          }
          draggableItems.insertAdjacentHTML("beforeend", _draggableElement);
        }

        for (const element of randomDroppableBrands) {
          matchingPairs.insertAdjacentHTML(
            "beforeend",
            `<div class="matching-pair ms-2 me-2">
              <img class="mb-2" draggable="false" alt="..." src="${element.iconName}" style="width: 7rem;height: 7rem;"></img>
              <span class="droppable" data-brand="${element.iddetail}"></span>
            </div>`
          );
        }

        draggableElements = document.querySelectorAll(".draggable");
        droppableElements = document.querySelectorAll(".droppable");

        draggableElements.forEach((elem) => {
          elem.addEventListener("dragstart", dragStart);
        });

        droppableElements.forEach((elem) => {
          elem.addEventListener("dragenter", dragEnter);
          elem.addEventListener("dragover", dragOver);
          elem.addEventListener("dragleave", dragLeave);
          elem.addEventListener("drop", drop);
        });

        if (props.timeStart === false) {
          context.emit("startTime");
        }
      }

      // Drag and Drop Functions
      //Events fired on the drag target
      function dragStart(event) {
        dragableactual = event.target;
      }

      //Events fired on the drop target
      function dragEnter(event) {
        if (
          event.target.classList &&
          event.target.classList.contains("droppable") &&
          !event.target.classList.contains("dropped")
        ) {
          event.target.classList.add("droppable-hover");
        }
      }

      function dragOver(event) {
        if (
          event.target.classList &&
          event.target.classList.contains("droppable") &&
          !event.target.classList.contains("dropped")
        ) {
          event.preventDefault();
        }
      }

      function dragLeave(event) {
        if (
          event.target.classList &&
          event.target.classList.contains("droppable") &&
          !event.target.classList.contains("dropped")
        ) {
          event.target.classList.remove("droppable-hover");
        }
      }

      function drop(event) {
        event.preventDefault();

        event.target.classList.remove("droppable-hover");

        const droppableElementBrand = event.target.getAttribute("data-brand");
        let droppableElementColor;
        let droppableElementImg;
        let droppableactual;

        if (dragableactual != null) {
          droppableactual = dragableactual.getAttribute("data-id");
          droppableElementColor = dragableactual.getAttribute("data-color");
          droppableElementImg = dragableactual.getAttribute("data-image");
        }

        const isCorrectMatching = droppableactual === droppableElementBrand;
        if (isCorrectMatching) {
          event.target.classList.add("dropped");
          dragableactual.classList.add("dragged");
          dragableactual.setAttribute("draggable", "false");
          if (droppableElementColor) {
            event.target.innerHTML = `<div style="background-color:${droppableElementColor}; width:100px; height:100px;"></div>`;
          } else if (droppableElementImg) {
            event.target.innerHTML = `<img src="${droppableElementImg}" style="width:100px; height:100px;"></img>`;
          }
          correct++;
          dragableactual = null;
        }
        context.emit("movValid", isCorrectMatching);
        context.emit("movesCounter");

        if (correct === Math.min(totalMatchingPairs, totalDraggableItems)) {
          winGame();
        }
      }

      function winGame() {
        context.emit("stopTime");
        context.emit("displayModal");
      }
    }
  },
};
</script>

<style>
.draggable-items {
  display: flex;
  justify-content: center;
  transition: opacity 0.5s;
}
.draggable {
  height: 7rem;
  width: 7rem;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 4rem;
  font-weight: bold;
  margin: 0rem 0.5rem;
  cursor: move;
  transition: opacity 0.2s;
}
.draggable:hover {
  opacity: 0.5;
}
.matching-pairs {
  transition: opacity 0.5s;
  justify-content: center;
}
.matching-pair {
  width: 7rem;
  height: 14rem;
}
.matching-pair span {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  user-select: none;
}
.label {
  width: 15rem;
  font-size: 2rem;
}
.droppable {
  width: 7rem;
  height: 7rem;
  font-size: 4rem;
  background-color: #fff;
  border: 3px dashed #111;
  transition: 0.2s;
}
.droppable-hover {
  background-color: #bee3f0;
  transform: scale(1.1);
}
.dropped {
  border-style: solid;
}
.dragged {
  user-select: none;
  opacity: 0.1;
  cursor: default;
}
.draggable.dragged:hover {
  opacity: 0.1;
}
</style>
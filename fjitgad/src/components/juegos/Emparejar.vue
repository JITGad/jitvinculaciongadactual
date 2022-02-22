<template>
  <section class="draggable-items"></section>
  <section class="matching-pairs"></section>
</template>

<script>
import { nextTick, onMounted } from "vue";
import { generateRandomItemsArray, setPathFile } from "../../util/Utilities.js";
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
  ],
  setup(props, context) {
    const brands = [];
    onMounted(async () => {
      brands.clear();
      props.model.detalles.forEach((element) => {
        brands.push({
          iconName: setPathFile(element.image),
          brandName: element.color,
          color: element.html,
        });
      });

      await nextTick();
      process();
    });
    function process() {
      let correct = 0;
      let total = 0;
      const totalDraggableItems = brands.length;
      const totalMatchingPairs = brands.length; // Should be <= totalDraggableItems

      const draggableItems = document.querySelector(".draggable-items");
      const matchingPairs = document.querySelector(".matching-pairs");
      let draggableElements;
      let droppableElements;

      initiateGame();

      function initiateGame() {
        const randomDraggableBrands = generateRandomItemsArray(
          totalDraggableItems,
          brands
        );
        const randomDroppableBrands =
          totalMatchingPairs < totalDraggableItems
            ? generateRandomItemsArray(
                totalMatchingPairs,
                randomDraggableBrands
              )
            : randomDraggableBrands;
        const alphabeticallySortedRandomDroppableBrands = [
          ...randomDroppableBrands,
        ].sort((a, b) =>
          a.brandName.toLowerCase().localeCompare(b.brandName.toLowerCase())
        );

        for (let i = 0; i < randomDraggableBrands.length; i++) {
          draggableItems.insertAdjacentHTML(
            "beforeend",
            `
            <div class="draggable" draggable="true" style="background-color:${randomDraggableBrands[i].color}; width:100px; height:100px;font-size: 20px;" id="${randomDraggableBrands[i].color}">${randomDraggableBrands[i].brandName}</div>`
          );
        }

        for (
          let i = 0;
          i < alphabeticallySortedRandomDroppableBrands.length;
          i++
        ) {
          matchingPairs.insertAdjacentHTML(
            "beforeend",
            `
            <div class="matching-pair">
              <span class="label"><img draggable="false" src="${alphabeticallySortedRandomDroppableBrands[i].iconName}" style="color: ${randomDraggableBrands[i].color}; width:100px; "></img></span>
              <span class="droppable"data-brand="${alphabeticallySortedRandomDroppableBrands[i].color}"></span>
            </div>
          `
          );
        }

        draggableElements = document.querySelectorAll(".draggable");
        droppableElements = document.querySelectorAll(".droppable");

        draggableElements.forEach((elem) => {
          elem.addEventListener("dragstart", dragStart);
          // elem.addEventListener("drag", drag);
          // elem.addEventListener("dragend", dragEnd);
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
        // transfiere texto que en este caso es el html del color (ID)
        event.dataTransfer.setData("text", event.target.id); // or "text/plain"
        // console.log("que será estoxd");
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
          //  console.log("arrastrando");
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
        // timer();
        // transfiere texto que en este caso es el html del color
        const draggableElementBrand = event.dataTransfer.getData("text");
        //  console.log(draggableElementBrand);
        const droppableElementBrand = event.target.getAttribute("data-brand");
        const isCorrectMatching =
          draggableElementBrand === droppableElementBrand;
        //  console.log(droppableElementBrand);
        //  console.log(isCorrectMatching);
        total++;
        if (isCorrectMatching) {
          const draggableElement = document.getElementById(
            draggableElementBrand
          );
          event.target.classList.add("dropped");
          draggableElement.classList.add("dragged");
          /* draggableElement.setAttribute("draggable", "false"); */
          //   console.log(draggableElementBrand);

          event.target.innerHTML = `<div style="background-color:${draggableElementBrand}; width:100px; height:100px;"></div>`;
          // event.target.innerHTML = `<img src="${draggableElementBrand}" style="width:100px;"></img>`;
          correct++;
        }

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

<style scoped>
.draggable-items {
  display: flex;
  justify-content: center;
  margin: 1rem 1rem 1.5rem 1rem;
  transition: opacity 0.5s;
}
.draggable {
  height: 5rem;
  width: 5rem;
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
}
.matching-pair {
  height: 6rem;
  width: 22rem;
  margin: 1rem auto;
  display: flex;
  justify-content: space-between;
}
.matching-pair span {
  height: 100%;
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
  width: 6rem;
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
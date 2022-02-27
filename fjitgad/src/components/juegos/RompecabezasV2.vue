<template>
  <div class="ps-0 containermain" id="containercan2">
    <canvas id="myCanvas" width="170" height="170"></canvas>
  </div>
</template>

<script>
import { nextTick, onMounted, watch } from "vue";
import { setPathFile } from "../../util/Utilities";
export default {
  name: "RompecabezasV2",
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
    var PUZZLE_DIFFICULTY = 1;
    let CANVAS = null;
    let CONTEXT = null;
    let CONTAINER = null;
    let SCALER = 0.6;
    let SIZE;
    let PIECES = [];
    let PIECESESTABELCIDAS = [];
    let SELECTED_PIECE = null;
    const PUZZLE_HOVER_TINT = "#1f003c";
    var _img;

    onMounted(async () => {
      await nextTick();
      CONTAINER = document.getElementById("containercan2");
      CANVAS = document.getElementById("myCanvas");
      CONTEXT = CANVAS.getContext("2d");
      CANVAS.style.border = "1px solid black";
      InitGame();
    });

    watch(
      () => [props.level, props.timeStart],
      ([nivel, prevNivel], [timestart, timestarprev]) => {
        if (timestart != timestarprev) {
          if (!timestart) return;
        }
        InitGame();
      }
    );

    function InitGame() {
      PUZZLE_DIFFICULTY = props.level + 1;
      PIECES.clear();
      PIECESESTABELCIDAS.clear();
      main(setPathFile(props.model.detalles[0].image));
    }

    function main(img) {
      _img = new Image();
      _img.src = img;
      _img.addEventListener("load", onImage, false);
    }

    function onImage() {
      SIZE = {
        x: 0,
        y: 0,
        width: 0,
        height: 0,
        rows: PUZZLE_DIFFICULTY,
        columns: PUZZLE_DIFFICULTY,
      };


      addEventListeners();
      handleResize();
      initializePieces(SIZE.rows, SIZE.columns);
      updateCanvas();
      randomizePieces();
      CONTEXT.save();
      CONTEXT.clearRect(20, 20, 500, 500);
      CONTEXT.globalAlpha = 0.4;
      CONTEXT.fillStyle = PUZZLE_HOVER_TINT;
      CONTEXT.fillRect(20, 20, 500, 500);
      CONTEXT.restore();
      CONTEXT.save();
    }

    function addEventListeners() {
      CANVAS.addEventListener("mousedown", onMouseDown);
      CANVAS.addEventListener("mousemove", onMouseMove);
      CANVAS.addEventListener("mouseup", onMouseUp);

      CANVAS.addEventListener("touchstart", onTouchStart);
      CANVAS.addEventListener("touchmove", onTouchMove);
      CANVAS.addEventListener("touchend", onTouchEnd);
      window.addEventListener("resize", resizeWindow);
    }

    function resizeWindow(evt) {
      handleResize();
      updateCanvas();
    }

    function onTouchStart(evt) {
      let loc = {
        x:
          evt.touches[0].clientX ||
          evt.touches[0].screenX ||
          evt.touches[0].pageX ||
          0,
        y:
          evt.touches[0].clientY ||
          evt.touches[0].screenY ||
          evt.touches[0].pageY ||
          0,
      };
      onMouseDown(loc);
    }

    function onTouchMove(evt) {
      let loc = {
        x:
          evt.touches[0].clientX ||
          evt.touches[0].screenX ||
          evt.touches[0].pageX ||
          0,
        y:
          evt.touches[0].clientY ||
          evt.touches[0].screenY ||
          evt.touches[0].pageY ||
          0,
      };
      onMouseMove(loc);
    }

    function onTouchEnd() {
      onMouseUp();
    }

    function onMouseDown(evt) {
      const loc = {
        x: evt.layerX || evt.offsetX || evt.clientX || 0,
        y: evt.layerY || evt.offsetY || evt.clientY || 0,
      };
      SELECTED_PIECE = getPressendPiece(loc);
      if (SELECTED_PIECE != null) {
        SELECTED_PIECE.offset = {
          x: loc.x - SELECTED_PIECE.x,
          y: loc.y - SELECTED_PIECE.y,
        };
      }
    }

    function onMouseMove(evt) {
      if (SELECTED_PIECE != null) {
        const index = PIECES.indexOf(SELECTED_PIECE);
        if (index > -1) {
          PIECES.splice(index, 1);
          PIECES.push(SELECTED_PIECE);
        }
        const loc = {
          x: evt.layerX || evt.offsetX || evt.clientX || 0,
          y: evt.layerY || evt.offsetY || evt.clientY || 0,
        };
        SELECTED_PIECE.x = loc.x - SELECTED_PIECE.offset.x;
        SELECTED_PIECE.y = loc.y - SELECTED_PIECE.offset.y;
      }
    }

    function onMouseUp() {
      if (SELECTED_PIECE != null) {
        const movValid = SELECTED_PIECE.isClose();
        if (movValid) {
          SELECTED_PIECE.snap();
        }
        SELECTED_PIECE = null;
        context.emit("movValid", movValid);
        context.emit("movesCounter");
      }
    }

    function getPressendPiece(loc) {
      for (let i = PIECES.length - 1; i >= 0; i--) {
        if (
          loc.x > PIECES[i].x &&
          loc.x < PIECES[i].x + PIECES[i].width &&
          loc.y > PIECES[i].y &&
          loc.y < PIECES[i].y + PIECES[i].height
        ) {
          return PIECES[i];
        }
      }
      return null;
    }

    function handleResize() {
      CANVAS.height = parseInt(
        getComputedStyle(CONTAINER).getPropertyValue("height")
      );
      CANVAS.width = parseInt(
        getComputedStyle(CONTAINER).getPropertyValue("width")
      );

      let resizer =
        SCALER *
        Math.min(CANVAS.width / _img.width, CANVAS.height / _img.height);
      SIZE.width = resizer * _img.width;
      SIZE.height = resizer * _img.height;
      SIZE.x = CANVAS.width / 2 - SIZE.width / 2;
      SIZE.y = CANVAS.height / 2 - SIZE.height / 2;
    }

    function updateCanvas() {
      CONTEXT.clearRect(0, 0, CANVAS.width, CANVAS.height);

      CONTEXT.globalAlpha = 0.5;
      CONTEXT.drawImage(_img, SIZE.x, SIZE.y, SIZE.width, SIZE.height);

      CONTEXT.strokeRect(SIZE.x, SIZE.y, SIZE.width, SIZE.height);

      CONTEXT.globalAlpha = 1;

      for (let i = 0; i < PIECES.length; i++) {
        PIECES[i].draw(CONTEXT);
      }

      window.requestAnimationFrame(updateCanvas);
    }

    // iniciar todo
    function initializePieces(rows, cols) {
      SIZE.rows = rows;
      SIZE.columns = cols;

      PIECES = [];

      if (props.timeStart === false) {
        context.emit("startTime");
      }

      for (let i = 0; i < SIZE.rows; i++) {
        for (let j = 0; j < SIZE.columns; j++) {
          PIECES.push(new Piece(i, j));
        }
      }
    }

    function locg(i) {
      let loc = {
        x: Math.random() * (CANVAS.width - PIECES[i].width),
        y: Math.random() * (CANVAS.height - PIECES[i].height),
      };
      return loc;
    }

    // random
    function randomizePieces() {
      for (let i = 0; i < PIECES.length; i++) {
        let loc = locg(i);

        while (
          !(
            loc.x + PIECES[i].width <= SIZE.x || loc.x >= SIZE.x + SIZE.width
          ) &&
          !(loc.y + PIECES[i].height <= SIZE.y || loc.y >= SIZE.y + SIZE.height)
        ) {
          loc = locg(i);
        }
        PIECES[i].x = loc.x;
        PIECES[i].y = loc.y;
      }
    }

    class Piece {
      constructor(rowIndex, colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.x = SIZE.x + (SIZE.width * this.colIndex) / SIZE.columns;
        this.y = SIZE.y + (SIZE.height * this.rowIndex) / SIZE.rows;
        this.width = SIZE.width / SIZE.columns;
        this.height = SIZE.height / SIZE.rows;
        this.xCorrect = this.x;
        this.yCorrect = this.y;
      }

      draw(context) {
        context.beginPath();
        context.drawImage(
          _img,
          (this.colIndex * _img.width) / SIZE.columns,
          (this.rowIndex * _img.height) / SIZE.rows,
          _img.width / SIZE.columns,
          _img.height / SIZE.rows,
          this.x,
          this.y,
          this.width,
          this.height
        );

        context.rect(this.x, this.y, this.width, this.height);

        context.stroke();

        context.strokeRect(
          this.xCorrect,
          this.yCorrect,
          this.width,
          this.height
        );
      }
      isClose() {
        if (
          distance(
            { x: this.x, y: this.y },
            { x: this.xCorrect, y: this.yCorrect }
          ) <
          this.width / PUZZLE_DIFFICULTY
        ) {
          PIECESESTABELCIDAS.push(SELECTED_PIECE);
          if (checkwin(PIECES, PIECESESTABELCIDAS)) {
            gameOver();
          }
          return true;
        }
        return false;
      }
      snap() {
        this.x = this.xCorrect;
        this.y = this.yCorrect;
      }
    }

    function distance(p1, p2) {
      return Math.sqrt(
        (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)
      );
    }

    function checkwin(arr1, arr2) {
      if ((Array.isArray(arr1) && Array.isArray(arr2)) === false) return false;
      return (
        JSON.stringify([...new Set(arr1.flat().sort())]) ===
        JSON.stringify([...new Set(arr2.flat().sort())])
      );
    }

    function gameOver() {
      context.emit("displayModal");
    }
  },
};
</script>

<style>
.containermain {
  display: flex;
  width: 100%;
  height: 90vh;
}
</style>
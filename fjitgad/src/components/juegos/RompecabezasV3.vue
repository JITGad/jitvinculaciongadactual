<template>
  <div class="ps-0 containermain" id="containercan2">
    <canvas id="myCanvas2" width="170" height="170"></canvas>
  </div>
</template>

<script>
import { nextTick, onMounted, watch } from "vue";
import { setPathFile, getRandomInt } from "../../util/Utilities";
export default {
  name: "RompecabezasV3",
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
    let PIECESDATA = [];
    let PIECESESTABELCIDAS = [];
    let SELECTED_PIECE = null;
    const PUZZLE_HOVER_TINT = "#1f003c";
    var _img;

    onMounted(async () => {
      await nextTick();
      CONTAINER = document.getElementById("containercan2");
      CANVAS = document.getElementById("myCanvas2");
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
    }

    function addEventListeners() {
      CANVAS.addEventListener("mousedown", onMouseDown);
      CANVAS.addEventListener("mousemove", onMouseMove);
      CANVAS.addEventListener("mouseup", onMouseUp);

      CANVAS.addEventListener("touchstart", onTouchStart);
      CANVAS.addEventListener("touchmove", onTouchMove);
      CANVAS.addEventListener("touchend", onTouchEnd);

      //window.addEventListener("resize", resizeWindow);
    }

    function resizeWindow(evt) {
      handleResize();
      updateCanvas();
    }

    function onTouchStart(evt) {
      const loc = getLocTouch(evt);
      onPieceDown(loc);
    }

    function onTouchMove(evt) {
      if (SELECTED_PIECE != null) {
        const loc = getLocTouch(evt);
        onPieceMove(loc);
      }
    }

    function getLocTouch(evt) {
      var rect = evt.target.getBoundingClientRect();
      return {
        x:
          (evt.touches[0].pageX ||
            evt.touches[0].clientX ||
            evt.touches[0].screenX ||
            0) - rect.left,
        y:
          (evt.touches[0].clientY ||
            evt.touches[0].screenY ||
            evt.touches[0].pageY ||
            0) - rect.top,
      };
    }

    function onTouchEnd() {
      onMouseUp();
    }

    function onPieceDown(loc) {
      SELECTED_PIECE = getPressendPiece(loc);
      if (SELECTED_PIECE != null) {
        SELECTED_PIECE.offset = {
          x: loc.x - SELECTED_PIECE.x,
          y: loc.y - SELECTED_PIECE.y,
        };
      }
    }

    function onMouseDown(evt) {
      const loc = {
        x: evt.offsetX || evt.layerX || evt.clientX || 0,
        y: evt.offsetY || evt.layerY || evt.clientY || 0,
      };
      onPieceDown(loc);
    }

    function onPieceMove(loc) {
      const index = PIECES.indexOf(SELECTED_PIECE);
      if (index > -1) {
        PIECES.splice(index, 1);
        PIECES.push(SELECTED_PIECE);
      }
      SELECTED_PIECE.x = loc.x - SELECTED_PIECE.offset.x;
      SELECTED_PIECE.y = loc.y - SELECTED_PIECE.offset.y;
      if (SELECTED_PIECE.isClosePosi()) {
        CONTEXT.globalAlpha = 0.4;
        CONTEXT.fillStyle = PUZZLE_HOVER_TINT;
        CONTEXT.fillRect(
          SELECTED_PIECE.xCorrect,
          SELECTED_PIECE.yCorrect,
          SELECTED_PIECE.width,
          SELECTED_PIECE.height
        );
        CONTEXT.save();
      }
    }

    function onMouseMove(evt) {
      if (SELECTED_PIECE != null) {
        const loc = {
          x: evt.offsetX || evt.layerX || evt.clientX || 0,
          y: evt.offsetY || evt.layerY || evt.clientY || 0,
        };
        onPieceMove(loc);
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
      PIECESDATA = [];

      if (props.timeStart === false) {
        context.emit("startTime");
      }
      var xPos = 0;
      var yPos = 0;
      for (var i = 0; i < PUZZLE_DIFFICULTY * PUZZLE_DIFFICULTY; i++) {
        var _piece = {};
        _piece.x = xPos;
        _piece.y = yPos;
        _piece.width = SIZE.width / SIZE.columns;
        _piece.height = SIZE.height / SIZE.rows;
        _piece.combinada = false;
        PIECESDATA.push(_piece);
        xPos += _piece.width;
        if (xPos >= SIZE.width) {
          xPos = 0;
          yPos += _piece.width;
        }
      }
      for (let index = 0; index < PUZZLE_DIFFICULTY; index++) {
        try {
          let indexPiece = getRandomInt(0, PIECESDATA.length);
          let piece1 = PIECESDATA[indexPiece];
          let count = 0;
          while (piece1.combinada === true || count < PIECESDATA.length) {
            indexPiece = getRandomInt(0, PIECESDATA.length);
            piece1 = PIECESDATA[indexPiece];
            count++;
          }
          let XorY = getRandomInt(0, 2);
          let piece2;
          if (XorY === 0) {
            //trataremos como x
            piece2 = PIECESDATA[index + 1];
            PIECESDATA.splice(index + 1, 1);
          } else {
            //trataremos como y
            piece2 = PIECESDATA[index + PUZZLE_DIFFICULTY];
            PIECESDATA.splice(index + PUZZLE_DIFFICULTY, 1);
          }
          piece1.width = piece1.width + piece2.width;
          piece1.height = piece1.height + piece2.height;
          piece1.combinada = true;
          PIECESDATA[indexPiece] = piece1;
        } catch (error) {}
      }

      for (let i = 0; i < PIECESDATA.length; i++) {
        let piece1 = PIECESDATA[i];
        PIECES.push(new Piece(piece1.width,piece1.height, piece1.x, piece1.y));
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
      constructor(_width, _heigth, _x, _y) {
        this.x = _x;
        this.y = _y;
        this.width = _width;
        this.height = _heigth;
        this.xCorrect = this.x;
        this.yCorrect = this.y;
      }

      draw(context) {
        context.beginPath();
        context.drawImage(
          _img,
          this.x,
          this.y,
          this.width,
          this.height,
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
      isClosePosi() {
        if (
          distance(
            { x: this.x, y: this.y },
            { x: this.xCorrect, y: this.yCorrect }
          ) <
          this.width / PUZZLE_DIFFICULTY
        ) {
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
  height: 70vh;
}
</style>
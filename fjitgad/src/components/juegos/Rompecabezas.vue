<template>
  <div class="ps-0" id="containercan">
    <canvas id="canvas" width="170" height="170"></canvas>
  </div>
  <img alt="" id="imgcanva" />
</template>

<script>
import { nextTick, onMounted, watch } from "vue";
import { setPathFile } from "../../util/Utilities";
export default {
  name: "Rompecabezas",
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
    var _img;
    var _pieces;
    var _puzzleWidth;
    var _puzzleHeight;
    var _pieceWidth;
    var _pieceHeight;

    var _dimImageOrigen = { width: 0, height: 0 };
    var _dimPieceOrigen = { width: 0, height: 0 };
    var _currentPiece;
    var _currentDropPiece;

    var _mouse;
    var _con;
    var _canvas;
    var _stage;
    var PUZZLE_DIFFICULTY = 0;
    const PUZZLE_HOVER_TINT = "#009900";
    var idim;

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
      const img = setPathFile(props.model.detalles[0].image);
      idim = document.getElementById("imgcanva");
      _img = new Image();

      _con = document.getElementById("containercan");
      _canvas = document.getElementById("canvas");
      _stage = _canvas.getContext("2d");

      PUZZLE_DIFFICULTY = props.level + 1;

      _img.src = img;
      idim.src = img;

      _img.addEventListener("load", onImage, false);
    }

    function onImage(e) {
      _dimImageOrigen.width = _img.width;
      _dimImageOrigen.height = _img.height;

      _dimPieceOrigen.width = Math.floor(
        _dimImageOrigen.width / PUZZLE_DIFFICULTY
      );
      _dimPieceOrigen.height = Math.floor(
        _dimImageOrigen.height / PUZZLE_DIFFICULTY
      );

      _img.height = parseInt(getComputedStyle(_con).getPropertyValue("height"));
      _img.width = parseInt(getComputedStyle(_con).getPropertyValue("width"));

      _pieceWidth = Math.floor(_img.width / PUZZLE_DIFFICULTY);
      _pieceHeight = Math.floor(_img.height / PUZZLE_DIFFICULTY);

      _puzzleWidth = _pieceWidth * PUZZLE_DIFFICULTY;
      _puzzleHeight = _pieceHeight * PUZZLE_DIFFICULTY;

      setCanvas();
      initPuzzle();
    }

    function buildPieces() {
      var sxPos = 0;
      var syPos = 0;
      var dxPos = 0;
      var dyPos = 0;
      for (var i = 0; i < PUZZLE_DIFFICULTY * PUZZLE_DIFFICULTY; i++) {
        var piece = {};
        piece.sx = sxPos;
        piece.sy = syPos;
        piece.dx = dxPos;
        piece.dy = dyPos;
        _pieces.push(piece);
        sxPos += _dimPieceOrigen.width;
        dxPos += _pieceWidth;
        if (dxPos >= _puzzleWidth) {
          sxPos = 0;
          dxPos = 0;
          dyPos += _pieceHeight;
          syPos += _dimPieceOrigen.height;
        }
      }
      shufflePuzzle();
    }

    function shufflePuzzle() {
      _pieces = shuffleArray(_pieces);
      _stage.clearRect(0, 0, _puzzleWidth, _puzzleHeight);
      var xPos = 0;
      var yPos = 0;
      for (var i = 0; i < _pieces.length; i++) {
        var piece = _pieces[i];
        piece.xPos = xPos;
        piece.yPos = yPos;
        _stage.drawImage(
          _img,
          piece.sx,
          piece.sy,
          _dimPieceOrigen.width,
          _dimPieceOrigen.height,
          piece.xPos,
          piece.yPos,
          _pieceWidth,
          _pieceHeight
        );
        _stage.strokeRect(piece.dx, piece.dy, _pieceWidth, _pieceHeight);
        xPos += _pieceWidth;

        if (xPos >= _puzzleWidth) {
          xPos = 0;
          yPos += _pieceHeight;
        }
      }
      for (var i = 0; i < _pieces.length; i++) {
        var piece = _pieces[i];
        _stage.strokeRect(piece.dx, piece.dy, _pieceWidth, _pieceHeight);
      }
      document.onmousedown = onPuzzleClick;
    }

    function shuffleArray(o) {
      for (
        var j, x, i = o.length;
        i;
        j = parseInt(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x
      );
      return o;
    }

    function onPuzzleClick(e) {
      context.emit("movesCounter");
      _mouse.x = e.offsetX || e.layerX || e.clientX;
      _mouse.y = e.offsetY || e.layerY || e.clientY;

      _currentPiece = checkPieceClicked();
      if (_currentPiece != null) {
        _stage.clearRect(
          _currentPiece.xPos,
          _currentPiece.yPos,
          _pieceWidth,
          _pieceHeight
        );
        _stage.save();
        _stage.globalAlpha = 0.4;
        _stage.drawImage(
          _img,
          _currentPiece.sx,
          _currentPiece.sy,
          _dimPieceOrigen.width,
          _dimPieceOrigen.height,
          _mouse.x - _pieceWidth / 2,
          _mouse.y - _pieceHeight / 2,
          _pieceWidth,
          _pieceHeight
        );
        _stage.restore();
        document.onmousemove = updatePuzzle;
        document.onmouseup = pieceDropped;
      }
    }

    function checkPieceClicked() {
      var i;
      var piece;
      for (i = 0; i < _pieces.length; i++) {
        piece = _pieces[i];
        if (
          _mouse.x < piece.xPos ||
          _mouse.x > piece.xPos + _pieceWidth ||
          _mouse.y < piece.yPos ||
          _mouse.y > piece.yPos + _pieceHeight
        ) {
        } else {
          return piece;
        }
      }
      return null;
    }

    function updatePuzzle(e) {
      _currentDropPiece = null;
      _mouse.x = e.offsetX || e.layerX || e.clientX;
      _mouse.y = e.offsetY || e.layerY || e.clientY;
      _stage.clearRect(0, 0, _puzzleWidth, _puzzleHeight);
      var i;
      var piece;
      for (i = 0; i < _pieces.length; i++) {
        piece = _pieces[i];
        if (piece === _currentPiece) {
          continue;
        }
        _stage.drawImage(
          _img,
          piece.sx,
          piece.sy,
          _dimPieceOrigen.width,
          _dimPieceOrigen.height,
          piece.xPos,
          piece.yPos,
          _pieceWidth,
          _pieceHeight
        );
        _stage.strokeRect(piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        if (_currentDropPiece == null) {
          if (
            _mouse.x < piece.xPos ||
            _mouse.x > piece.xPos + _pieceWidth ||
            _mouse.y < piece.yPos ||
            _mouse.y > piece.yPos + _pieceHeight
          ) {
            //NOT OVER
          } else {
            _currentDropPiece = piece;
            _stage.save();
            _stage.globalAlpha = 0.4;
            _stage.fillStyle = PUZZLE_HOVER_TINT;
            _stage.fillRect(
              _currentDropPiece.xPos,
              _currentDropPiece.yPos,
              _pieceWidth,
              _pieceHeight
            );
            _stage.restore();
          }
        }
      }
      _stage.save();
      _stage.globalAlpha = 0.6;
      _stage.drawImage(
        _img,
        _currentPiece.sx,
        _currentPiece.sy,
        _dimPieceOrigen.width,
        _dimPieceOrigen.height,
        _mouse.x - _pieceWidth / 2,
        _mouse.y - _pieceHeight / 2,
        _pieceWidth,
        _pieceHeight
      );
      _stage.restore();
      _stage.strokeRect(
        _mouse.x - _pieceWidth / 2,
        _mouse.y - _pieceHeight / 2,
        _pieceWidth,
        _pieceHeight
      );
    }

    function pieceDropped(e) {
      document.onmousemove = null;
      document.onmouseup = null;
      let movValid = false;
      if (_currentDropPiece != null) {
        var tmp = { xPos: _currentPiece.xPos, yPos: _currentPiece.yPos };
        _currentPiece.xPos = _currentDropPiece.xPos;
        _currentPiece.yPos = _currentDropPiece.yPos;
        _currentDropPiece.xPos = tmp.xPos;
        _currentDropPiece.yPos = tmp.yPos;
        if (
          _currentPiece.xPos === _currentPiece.dx &&
          _currentPiece.yPos === _currentPiece.dy
        ) {
          movValid = true;
        }
      }
      if (_currentPiece != null) {
        context.emit("movValid", movValid);
      }
      resetPuzzleAndCheckWin();
    }

    function resetPuzzleAndCheckWin() {
      _stage.clearRect(0, 0, _puzzleWidth, _puzzleHeight);
      var gameWin = true;
      var i;
      var piece;
      for (i = 0; i < _pieces.length; i++) {
        piece = _pieces[i];
        _stage.drawImage(
          _img,
          piece.sx,
          piece.sy,
          _dimPieceOrigen.width,
          _dimPieceOrigen.height,
          piece.xPos,
          piece.yPos,
          _pieceWidth,
          _pieceHeight
        );
        _stage.strokeRect(piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        if (piece.xPos !== piece.dx || piece.yPos !== piece.dy) {
          gameWin = false;
        }
      }
      if (gameWin) {
        setTimeout(gameOver, 500);
      }
    }

    function gameOver() {
      document.onmousedown = null;
      document.onmousemove = null;
      document.onmouseup = null;
      context.emit("displayModal");
      context.emit("stopTime");
    }
    function setCanvas() {
      _canvas.width = _puzzleWidth;
      _canvas.height = _puzzleHeight;
    }
    function initPuzzle() {
      _pieces = [];
      _mouse = { x: 0, y: 0 };
      _currentPiece = null;
      _currentDropPiece = null;

      _stage.drawImage(_img, 0, 0, _puzzleWidth, _puzzleHeight);

      if (props.timeStart === false) {
        context.emit("startTime");
      }
      buildPieces();
    }
  },
};
</script>

<style scoped>
#imgcanva {
  width: 499px;
  height: 499px;
  border: 1px solid black;
  margin: 1rem;
}
#containercan {
  width: 500px;
  height: 500px;
  border: 1px solid black;
  margin: 1rem;
  padding: 0;
}
</style>
const fm = new FetchMaster();
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
    const response = await getJuego(79);
    if (!response.status.error) {
        console.log(GlobalImageLocation + response.data.image);
        init(GlobalImageLocation + response.data.detalles[0].image, response.data.level);
        /* for(i in response.data.detalles)
         init(GlobalImageLocation+response.data.detalles[i].image, response.data.level); */
    } else {
        alert("ERROR");
    }

}

function getJuego(gameid = 0) {
    return new Promise((resolve) => {
        fm.get(`${urlApi}/getGamebyid${encodeQueryString({'idgame': gameid})}`,
                (data) => resolve(data), true);
    });
}


// JUEGO

const modal = document.getElementById("modal");
const idim = document.getElementById("imgcanva");
const timeCounter = document.querySelector(".timer");
let time;
let minutes = 0;
let seconds = 0;
let timeStart = false;
let movimientos = 0;
const reset = document.querySelector(".reset-btn");
const movesCount = document.querySelector(".moves-counter");
var PUZZLE_DIFFICULTY = 0;
const PUZZLE_HOVER_TINT = '#009900';


var _img;
var _pieces;
var _puzzleWidth;
var _puzzleHeight;
var _pieceWidth;
var _pieceHeight;

var _dimImageOrigen = {width: 0, height: 0};
var _dimPieceOrigen = {width: 0, height: 0};
var _currentPiece;
var _currentDropPiece;
var dimenrealWidth;
var dimenrealHeight;

var _mouse;
var _con;
var _canvas;
var _stage;
var hcv;
var wcv;

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
    stopTime();
    timeStart = false;
    seconds = 0;
    minutes = 0;
    timeCounter.innerHTML = "<i class='fa fa-hourglass-start'></i>" + " Timer: 00:00";
    movesCount.innerHTML = 0;
}

function movesCounter() {
    // Actualizar el html del contador de movimientos
    movesCount.innerHTML++;
    // Lleva la cuenta del número de movimientos de cada par comprobado
    movimientos++;
}


function init(img) {
    _img = new Image();

    _con = document.getElementById('containercan');
    _canvas = document.getElementById('canvas');
    _stage = _canvas.getContext('2d');

    PUZZLE_DIFFICULTY = 3;

    _img.src = img;
    idim.src = img;

    _img.addEventListener('load', onImage, false);
}


function onImage(e) {
    _dimImageOrigen.width = _img.width;
    _dimImageOrigen.height = _img.height;

    _dimPieceOrigen.width = Math.floor(_dimImageOrigen.width / PUZZLE_DIFFICULTY);
    _dimPieceOrigen.height = Math.floor(_dimImageOrigen.height / PUZZLE_DIFFICULTY);

    _img.height = parseInt(getComputedStyle(_con).getPropertyValue('height'));
    _img.width = parseInt(getComputedStyle(_con).getPropertyValue('width'));

    _pieceWidth = Math.floor(_img.width / PUZZLE_DIFFICULTY);
    _pieceHeight = Math.floor(_img.height / PUZZLE_DIFFICULTY);

    _puzzleWidth = _pieceWidth * PUZZLE_DIFFICULTY;
    _puzzleHeight = _pieceHeight * PUZZLE_DIFFICULTY;

    setCanvas();
    initPuzzle();
}


function setCanvas() {
    _canvas.width = _puzzleWidth;
    _canvas.height = _puzzleHeight;
    // _canvas.style.border = "1px solid black";
}

function initPuzzle() {
    _pieces = [];
    _mouse = {x: 0, y: 0};
    _currentPiece = null;
    _currentDropPiece = null;

    _stage.drawImage(_img, 0, 0, _puzzleWidth, _puzzleHeight);

    if (timeStart === false) {
        timeStart = true;
        timer();
    }
    // createTitle("Click to Start Puzzle");
    buildPieces();
}

reset.addEventListener('click', function () {
    resetEverything();
    initPuzzle();
});

function createTitle(msg) {
    _stage.fillStyle = "#000000";
    _stage.globalAlpha = .4;
    _stage.fillRect(100, _puzzleHeight - 40, _puzzleWidth - 200, 40);
    _stage.fillStyle = "#FFFFFF";
    _stage.globalAlpha = 1;
    _stage.textAlign = "center";
    _stage.textBaseline = "middle";
    _stage.font = "20px Arial";
    _stage.fillText(msg, _puzzleWidth / 2, _puzzleHeight - 20);
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
        _stage.drawImage(_img, piece.sx, piece.sy, _dimPieceOrigen.width, _dimPieceOrigen.height, piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        _stage.strokeRect(piece.dx, piece.dy, _pieceWidth, _pieceHeight);
        xPos += _pieceWidth;

        if (xPos >= _puzzleWidth) {
            xPos = 0;
            yPos += _pieceHeight;
        }
    }
    document.onmousedown = onPuzzleClick;
}

function shuffleArray(o) {
    for (var j, x, i = o.length; i; j = parseInt(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
    return o;
}

function onPuzzleClick(e) {
    movesCounter();
    if (e.layerX || e.layerX == 0) {
        _mouse.x = e.layerX - _canvas.offsetLeft;
        _mouse.y = e.layerY - _canvas.offsetTop;
    } else if (e.offsetX || e.offsetX == 0) {
        _mouse.x = e.offsetX - _canvas.offsetLeft;
        _mouse.y = e.offsetY - _canvas.offsetTop;
    }
    _currentPiece = checkPieceClicked();
    if (_currentPiece != null) {
        _stage.clearRect(_currentPiece.xPos, _currentPiece.yPos, _pieceWidth, _pieceHeight);
        _stage.save();
        _stage.globalAlpha = .4;
        _stage.drawImage(_img, _currentPiece.sx, _currentPiece.sy, _dimPieceOrigen.width, _dimPieceOrigen.height, _mouse.x - (_pieceWidth / 2), _mouse.y - (_pieceHeight / 2), _pieceWidth, _pieceHeight);
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
        if (_mouse.x < piece.xPos || _mouse.x > (piece.xPos + _pieceWidth) || _mouse.y < piece.yPos || _mouse.y > (piece.yPos + _pieceHeight)) {
        } else {
            return piece;
        }
    }
    return null;
}

function updatePuzzle(e) {
    _currentDropPiece = null;
    if (e.layerX || e.layerX === 0) {
        _mouse.x = e.layerX - _canvas.offsetLeft;
        _mouse.y = e.layerY - _canvas.offsetTop;
    } else if (e.offsetX || e.offsetX === 0) {
        _mouse.x = e.offsetX - _canvas.offsetLeft;
        _mouse.y = e.offsetY - _canvas.offsetTop;
    }
    _stage.clearRect(0, 0, _puzzleWidth, _puzzleHeight);
    var i;
    var piece;
    for (i = 0; i < _pieces.length; i++) {
        piece = _pieces[i];
        if (piece === _currentPiece) {
            continue;
        }
        _stage.drawImage(_img, piece.sx, piece.sy, _dimPieceOrigen.width, _dimPieceOrigen.height, piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        _stage.strokeRect(piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        if (_currentDropPiece == null) {
            if (_mouse.x < piece.xPos || _mouse.x > (piece.xPos + _pieceWidth) || _mouse.y < piece.yPos || _mouse.y > (piece.yPos + _pieceHeight)) {
                //NOT OVER
            } else {
                _currentDropPiece = piece;
                _stage.save();
                _stage.globalAlpha = .4;
                _stage.fillStyle = PUZZLE_HOVER_TINT;
                _stage.fillRect(_currentDropPiece.xPos, _currentDropPiece.yPos, _pieceWidth, _pieceHeight);
                _stage.restore();
            }
        }
    }
    _stage.save();
    _stage.globalAlpha = .6;
    _stage.drawImage(_img, _currentPiece.sx, _currentPiece.sy, _dimPieceOrigen.width, _dimPieceOrigen.height, _mouse.x - (_pieceWidth / 2), _mouse.y - (_pieceHeight / 2), _pieceWidth, _pieceHeight);
    _stage.restore();
    _stage.strokeRect(_mouse.x - (_pieceWidth / 2), _mouse.y - (_pieceHeight / 2), _pieceWidth, _pieceHeight);
}

function pieceDropped(e) {
    document.onmousemove = null;
    document.onmouseup = null;
    if (_currentDropPiece != null) {
        var tmp = {xPos: _currentPiece.xPos, yPos: _currentPiece.yPos};
        _currentPiece.xPos = _currentDropPiece.xPos;
        _currentPiece.yPos = _currentDropPiece.yPos;
        _currentDropPiece.xPos = tmp.xPos;
        _currentDropPiece.yPos = tmp.yPos;
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
        _stage.drawImage(_img, piece.sx, piece.sy, _dimPieceOrigen.width, _dimPieceOrigen.height, piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        _stage.strokeRect(piece.xPos, piece.yPos, _pieceWidth, _pieceHeight);
        if (piece.xPos !== piece.dx || piece.yPos !== piece.dy) {
            console.log(piece);
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
    displayModal();
    stopTime();
    AddEstadisticas();
    // initPuzzle();
}



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
    p[1].innerHTML = "Movimientos: " + movimientos;
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


getdata();

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
        console.log(GlobalImageLocation + response.data.detalles[0].image);
        main(GlobalImageLocation + response.data.detalles[0].image);
    } else {
        alert("ERROR");
    }

}

function getJuego(gameid = 0) {
    return new Promise((resolve) => {
        fm.get(`${urlApi}/getGamebyid${encodeQueryString({ 'idgame': gameid })}`,
            (data) => resolve(data), true);
    });
}



//datos

const modal = document.getElementById("modal");
const timeCounter = document.querySelector(".timer");
const reset = document.querySelector(".reset-btn");
const movesCount = document.querySelector(".moves-counter");
const playAgainBtn = document.querySelector(".play-again-btn");
const PUZZLE_HOVER_TINT = '#009900';
let time;
let minutes = 0;
let seconds = 0;
let timeStart = false;
let movimientos = 0;


var PUZZLE_DIFFICULTY = 3;
let VIDEO = null;
let CANVAS = null;
let CONTEXT = null;
let SCALER = 0.6;
let SIZE;
let PIECES = [];
let PIECESESTABELCIDAS = [];
var _puzzleWidth;
var _puzzleHeight;
var _pieceWidth;
var _pieceHeight;
let SELECTED_PIECE = null;
var _img;


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

function main(img) {
    _img = new Image();
    _img.src = img;
    CANVAS = document.getElementById("myCanvas");
    CONTEXT = CANVAS.getContext("2d");
    CANVAS.style.border = "1px solid black";
    _img.addEventListener('load', onImage, false);
}

function onImage() {

    SIZE = { x: 0, y: 0, width: 0, height: 0, rows: PUZZLE_DIFFICULTY, columns: PUZZLE_DIFFICULTY };

    //console.log(SIZE);

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
}

function onTouchStart(evt) {
    let loc = {
        x: evt.touches[0].clientX,
        y: evt.touches[0].clientY
    };
    onMouseDown(loc);
}

function onTouchMove(evt) {
    let loc = {
        x: evt.touches[0].clientX,
        y: evt.touches[0].clientY
    };
    onMouseMove(loc);
}

function onTouchEnd() {
    onMouseUp();
}

function onMouseDown(evt) {
    SELECTED_PIECE = getPressendPiece(evt);
    if (SELECTED_PIECE != null) {
        SELECTED_PIECE.offset = {
            x: evt.x - SELECTED_PIECE.x,
            y: evt.y - SELECTED_PIECE.y
        }
    }
}

function onMouseMove(evt) {
    if (SELECTED_PIECE != null) {
        const index = PIECES.indexOf(SELECTED_PIECE);
        if (index > -1) {
            PIECES.splice(index, 1);
            PIECES.push(SELECTED_PIECE);
        }
        SELECTED_PIECE.x = evt.x - SELECTED_PIECE.offset.x;
        SELECTED_PIECE.y = evt.y - SELECTED_PIECE.offset.y;

        SELECTED_PIECE = getPressendPiece(evt);
            
            if(SELECTED_PIECE != null){
                if (SELECTED_PIECE.isClosePosi()) {
                   // SELECTED_PIECE.snap();
                   console.log(SELECTED_PIECE);
                   //console.log(SIZE.width + " - " + SIZE.height);
                   CONTEXT.globalAlpha = 0.4;
                   CONTEXT.fillStyle = PUZZLE_HOVER_TINT;
                   CONTEXT.fillRect(SELECTED_PIECE.xCorrect, SELECTED_PIECE.yCorrect, SIZE.width, SIZE.height);
                   CONTEXT.restore();
                   CONTEXT.save();
                }
            }
    }
}

function onMouseUp() {

    if (SELECTED_PIECE != null) {
        if (SELECTED_PIECE.isClose()) {
            SELECTED_PIECE.snap();
        }
        SELECTED_PIECE = null;
    }
}

function getPressendPiece(loc) {
    for (let i = PIECES.length - 1; i >= 0; i--) {
        if (loc.x > PIECES[i].x && loc.x < PIECES[i].x + PIECES[i].width &&
            loc.y > PIECES[i].y && loc.y < PIECES[i].y + PIECES[i].height) {
            return PIECES[i];
        }
    }
    return null;
}

function handleResize() {
    CANVAS.width = 800;
    CANVAS.height = 600;

    let resizer = SCALER *
        Math.min(
            CANVAS.width / _img.width,
            CANVAS.height / _img.height
        );
    SIZE.width = resizer * _img.width;
    SIZE.height = resizer * _img.height;
    SIZE.x = CANVAS.width / 2 - SIZE.width / 2;
    SIZE.y =  CANVAS.height / 2 - SIZE.height / 2;
}

function updateCanvas() {
    CONTEXT.clearRect(0, 0, CANVAS.width, CANVAS.height);
    
    CONTEXT.globalAlpha = 0.5;
    CONTEXT.drawImage(_img,
        SIZE.x, SIZE.y,
        SIZE.width, SIZE.height);

    CONTEXT.strokeRect(SIZE.x, SIZE.y, SIZE.width, SIZE.height);    


    CONTEXT.globalAlpha = 1;

    for (let i = 0; i < PIECES.length; i++) {
        PIECES[i].draw(CONTEXT);

        //CONTEXT.strokeRect(SIZE.x, SIZE.y, SIZE.width, SIZE.height); 
    }

    window.requestAnimationFrame(updateCanvas);
}

// iniciar todo
function initializePieces(rows, cols) {
    SIZE.rows = rows;
    SIZE.columns = cols;

    PIECES = [];

    if (timeStart === false) {
        timeStart = true;
        timer();
    }

    for (let i = 0; i < SIZE.rows; i++) {
        for (let j = 0; j < SIZE.columns; j++) {
            PIECES.push(new Piece(i, j));
        }
    }
}

function locg(i){
    let loc = {
        x: Math.random() * (CANVAS.width - PIECES[i].width),
        y: Math.random() * (CANVAS.height - PIECES[i].height)
    }
    return loc;
}

// random
function randomizePieces() {
    for (let i = 0; i < PIECES.length; i++) {
        let loc = locg(i);

       while(!(loc.x + PIECES[i].width  <= SIZE.x || loc.x >= SIZE.x + SIZE.width) &&
       !(loc.y + PIECES[i].height  <= SIZE.y || loc.y >= SIZE.y + SIZE.height)){
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
        this.x = SIZE.x + SIZE.width * this.colIndex / SIZE.columns;
        this.y = SIZE.y + SIZE.height * this.rowIndex / SIZE.rows;
        this.width = SIZE.width / SIZE.columns;
        this.height = SIZE.height / SIZE.rows;
        this.xCorrect = this.x;
        this.yCorrect = this.y;
    }

    draw(context) {
        context.beginPath();

        context.drawImage(_img,
            this.colIndex * _img.width / SIZE.columns,
            this.rowIndex * _img.height / SIZE.rows,
            _img.width / SIZE.columns,
            _img.height / SIZE.rows,
            this.x,
            this.y,
            this.width,
            this.height);

        context.rect(this.x, this.y, this.width, this.height);
        
        context.stroke();
        
        context.strokeRect(this.xCorrect, this.yCorrect, this.width, this.height);    

    }
    isClose() {
        if (distance({ x: this.x, y: this.y },
            { x: this.xCorrect, y: this.yCorrect }) < this.width / PUZZLE_DIFFICULTY) {
                  
            PIECESESTABELCIDAS.push(SELECTED_PIECE);
             if(checkwin(PIECES,PIECESESTABELCIDAS)){
                gameOver();
            }     
            return true;
        }
        return false;
    }

    isClosePosi(){
        if (distance({ x: this.x, y: this.y },
            { x: this.xCorrect, y: this.yCorrect }) < this.width / PUZZLE_DIFFICULTY) {       
                
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
        (p1.x - p2.x) * (p1.x - p2.x) +
        (p1.y - p2.y) * (p1.y - p2.y));
}

function checkwin(arr1,arr2) {
    if ((Array.isArray(arr1) && Array.isArray(arr2)) === false) return false;
    return JSON.stringify([...new Set(arr1.flat().sort())]) === JSON.stringify([...new Set(arr2.flat().sort())]);
}  

function gameOver() {
    displayModal();
    stopTime();
    AddEstadisticas();
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

reset.addEventListener('click', function () {
    resetEverything();
    onImage();
});

playAgainBtn.addEventListener('click',function() {
    modal.style.display = "none";
    onImage();
  });


getdata();
//main();
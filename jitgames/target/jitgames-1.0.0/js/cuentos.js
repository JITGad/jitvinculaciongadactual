
const fm = new FetchMaster();
const story = [];
var urlApi = "webresources/game";
var lvl = 3;

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
    const response = await getJuego(52);
    if (!response.status.error) {
        // console.log(response.data.detalles);
        // setLoading(false);
        lvl = response.data.level;
        for (i in response.data.detalles)
            /*    if (story.length <= lvl) { */
            story.push({
                image: GlobalImageLocation + response.data.detalles[i].image,
                paragraph: response.data.detalles[i].paragraph
            });
        /* } else {
            break;
        } */
        console.log(story);
        init();
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

const imgbox = document.querySelector(".img-box");
const paragraphbox = document.querySelector(".paragraph-box");

const prevb = document.querySelector(".prev");
const nextb = document.querySelector(".next");

const modal = document.getElementById("modal");
const playAgainBtn = document.querySelector(".play-again-btn");

var i = 0, cont = 0;

var slider_img;

function init() {
    // const Historyimg = shuffle(story.image);
    const addImage = document.createElement("IMG");
    addImage.classList.add('slider-img');
    addImage.setAttribute("src", story[0].image);
    imgbox.appendChild(addImage);
    paragraphbox.textContent = story[0].paragraph;
    slider_img = document.querySelector('.slider-img');
}

function prev() {
    if (cont === story.length) {
        return win();
    } else {
        cont--;
        if (i <= 0) i = story.length;
        i--;
        return setImg(), paragraph();
    }
}

function next() {

    if (cont === story.length) {
        return win();
    } else {
        /*   console.log(story[cont]);
          console.log(story[0]); */
        cont++;
        if (i >= story.length - 1) i = -1;
        i++;
        return setImg(), paragraph();

    }

}

function setImg() {
    if(story[i] === story[0]){
        i++;
    }
    return slider_img.setAttribute('src', story[i].image);
}

function paragraph() {
    return paragraphbox.textContent = story[i].paragraph;
}

prevb.addEventListener('click', function () {
    prev();
});

nextb.addEventListener('click', function () {
    next();
});

playAgainBtn.addEventListener('click',function() {
    modal.style.display = "none";
    i = 0;
    cont = 0;
    slider_img.setAttribute('src', story[i].image);
  });


function win(){
    setTimeout(displayModal(), 500);
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

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
        // console.log(story);
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

const dataslides = document.querySelector(".data-slides-c");
const buttons = document.querySelectorAll("[data-carousel-button]");
function init() {
    // const Historyimg = shuffle(story.image);

    for (let i = 0; i < story.length; i++) {
        const liTag = document.createElement('LI');
        liTag.classList.add('slide');
        const addImage = document.createElement("IMG");
        //addImage.classList.add('image');
        const addParagraph = document.createElement("DIV");
        addParagraph.classList.add('centrado');
        // Añadir <img> a <li>
        liTag.appendChild(addParagraph);
        liTag.appendChild(addImage);
        addParagraph.innerHTML = story[i].paragraph;
        addImage.setAttribute("data-active", "false");
        addImage.setAttribute("src", story[i].image);
        console.log(story[i].image);
        // Añadir una etiqueta alt a la imagen
        addImage.setAttribute("alt", "--");
        // Actualiza el nuevo <li> a dataslides <ul>
        dataslides.appendChild(liTag);

    }
    cargar();
}


function cargar() {
    /* const slideone = dataslides.querySelector(".slide").firstChild;
    slideone.src = story[0].image;
    console.log(slideone); */
    // console.log(image);

    buttons.forEach(button => {
        button.addEventListener("click", () => {
            console.log(button.dataset.carouselButton);
            const offset = button.dataset.carouselButton === "next" ? 1 : -1
            const slides = button
                .closest("[data-carousel]")
                .querySelector("[data-slides]")

            const activeSlide = slides.querySelector("[data-active]")
            let newIndex = [...slides.children].indexOf(activeSlide) + offset
            /*  console.log(newIndex); */
            if (newIndex < 0) newIndex = slides.children.length - 1;
            if (newIndex >= slides.children.length) newIndex = 0;
            slides.children[newIndex].dataset.active = true
            delete activeSlide.dataset.active
        })
    })
}



getdata();
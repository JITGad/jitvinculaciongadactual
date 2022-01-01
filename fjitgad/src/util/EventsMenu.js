const mobileScreen = window.matchMedia("(max-width: 990px )");

export function toogleMenu() {
    if (mobileScreen.matches) {
        $(".dashboard-nav").toggleClass("mobile-show");
    } else {
        $(".dashboard").toggleClass("dashboard-compact");
    }
}

//window.addEventListener('load', (event) => toogleMenu());

$("body").on("click", '.dashboard-nav-dropdown-toggle', function () {
    $(this).closest(".dashboard-nav-dropdown")
        .toggleClass("show")
        .find(".dashboard-nav-dropdown")
        .removeClass("show");
    $(this).parent()
        .siblings()
        .removeClass("show");
});

$("body").on("click", ".menu-toggle", function () {
    toogleMenu();
});

$("body").on("click", ".dashboard-nav-link", function () {
    $(this).closest(".dashboard-nav-list")
        .find(".dashboard-nav-link")
        .removeClass("active");
    $(this).addClass("active");
});
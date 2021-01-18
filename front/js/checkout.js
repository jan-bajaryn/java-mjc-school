document.addEventListener("DOMContentLoaded", function (event) {
    let item = localStorage.getItem('last-scroll-position');
    if (item) {
        document.body.scrollTop = Number.parseInt(item);
    }
});


window.addEventListener('scroll', () => {
    localStorage.setItem('last-scroll-position', document.body.scrollTop);
})

function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}
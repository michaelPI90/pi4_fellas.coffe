// Evento de Mouseover

var profile = document.querySelector("#profile");
var listaOpcoes = document.querySelector("#lista-opcoes-usuario")

profile.addEventListener('mouseover', function () {

    listaOpcoes.style.display = "block"

})

listaOpcoes.addEventListener('mouseover', function () {

    listaOpcoes.style.display = "block"


})

// Evento de Mouseout

profile.addEventListener('mouseout', function () {

    listaOpcoes.style.display = "none"

})

listaOpcoes.addEventListener('mouseout', function () {


    listaOpcoes.style.display = "none"

})
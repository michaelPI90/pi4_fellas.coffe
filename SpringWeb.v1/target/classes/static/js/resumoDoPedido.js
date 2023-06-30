var adicionarEndereco = document.getElementById("botao-form-adicionar-endereco")
var mostrarFormulario = document.getElementById("container-adicionar-endereco")

adicionarEndereco.addEventListener('click', function() {

    mostrarFormulario.style.display = 'block'

})

var salvarModificacoes = document.getElementById("botao-salvar-modificacoes")
var mostrarCardEndereco = document.getElementById("card-adicionar-endereco")

salvarModificacoes.addEventListener('click', function() {


    mostrarCardEndereco.style.display = 'block'


})
// function confereSenha() {
//     const senha = document.querySelector('input[name=password]')
//     const confirma = document.querySelector("input[name=password2]")

//     if (confirma.value !== senha.value) {
//         confirma.setCustomValidity("As senhas não conferem")
//     } else {
//         confirma.setCustomValidity('')

//     }
// }

// function senhaOK() {
//     alert('Senhas conferem!')
// }





function consultarCep(cepInputId, logradouroInputId, bairroInputId, cidadeInputId, estadoInputId) {
    var cep = document.getElementById(cepInputId).value; // Obtém o valor do campo de entrada do CEP

    // Verifica se o campo do CEP está preenchido
    if (cep.trim() === "") {
        alert("Por favor, insira um CEP válido.");
        return;
    }

    // Cria a URL para consulta do CEP
    var url = "https://viacep.com.br/ws/" + cep + "/json/";

    // Realiza a requisição AJAX para obter as informações do CEP
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var enderecoCliente = JSON.parse(xhr.responseText);

            // Atualiza os campos do endereço com as informações obtidas
            document.getElementById(logradouroInputId).value = enderecoCliente.logradouro;
            document.getElementById(bairroInputId).value = enderecoCliente.bairro;
            document.getElementById(cidadeInputId).value = enderecoCliente.localidade;
            document.getElementById(estadoInputId).value = enderecoCliente.uf;
        }
    };
    xhr.send();
}







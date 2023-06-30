
function salvarUsuario(button) {
    var row = button.parentNode.parentNode;
    var username = row.getElementsByTagName("td")[0].getElementsByTagName("input")[0];
    var email = row.getElementsByTagName("td")[1].getElementsByTagName("span")[0];
    var status = row.getElementsByTagName("td")[2].getElementsByTagName("input")[0];
    var grupo = row.getElementsByTagName("td")[3].getElementsByTagName("span")[0];
    var userId = row.getElementsByTagName("td")[6].getElementsByTagName("input")[0];

    fetch('/api/usuario/alterarDadosUsuario', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: username.value,
            statusAtivo: status.checked,
            email: email.textContent,
            grupo: grupo.textContent,
            userId: userId.value
        })
    })
        .then(response => {
            if (response.ok) {
                alert('Usuário alterado com sucesso!')
            } else {
                alert('Ocorreu um erro ao alterar o usuário!')
                location.reload()
            }
        })
        .catch(error => {
            alert('Ocorreu um erro ao enviar a solicitação: ' + error.message)
            location.reload()
        })
}

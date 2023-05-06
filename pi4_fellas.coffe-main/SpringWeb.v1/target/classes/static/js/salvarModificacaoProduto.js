
function salvarProduto(button) {
    var row = button.parentNode.parentNode;
    var id = row.getElementsByTagName("td")[0].getElementsByTagName("span")[0];
    var qntdEstoque = row.getElementsByTagName("td")[3].getElementsByTagName("input")[0];
    var imagem = row.getElementsByTagName("td")[5].getElementsByTagName("span")[0];

    //alert("id: " + id.textContent + " qntdEstoque: " + qntdEstoque.value)
    

    fetch('/alterarDadosProduto', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id.textContent,
            qntdEstoque: qntdEstoque.value,
            imagem: imagem.textContent
        })
    })
        .then(response => {
            if (response.ok) {
                alert('Produto alterado com sucesso!')
            } else {
                alert("RESPONSE: " + response.text)
                alert('Ocorreu um erro ao alterar o produto!')
                location.reload()
            }
        })
        .catch(error => {
            alert('Ocorreu um erro ao enviar a solicitação: ' + error.message)
            location.reload()
        })
}

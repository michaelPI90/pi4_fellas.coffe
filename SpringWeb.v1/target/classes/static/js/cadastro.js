function mostrarSenha() {
    var $icone = $('#icone');
    var $senha = $('#senha');

    $icone
        .on('mousedown mouseup', function () {
            var inputType = $senha.attr('type') == 'password' ? 'text' : 'password';
            $senha.attr('type', inputType);
        })
        .on('mouseout', function () {
            $senha.attr('type', 'password');
        });
}

function mostrarSenha2() {
    var $icone2 = $('#icone2');
    var $senha2 = $('#senha2');

    $icone2
        .on('mousedown mouseup', function () {
            var inputType = $senha2.attr('type') == 'password' ? 'text' : 'password';
            $senha2.attr('type', inputType);
        })
        .on('mouseout', function () {
            $senha2.attr('type', 'password');
        });
}

function limparDados() {
    var form   = document.getElementById("formulario");
    var nome   = form;
 
    var set = confirm("Deseja apagar os dados do formulário?");
    if (set) {
       form.reset();
    }
 }
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

function confereSenha() {
  const password = formuser.password.value;
  const password2 = formuser.password2.value;

  console.log(password, password2);

  if (password !== password2) {
    alert("Senhas diferentes");
    formuser.password.focus();
    return false;
  }
}

function togglePaymentOption(option) {
  var boletoOption = document.getElementById("boleto");
  var cartaoOption = document.getElementById("cartao");

  if (option === "boleto") {
    boletoOption.style.display = "block";
    cartaoOption.style.display = "none";
  } else if (option === "cartao") {
    boletoOption.style.display = "none";
    cartaoOption.style.display = "block";
  }
}

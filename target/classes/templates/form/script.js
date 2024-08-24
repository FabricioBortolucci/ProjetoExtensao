document.getElementById('userForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Evita o comportamento padrão do formulário

    // Captura os dados do formulário
    const formData = {
        nome: document.getElementById('nome').value,
        endereco: document.getElementById('endereco').value,
        telefone: document.getElementById('telefone').value,
        cpf: document.getElementById('cpf').value,
        idade: document.getElementById('idade').value
    };

    // Envia os dados para o backend
    fetch('http://localhost:8080/teste', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('mensagem').innerText = data.mensagem;
        })
        .catch((error) => {
            console.error('Error:', error);
            document.getElementById('mensagem').innerText = 'Erro ao enviar os dados.';
        });
});

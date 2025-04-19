// Exemplo de requisição para a API do backend
fetch('http://localhost:8080/api/transacoes')
  .then(response => response.json())
  .then(data => {
    const transacoesContainer = document.getElementById('transacoes');
    data.forEach(transacao => {
      const transacaoElement = document.createElement('div');
      transacaoElement.textContent = `Data: ${transacao.data}, Valor: ${transacao.valor}`;
      transacoesContainer.appendChild(transacaoElement);
    });
  })
  .catch(error => console.error('Erro ao carregar transações:', error));

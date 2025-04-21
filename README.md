
**📌 Branch Main - Tronco Principal**  
*(Base estável para novas features)*  

# Sistema de Organização de Extratos Bancários

## Funcionalidades Implementadas
- ✅ **Importação multi-formatos**  
  Suporte a:
  - Nubank (Conta Corrente)
  - Nubank (Cartão de Crédito)  
  - Mercado Pago (Conta)

- 🎨 **Visualização inteligente**  
  - Cores por tipo de transação:
    - Roxo (#A020F0): Pagamentos de cartão
    - Vermelho (#FF0000): Compras no cartão
    - Verde (#00FF00): Créditos
    - Vermelho: Débitos (outros extratos)

- 📊 **Resumo financeiro**  
  - Agrupamento por categoria
  - Formatação monetária (R$ 1.000,00)
  - Exibição apenas da última importação

- 🛡️ **Validações**  
  - Checagem de cabeçalhos
  - Prevenção de duplicatas
  - Tratamento de formatos numéricos (1.000,00 vs 1000.00)

**🛠️ Estrutura Técnica**  
```bash
src/
├── main/
│   ├── controller/    # Lógica de fluxo
│   ├── model/         # Entidades e enums
│   ├── repository/    # Armazenamento em memória
│   └── service/       # Regras de negócio
└── test/              # (Próximos passos)
```

**🔧 Tecnologias-Chave**  
- Java 17+
- Java Collections Framework
- NIO para manipulação de arquivos
- ANSI colors para terminal

**🌱 Próximas Branches Possíveis**  
1. `feature/persistencia-json`  
2. `feature/auto-detect-formato`  
3. `feature/relatorios-mensais`  
4. `bugfix/ajuste-categorizacao`  

**📄 Documentação Associada**  
- [Esquema CSV Suportados](docs/csv_specs.md)  
- [Padrões de Cores](docs/color_coding.md)  

**✅ Critérios de Aceite Atendidos**  
- Importa 3 formatos bancários diferentes  
- Exibe transações com codificação visual  
- Gera resumo financeiro não acumulativo  
- Trata erros básicos de formatação  

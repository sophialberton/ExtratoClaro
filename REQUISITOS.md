# 💰 Conta Clara - Leitura e Organização de Extratos Bancários

Sistema desenvolvido em **Java** para leitura, categorização e organização de extratos bancários. Permite a importação de arquivos `.CSV` (e futuramente `.OFX`), categorização automática de transações, criação de categorias personalizadas e visualização de extratos organizados por mês/ano.

---

## 🚀 Funcionalidades Esperadas

### ✅ Requisitos Funcionais

- [ ] Importar arquivos de extrato nos formatos `.CSV` (OFX em breve)
- [ ] Identificar automaticamente transações com base em padrões:
  - Aplicações/Resgates
  - Pagamentos de cartão de crédito
  - Transferências entre contas
  - Rendimentos
- [ ] Permitir categorização manual de transações não reconhecidas
- [ ] Gerenciar categorias:
  - Categorias padrão do sistema
  - Criação e edição de categorias personalizadas
- [ ] Exibir extratos organizados por **mês** e **ano**
- [ ] Calcular e exibir o **saldo total atual** consolidando todas as importações
- [ ] Armazenar o histórico de importações anteriores
- [ ] Permitir a importação de múltiplos arquivos sem sobrescrever dados anteriores
- [ ] Interface para edição de transações e gerenciamento de categorias

### 🧠 Requisitos Não Funcionais

- [ ] Desenvolvido em Java, com estrutura modular (MVC)
- [ ] Interface limpa, intuitiva e responsiva (JavaFX - em construção)
- [ ] Armazenamento local eficiente (inicialmente em memória ou arquivos, com possibilidade futura de SQLite)
- [ ] Processamento rápido de arquivos grandes
- [ ] Tratamento de erros em arquivos mal formatados
- [ ] Prevenção de duplicação de transações importadas
- [ ] Código limpo e organizado, com separação clara de responsabilidades

---

## 📁 Estrutura Inicial do Projeto

```plaintext
conta-clara/
├── README.md
├── REQUISITOS.md
├── .dist/
└── src/
    ├── Main.java
    ├── model/
    │   └── Transacao.java
    └── service/
        ├── Categorizador.java
        └── LeitorCSV.java

```

## 📌 Próximas Etapas

- [ ] Implementar parser para arquivos OFX
- [ ] Criar interface gráfica com JavaFX
- [ ] Adicionar controle de categorias personalizadas
- [ ] Armazenar e recuperar dados entre sessões
- [ ] Exportar relatórios ou gerar gráficos por categoria

---

## 🛠 Tecnologias

- Java 17+
- IDE: IntelliJ IDEA / VSCode
- JavaFX (planejado)
- [OpenCSV](http://opencsv.sourceforge.net/) (para leitura de arquivos .CSV)

---

## 💡 Contribuições

Este projeto está em fase inicial de desenvolvimento. Sugestões, testes, melhorias e feedbacks são sempre bem-vindos! ✨


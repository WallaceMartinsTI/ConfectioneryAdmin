Don't speak Portuguese? <a href="./README.md">Clique here</a> to view this page in English.

<h1 align="center">
  <p align="center">Confectionary Admin</p>

<img
src="./screenshot.png"
alt="Confectionary Admin screenshot"
/>

</h1>

## 💻 Projeto

Na versão 1.0, o aplicativo será uma ferramenta essencial para o administrador no ramo da confeitaria, permitindo acompanhar pedidos e gerenciar clientes de forma eficiente e organizada.

## ✨ Funcionalidades

- Monitoramento e acompanhamento de pedidos;

- Visualização detalhada dos pedidos, incluindo nome, descrição, valor, status (Orçamento, Confirmado, Em Produção, Finalizados, Entregue, Cancelados), data de criação, data de entrega e informações do cliente;

- Edição e exclusão de pedidos;

- Sistema de fidelização de clientes, com uma lista de clientes e suas respectivas informações;

- Diálogos de confirmação para ações sensíveis;

- Persistência de dados local;

- Sincronização de dados na nuvem;

## 💡 Ideias a partir de entrevistas com profissionais da área

- Uma lista abrangente de pedidos com a capacidade de filtrar por status específico;

- Um botão de progresso para avançar o status do pedido, além da possibilidade de editar manualmente o status em caso de erro;

- Fidelização de clientes: ao criar um pedido, o administrador seleciona um cliente. Ao criar um novo cliente, são coletadas informações como Nome, Email, Telefone, Endereço, Sexo, Data de Nascimento (para mensagens personalizadas), e Observações (anotações sobre preferências do cliente). Promoções e descontos podem ser aplicados com base na quantidade de pedidos realizados, aniversário, entre outros. Anotações técnicas: Cada cliente terá, além das informações pessoais, uma lista dos pedidos feitos e a quantidade total de pedidos realizados.

- Backup de dados locais na nuvem, garantindo que o usuário possa recuperar suas informações caso reinstale o aplicativo, utilizando um sistema de login.

## 🧾 Detalhes das Telas

- O aplicativo tem uma "Bottom App Bar" com as seguintes opções: Menu, Pedidos, Clientes, Info;

- A tela inicial ("Home") exibirá um dashboard com a data e hora atuais, quantidade de pedidos por status, e um container com uma mensagem sobre sincronização, além de um botão para enviar os dados locais para a nuvem;

- Na seção "Pedidos" haverá uma lista de todos os pedidos, uma opção de filtro (por status ou data - mês e ano) e os pedidos serão exibidos em formato de CARDS, que podem ser expandidos para ver detalhes. Um botão de ação flutuante permitirá adicionar novos pedidos;

- Na seção "Clientes" haverá um botão de ação flutuante para adicionar novos clientes, e a lista de clientes será exibida em formato de CARD. Ao clicar em um cliente, uma tela com suas informações será aberta, permitindo editar ou deletar o cliente, além de oferecer dois botões adicionais: "Salvar Alterações" para confirmar edições, e "Ver Pedidos" para visualizar pedidos já entregues por aquele cliente;

- Na seção "Info" a tela será dividida em quatro partes: informações do usuário logado, informações sobre o aplicativo, opção para sincronizar dados da nuvem para o armazenamento local, e informações sobre o desenvolvedor, incluindo foto, nome, idade, área de atuação e links para contato por email e LinkedIn.

## 🛠️ Requisitos de Desenvolvimento

- `Android Studio (IDE);`
- `Kotlin (Linguagem de Programação);`
- `Jetpack Compose (Interface de Usuário);`
- `Jetpack Security (Criptografia);`
- `Compose Navigation (Navegação entre Telas);`
- `Hilt (Injeção de Dependências);`
- `MVVM (Arquitetura);`
- `Room (Banco de Dados Local);`
- `Firebase (Autenticação e Firestore como Banco de Dados na Nuvem);`

## 📝 Layout

Você pode visualizar um esboço inicial do layout do projeto através [desse link](https://www.figma.com/design/1ePpKDDBsV50dEKt3cPUth/Confectionary-Admin?node-id=0-1&t=xPLcqNjJS2hsGzMx-0). Este esboço serve como uma referência básica para a concepção das telas. Para acessá-lo, é necessário ter uma conta no [Figma](http://figma.com/).


## 📄 Licença

Esse projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para mais detalhes.

<br />

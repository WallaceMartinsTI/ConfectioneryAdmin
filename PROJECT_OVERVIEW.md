# Project Overview [EN_US]

The **Confectionary Admin** is an Android application designed to help professionals in the confectionery industry manage their orders efficiently and in an organized manner. With comprehensive features, the application aims to optimize the time and processes involved in order management, from quotation to delivery.

## Application Versions

### Version 1.0

In this version, the administrator (confectionery business) will have access to several features such as tracking orders, viewing order details, editing and deleting an order, changing the order status, and a customer loyalty system.

## Features

- **Track Orders (Status)**: Quotation, Confirmed, In Production, Finished, Delivered, Canceled;
- **Order Details**: Customer, Name, Description (Flavor, Filling), Price, Order Date, Delivery Date, Status;
- **Available Functions for the Administrator**:
  - Track orders;
  - View order details;
  - Edit/Delete an order;
  - Change the order status;
  - Customer loyalty;

## Ideas from Interviews with Industry Professionals

- **Order List**: a list with all orders, with a filter to show orders with the specified status;
- **Status Change**: a button to advance to the next status, and the ability to change the status manually in case of an error;
- **Data Storage**: data persistence, saving data locally and with cloud backup, so if the administrator uninstalls and reinstalls the app in the future, they do not lose the information;
- **Customer Loyalty**:
  - When creating an order, the administrator must choose a customer for it; if this customer does not exist, they must be created;
  - For new customers, collect the following information: Name, Email, Phone, Address, Gender, Date of Birth (for personalized messages), Address, Notes (annotations about the customer/preferences). Only the name is required, in case the customer only makes a quotation and does not place an order, other information is not necessary;
  - Offer promotions or discounts based on the number of orders placed by the customer, birthday, etc.;

## Screens and App Description

- **Starter Screen**: on the home screen, the app's name and subtitle are displayed, along with a welcome message and a button with the text "START". This screen is only shown the first time the app is started;

- **Login Screen**: on the login screen, the app's name and subtitle are displayed, along with a message prompting the user to log in or create an account, two fields for login (email and password), and two buttons, one for logging in with the text "LOGIN" and another to redirect the user to the account creation screen, labeled "CREATE ACCOUNT";

- **Account Registration Screen**: on the account registration screen, the title of the screen "REGISTER" is displayed, along with a back button "<". There is also a subtitle and screen description, fields for name, email, password, and password confirmation, and a button with the text "CREATE ACCOUNT";

- **Main Screen**: on the main screen, the title of the screen "ADMIN PANEL" is displayed, followed by a panel with the full date and time information, e.g., "Tuesday, July 16, 2024, 10:35:52". Below is a title "ORDERS" with a list showing the number of orders by STATUS, and at the bottom, the app navigation options "ORDERS", "MENU", and "CUSTOMERS";

- **Orders Screen**: on the orders screen, the title "ORDERS" is displayed, followed by a filter option "Filter by date or status" with a filter icon. Below is a list of orders, shown as cards. The normal cards display the name, status, delivery date, and an expand option. Expanded cards display the name, description, value, status, order date, delivery date, a button to advance to the next status, and two buttons at the bottom, one to edit the order and another to delete it. At the bottom are the app navigation options "ORDERS", "MENU", and "CUSTOMERS";

- **Customers Screen**: on the customers screen, the title is displayed, along with a search field to look for customers by name, and a list of customers. Each customer card has an option to expand to show all customer information: name, email, phone, gender, date of birth, address, and notes, along with two buttons, one to "VIEW ORDERS" and another to "DELETE". Clicking the "VIEW ORDERS" button opens a screen with a list of all orders. At the bottom are the app navigation options "ORDERS", "MENU", and "CUSTOMERS";

- **Create Order Screen**: on the create order screen, the title "CREATE ORDER" is displayed, with a back button to the left. Below is a subtitle, and fields for customer, name, description, price, order date, delivery date, and status. A button with the text "CREATE ORDER" is at the bottom;

- **Customer Registration Screen**: on the customer registration screen, the title "REGISTER CUSTOMER" is displayed, with a back button to the left. Below is a subtitle, and fields for name, email, phone, gender, date of birth, address, and notes. A button with the text "REGISTER CUSTOMER" is at the bottom;

## Technologies Used in Development

- **IDE**: Android Studio;
- **Language**: Kotlin;
- **Layout**: Jetpack Compose;
- **Database**: RoomDB;
- **Dependencies**: Extended Icons, Compose Navigation, Room;

<br/>
<hr/>
<hr/>
<hr/>
<br/>

# Visão Geral do Projeto [PT_BR]

O **Confectionary Admin** é um aplicativo desenvolvido para a plataforma Android, projetado para auxiliar profissionais do ramo da confeitaria a gerenciar seus pedidos de maneira eficiente e organizada. Com funcionalidades abrangentes, o aplicativo visa otimizar o tempo e os processos envolvidos na administração dos pedidos, desde o orçamento até a entrega.

## Versões do Aplicativo

### Versão 1.0

Nesta versão, o administrador (ramo da confeitaria), terá acesso a algumas funcionalidades como acompanhar pedidos, ver detalhes dos pedidos, editar e deletar um pedido, alterar o status do pedido e também um sistema de fidelização de clientes.

## Funcionalidades

- **Acompanhar Pedidos (Status)**: Orçamento, Confirmado, Em Produção, Finalizado, Entregue, Cancelado;
- **Detalhe do Pedido**: Cliente, Nome, Descrição (Sabor, Recheio), Preço, Data do Pedido, Data para Entrega, Status;
- **Funções Disponíveis para o Administrador**:
  - Acompanhar os pedidos;
  - Ver detalhes dos pedidos;
  - Editar/Deletar um Pedido;
  - Alterar o Status do Pedido;
  - Fidelização de Clientes;

## Ideias a partir de Entrevistas com pessoa da área

- **Lista de Pedidos**: uma lista com todos os pedidos, com um filtro para mostrar os pedidos com o status informado;
- **Mudança de Status**: um botão para avançar para o próximo status, e a capacidade de mudar o status manualmente em caso de erro;
- **Armazenamento de Dados**: persistência de dados, salvando os dados localmente e com backup em nuvem, para que no caso do administrador desinstalar o aplicativo e instalar novamente no futuro, ele não perca as informações;
- **Fidelização de Cliente**:
  - Na criação de um pedido, o administrador deve escolher um cliente para o mesmo, se não existir este cliente, ele deverá ser criado;
  - Para novos clientes, coletar as seguintes informações: Nome, Email, Telefone, Endereço, Gênero, Data de Nascimento (para mensagens personalizadas), Endereço, Observações (anotações sobre o cliente/gostos). Apenas o nome deve ser obrigatório, no caso do cliente fazer apenas o orçamento e não realizar nenhum pedido, não será necessário as demais informações;
  - Oferecer promoções ou descontos baseados no número de pedidos feitos pelo cliente, aniversário, etc;

## Telas e Descrição do Aplicativo

- **Tela Inicial**: na tela inicial, será apresentado o nome e o subtítulo do aplicativo, uma mensagem de boas vindas e um botão com o texto de "INICIAR". Essa tela será mostrada somente na primeira vez que o aplicativo é iniciado;

- **Tela de Login**: na tela de login, será apresentado o nome e o subtítulo do aplicativo, uma mensagem informando para fazer o login ou criar uma conta, dois campos para realizar o login sendo eles de email e senha.Por fim dois botões, uma para realizar o login com o texto de "LOGIN" e o outro para redirecionar o usuário para a tela de criação de conta de usuário, escrito "CRIAR CONTA";

- **Tela de Registro de Conta**: na tela de registro de conta, é apresentado o título da tela, no caso "REGISTRO", e ao lado esquerdo um botão com o ícone de voltar "<". Também tem um subtítulo e uma descrição da tela, campos de nome, email, senha e confirmação de senha, e por fim um botão com o texto de "CRIAR CONTA";

- **Tela Principal**: na tela principal, é apresentado o título da tela, no caso "PAINEL ADMIN", abaixo temos um painel com as informações da data e hora completa, exemplo: "Terça-Feira 16 de Julho de 2024 10:35:52". Abaixo temos um título escrito "PEDIDOS" com uma lista mostrando a quantidade de pedidos por STATUS, por fim na parte de baixo temos a navegação do aplicativo com as opções de "PEDIDOS", "MENU" e "CLIENTES";

- **Tela de Pedidos**: na tela de pedidos, é apresentado o título da tela, no caso "PEDIDOS", abaixo uma opção de filtro escrito "Filtrar por data ou status" e na frente um ícone de filtro. Abaixo temos uma lista com os pedidos. Os pedidos são em formatos de cards, os cards normal tem as informações de nome, status, data para entrega e a opção de expandir, já os cards expandidos, tem as informações de nome, descrição, valor, status, data do pedido, data para entrega e um botão de avançar para o proximo status, e na sua parte final dois botões, um para editar o pedido e outro para deletar. Por fim na parte de baixo temos a navegação do aplicativo com as opções de "PEDIDOS", "MENU" e "CLIENTES";

- **Tela de Clientes**: na tela de clientes, é apresentado o título da tela, um campo de busca para procurar clientes pelo nome, a lista com os clientes. Cada card de cliente tem a opção de expandir para mostrar todas as informações do cliente, tendo nome, email, telefone, gênero, data de nascimento, endereço e observações, além de dois botões, um para "VER PEDIDOS" e outro para "DELETAR". Ao clicar no botão de ver pedidos, é aberto uma tela com uma lista com todos os pedidos. Por fim na parte de baixo temos a navegação do aplicativo com as opções de "PEDIDOS", "MENU" e "CLIENTES";

- **Tela de Criar Pedidos**: na tela de criar pedidos, é apresentado o título da tela, no caso "CRIAR PEDIDO", e a esquerda um botão de voltar a tela anterior. Abaixo temos um subtítulo da tela, e campos de cliente, nome, descrição, preço, data do pedido, data para entrega, status. Por fim temos um botão com o texto "CRIAR PEDIDO";

- **Tela de Cadastro de Clientes**: na tela de cadastrar cliente, é apresentado o título da tela, no caso "CADASTRAR CLIENTE", e a esquerda um botão de voltar a tela anterior. Abaixo temos um subtítulo da tela, e campos de nome, email, telefone, gênero, data de nascimento, endereço e observações. Por fim temos um botão com o texto "CADASTRAR CLIENTE";

## Tecnologias Utilizadas no Desenvolvimento

- **IDE**: Android Studio;
- **Linguagem**: Kotlin;
- **Layout**: Jetpack Compose;
- **Banco de Dados**: RoomDB;
- **Dependências**: Extended Icons, Compose Navigation, Room;

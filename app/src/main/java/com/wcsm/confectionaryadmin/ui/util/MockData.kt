package com.wcsm.confectionaryadmin.ui.util

import com.wcsm.confectionaryadmin.data.model.entities.Customer
import com.wcsm.confectionaryadmin.data.model.entities.Order
import com.wcsm.confectionaryadmin.data.model.types.OrderStatus

val customersMock = listOf(
    Customer(
        customerId = "0",
        name = "Carlos Maia",
        email = "carlos.maia@hotmail.com",
        phone = "31997862543",
        gender = "Masculino",
        dateOfBirth = "23/10/1998",
        address = "Rua José Malvo, Nº 32, Guabas - Betim MG",
        notes = "Não gosta de abacaxi",
        userCustomerOwnerId = "0",
        ordersQuantity = 0,
        customerSince = getCurrentDateTimeMillis()
    ),
    Customer(
        customerId = "1",
        name = "Isabella Silva",
        email = "isabella.silva@gmail.com",
        phone = "31976357843",
        gender = "Feminino",
        dateOfBirth = "12/05/2002",
        address = "Rua Malvino Costa, Nº 21, Mecs - Belo Horizonte MG",
        notes= "Não gosta de morango",
        userCustomerOwnerId = "1",
        ordersQuantity = 0,
        customerSince = getCurrentDateTimeMillis()
    ),
    Customer(
        customerId = "2",
        name = "Ana Maria",
        email = "ana.maria@hotmail.com",
        phone = "31976487624",
        gender = "Feminino",
        dateOfBirth = "07/12/2000",
        address = "Av. Costa Silva, Nº 735, Quatis - Contagem MG",
        notes= "",
        userCustomerOwnerId = "2",
        ordersQuantity = 0,
        customerSince = getCurrentDateTimeMillis()
    ),
    Customer(
        customerId = "3",
        name = "Julesca Matil",
        email = "julesca.metil@gmail.com",
        phone = "31974826374",
        gender = "Outros",
        dateOfBirth = "10/09/1982",
        address = "Rua Paris, Nº 8, Kansas - Betim MG",
        notes= "Não gosta de castanha",
        userCustomerOwnerId = "3",
        ordersQuantity = 0,
        customerSince = getCurrentDateTimeMillis()
    ),
    Customer(
        customerId = "4",
        name = "João Silva",
        email = "joao.silva@hotmail.com",
        phone = "31978652738",
        gender = "Masculino",
        dateOfBirth = "29/01/1999",
        address = "Av. Brasil, Nº 32, Centro - Belo Horizonte MG",
        notes= "",
        userCustomerOwnerId = "4",
        ordersQuantity = 0,
        customerSince = getCurrentDateTimeMillis()
    )
)

val ordersMock = listOf(
    Order(
        orderId = "0",
        customerOwnerId = "0",
        title = "Bolo decenouro para o cliente com cobertura pr",
        description = "Recheio de chocolate e mousse de morango",
        price = 120.50,
        status = OrderStatus.QUOTATION,
        orderDate = convertStringToDateMillis("15/02/2024 16:30") ,
        deliverDate = convertStringToDateMillis("20/02/2024 17:25"),
        userOrderOwnerId = "0"
    ),
    Order(
        orderId = "1",
        customerOwnerId = "1",
        title = "Doce Brigadeiro 100u",
        description = "100 unidades de Brigadeiros skajdhiashdisahudiuhasiudhas suadhiashd idsahduias uiash iusah iduashi sadasdasd",
        price = 115.00,
        status = OrderStatus.CONFIRMED,
        orderDate = convertStringToDateMillis("25/02/2024 12:30"),
        deliverDate = convertStringToDateMillis("28/02/2024 16:22"),
        userOrderOwnerId = "1"
    ),
    Order(
        orderId = "2",
        customerOwnerId = "2",
        title = "Bolo de Aniversário",
        description = "Massa de Chocolate e recheio de prestígio",
        price = 95.25,
        status = OrderStatus.IN_PRODUCTION,
        orderDate = convertStringToDateMillis("28/03/2024 09:30"),
        deliverDate = convertStringToDateMillis("28/03/2024 09:30"),
        userOrderOwnerId = "2"
    ),
    Order(
        orderId = "3",
        customerOwnerId = "3",
        title = "Bolo de Aniversário com nome meio grande vamos ver",
        description = "Massa de Chocolate e recheio de prestígio",
        price = 95.25,
        status = OrderStatus.DELIVERED,
        orderDate = convertStringToDateMillis("28/03/2024 11:00"),
        deliverDate = convertStringToDateMillis("10/04/2024 16:00"),
        userOrderOwnerId = "3"
    )
)
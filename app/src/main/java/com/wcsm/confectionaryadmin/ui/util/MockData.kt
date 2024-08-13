package com.wcsm.confectionaryadmin.ui.util

import com.wcsm.confectionaryadmin.data.model.Customer
import com.wcsm.confectionaryadmin.ui.view.ordersMock

val customersMock = listOf(
    Customer(
        customerId = 0,
        name = "Carlos Maia",
        email = "carlos.maia@hotmail.com",
        phone = "31997862543",
        gender = "Masculino",
        dateOfBirth = "23/10/1998",
        address = "Rua José Malvo, Nº 32, Guabas - Betim MG",
        notes = "Não gosta de abacaxi"
    ),
    Customer(
        customerId = 1,
        name = "Isabella Silva",
        email = "isabella.silva@gmail.com",
        phone = "31976357843",
        gender = "Feminino",
        dateOfBirth = "12/05/2002",
        address = "Rua Malvino Costa, Nº 21, Mecs - Belo Horizonte MG",
        notes= "Não gosta de morango"
    ),
    Customer(
        customerId = 2,
        name = "Ana Maria",
        email = "ana.maria@hotmail.com",
        phone = "31976487624",
        gender = "Feminino",
        dateOfBirth = "07/12/2000",
        address = "Av. Costa Silva, Nº 735, Quatis - Contagem MG",
        notes= ""
    ),
    Customer(
        customerId = 3,
        name = "Julesca Matil",
        email = "julesca.metil@gmail.com",
        phone = "31974826374",
        gender = "Outros",
        dateOfBirth = "10/09/1982",
        address = "Rua Paris, Nº 8, Kansas - Betim MG",
        notes= "Não gosta de castanha"
    ),
    Customer(
        customerId = 4,
        name = "João Silva",
        email = "joao.silva@hotmail.com",
        phone = "31978652738",
        gender = "Masculino",
        dateOfBirth = "29/01/1999",
        address = "Av. Brasil, Nº 32, Centro - Belo Horizonte MG",
        notes= ""
    )
)
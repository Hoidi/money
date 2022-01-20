package com.money.money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyApplication.class, args);
	}

}


/*
en sida
	andra ska inte kunna komma in och facka
	typ man skriver in hur mycket man lagt ut
		och sen andel som den andre ska betala
		eller summa som den andre ska betala
		kanske ett namn på "kvittot"

	också så ska man kunna se vad man är skyldig den andre
	också så ska kunna säga att man gjort kvitt eller hur mycket man skickat den andre


Transaktion:
	Från vem (vem betala)
	Till vem (vem blir skyldig)
	total summa
	summa som Till är skyldig Från
	tid
	namn?

Användare:
	Namn

Bank:
	List<Transaktioner>

*/

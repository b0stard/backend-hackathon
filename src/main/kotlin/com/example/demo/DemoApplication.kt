package com.example.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class DemoApplication

private val log = LoggerFactory.getLogger("StartupLogger")

fun main(args: Array<String>) {
	val port = System.getenv("PORT") ?: "8080"
	log.info("Starting application...")
	log.info("PORT from environment: {}", port)
	runApplication<DemoApplication>(*args)
}
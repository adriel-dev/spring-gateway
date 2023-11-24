package com.adriel.material

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class MaterialApplication

fun main(args: Array<String>) {
    runApplication<MaterialApplication>(*args)
}

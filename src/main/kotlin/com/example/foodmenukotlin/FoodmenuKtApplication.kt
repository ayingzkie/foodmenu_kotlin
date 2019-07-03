package com.example.foodmenukotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters

import javax.annotation.PostConstruct
import java.util.TimeZone

@SpringBootApplication
@EntityScan(basePackageClasses = arrayOf(FoodmenuKtApplication::class, Jsr310JpaConverters::class))
class FoodmenuKtApplication {

    @PostConstruct
    internal fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(FoodmenuKtApplication::class.java, *args)
        }
    }
}

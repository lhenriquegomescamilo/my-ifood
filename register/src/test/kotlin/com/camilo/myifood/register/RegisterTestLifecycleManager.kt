package com.camilo.myifood.register

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.PostgreSQLContainer

class RegisterTestLifecycleManager : QuarkusTestResourceLifecycleManager {

    companion object {
        val POSTGRES: PostgreSQLContainer<Nothing>? = PostgreSQLContainer("postgres:12.4")

    }

    override fun start(): MutableMap<String, String?> {
        POSTGRES?.start()
        return mutableMapOf(
            "quarkus.datasource.jdbc.url" to POSTGRES?.jdbcUrl,
            "quarkus.datasource.username" to POSTGRES?.username,
            "quarkus.datasource.password" to POSTGRES?.password
        )
    }

    override fun stop() {
        POSTGRES?.let { if (it.isRunning) it.stop() }
    }
}
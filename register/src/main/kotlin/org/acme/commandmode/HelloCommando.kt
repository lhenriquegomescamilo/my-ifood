package org.acme.commandmode

import javax.inject.Inject

import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain

@QuarkusMain
class HelloCommando: QuarkusApplication {

    override fun run(vararg args: String?): Int {
        val name =  if (args.isNotEmpty()) args.joinToString(",") else "commando"
        println(name)
        return 0
    }

}

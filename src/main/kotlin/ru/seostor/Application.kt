package ru.seostor
import io.ktor.server.application.*
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine
import ru.seostor.plugins.*
import sun.security.util.Password

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val mongoPassword = System.getenv("MONGO_PASSWORD")
    val dbName = "ktor-notes"
    val db = KMongo.createClient(
        connectionString = "mongodb+srv://nig517:$mongoPassword@cluster0.e7akcc5.mongodb.net/$dbName?retryWrites=true&w=majority\n"
    )
        .coroutine
        .getDatabase(dbName)
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting()
}

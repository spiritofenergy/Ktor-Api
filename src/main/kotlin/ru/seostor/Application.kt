package ru.seostor
import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureDSA.SHA256
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine
import ru.seostor.data.models.MongoUserDataSource
import ru.seostor.plugins.*
import ru.seostor.security.hashing.SHA256HashingService
import ru.seostor.security.token.JvtTokenService
import ru.seostor.security.token.TokenConfig

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

    val userDataSource = MongoUserDataSource(db)
    val tokenService = JvtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.issuer").getString(),
        exiresIn = 365L * 1000L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    configureMonitoring()
    configureSerialization()
    configureSecurity(tokenConfig)
    configureRouting(
        userDataSource = userDataSource,
        hashingService = hashingService,
        tokenService = tokenService,
        tokenConfig = tokenConfig
    )
}

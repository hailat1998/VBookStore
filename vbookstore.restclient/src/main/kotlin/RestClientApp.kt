package com.hd.vbookstore.restclient

import com.hd.vbookstore.commons.BookDto
import com.hd.vbookstore.commons.LoginUserDto
import com.hd.vbookstore.commons.RegisterUserDto
import com.hd.vbookstore.commons.TokenResponse
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.net.URI
import java.util.*

@SpringBootApplication
class RestClientApp

fun main(args: Array<String>){
   runApplication<RestClientApp>(*args)
}

@Component
class BookCommandLineRunner(
    private val restClient: RestClient,
    @Value("\${api.base-url:http://localhost:8080/api}") private val baseUrl: String // Better configuration
) : CommandLineRunner {
    companion object {
        private const val INITIAL_DELAY = 10_000L
        private const val BOOKS_OPERATION_DELAY = 5_000L
    }
    private val logger = LoggerFactory.getLogger(BookCommandLineRunner::class.java)
    var  userToken: TokenResponse? = null
    var adminToken: TokenResponse? = null
    override fun run(vararg args: String?) {
        runBlocking(Dispatchers.IO + SupervisorJob()) {
            try {
                val (newUserToken, newAdminToken) = executeAuthenticationFlow()
                userToken = newUserToken
                adminToken = newAdminToken
                executeBookOperations()
            } catch (e: Exception) {
                logger.error("Error occurred during execution", e)
            }
        }
    }

    private suspend fun executeAuthenticationFlow(): Pair<TokenResponse?, TokenResponse?> {
        val user = createRegistrationDto()
        val registeredUser = registerUser(user)
        logger.info("Registered user: ${registeredUser.body}")

        val userToken = loginUser("john_doe", "123456789")
        val adminToken = loginUser("admin", "1998@ht1234")

        return Pair(userToken.body, adminToken.body)
    }

    private fun createRegistrationDto() = RegisterUserDto(
         "john_doe",
        "123456789",
        "John Doe",
         "123 Main St",
         "New York",
         "NY",
        "10001",
         "123-456-7890",
         "john@example.com"
    )

    private suspend fun registerUser(user: RegisterUserDto): ResponseEntity<*> {
        val registerRequestEntity = RequestEntity.post(URI("$baseUrl/auth/signup"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(user)
        return restClient.register(registerRequestEntity)
    }

    private suspend fun loginUser(username: String, password: String): ResponseEntity<TokenResponse> {
        val loginDto = LoginUserDto(username, password)
        val requestEntity = RequestEntity.post(URI("$baseUrl/auth/login"))
            .contentType(MediaType.APPLICATION_JSON)
            .body(loginDto)
        return restClient.login(requestEntity)
    }

    private suspend fun executeBookOperations() = withContext(Dispatchers.IO) {

            delay(BOOKS_OPERATION_DELAY)


            val booksResponse = restClient.getBooks("$baseUrl/book/all")
            logger.info("Books received: ${booksResponse.body?.content}")


            val searchResponse = restClient.searchBook("$baseUrl/book/search", "War")
            logger.info("Search results: ${searchResponse.body?.content}")


            val authorCount = restClient.countByAuthor("$baseUrl/book/countByAuthor", "Leo Tolstoy")
            logger.info("${authorCount.body?.author} book count: ${authorCount.body?.bookCount}")

            val authorsBookCount = restClient.countByAuthors("$baseUrl/book/countByAuthors")
            logger.info("Authors book count: ")

            authorsBookCount.body?.forEach {
               logger.info("${it.author}: ${it.bookCount}")
             }


            executeSaveBookOperations(userToken, adminToken)
    }

    private suspend fun executeSaveBookOperations(
        userToken: TokenResponse?,
        adminToken: TokenResponse?
    ) {

        val adminSavedBook = saveBookWithRole(adminToken, "Admin")
        logger.info("Admin Saved book: ${adminSavedBook.body}")


        val userSavedBook = saveBookWithRole(userToken, "User")
        logger.info("User trying to save book; response: $userSavedBook")
    }

    private suspend fun saveBookWithRole(
        tokenResponse: TokenResponse?,
        role: String
    ): ResponseEntity<*> {

        val book = BookDto(
            "1984",
            "Science Fiction",
            "English",
            "978-0451524935",
            "George Orwell",
            Date(),
            3,
            12.99f
        )

        val requestEntity = RequestEntity.post(URI("$baseUrl/book/save"))
            .withBearerToken(tokenResponse)
            .contentType(MediaType.APPLICATION_JSON)
            .body(book)

        return restClient.saveBook("$baseUrl/book/save", requestEntity)
    }

    private fun RequestEntity.BodyBuilder.withBearerToken(
        tokenResponse: TokenResponse?
    ): RequestEntity.BodyBuilder {
        return this.header("Authorization", "Bearer ${tokenResponse?.getAccessToken()}")
    }
}

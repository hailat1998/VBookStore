package com.hd.vbookstore.restclient

import com.hd.vbookstore.commons.*
import com.hd.vbookstore.domain.enums.BorrowStatus
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
        private const val INITIAL_DELAY = 2000L
        private const val BOOKS_OPERATION_DELAY = 2000L
    }

    val bookUser = BookDto(
        null,
        "1984",
        "Science Fiction",
        "English",
        "978-0451524935",
        "George Orwell",
        Date(),
        3,
        12.99f
    )

    val bookAdmin =  BookDto(
        null,
        "1984",
        "Science Fiction",
        "English",
        "978-0451524935",
        "George Orwell",
        Date(),
        3,
        12.99f
    )

    private var  borrowedBookResponseDto: BorrowedBookResponseDto? = null
    
    private val logger = LoggerFactory.getLogger(BookCommandLineRunner::class.java)
    var  userToken: TokenResponse? = null
    var adminToken: TokenResponse? = null
    override fun run(vararg args: String?) {
        runBlocking(Dispatchers.IO + SupervisorJob()) {
            try {
                delay(INITIAL_DELAY)

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
        logger.info("Registered user: ${registeredUser.body.toString()}")

        val userToken = loginUser("john_doe", "123456789")
        val adminToken = loginUser("admin", "1998@ht1234")

        return Pair(userToken.getOrNull()?.body, adminToken.getOrNull()?.body)
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

    private suspend fun loginUser(username: String, password: String): Result<ResponseEntity<TokenResponse>> {
        return runCatching {
            val loginDto = LoginUserDto(username, password)
            val requestEntity = RequestEntity.post(URI("$baseUrl/auth/login"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(loginDto)
            restClient.login(requestEntity)
        }
    }

    private suspend fun executeBookOperations() = withContext(Dispatchers.IO) {

            delay(BOOKS_OPERATION_DELAY)

           // val booksResponse = restClient.getBooks("$baseUrl/book/all")
         //   logger.info("Books received: ${booksResponse.body?.content}")

           // val searchResponse = restClient.searchBook("$baseUrl/book/search", "War")
        //    logger.info("Search results: ${searchResponse.body?.content}")

            val authorCount = restClient.countByAuthor("$baseUrl/book/countByAuthor", "Leo Tolstoy")
            logger.info("${authorCount.body?.author} book count: ${authorCount.body?.bookCount}")

            val authorsBookCount = restClient.countByAuthors("$baseUrl/book/countByAuthors")
            logger.info("Authors book count: ")

            authorsBookCount.body?.forEach {
               logger.info("${it.author}: ${it.bookCount}")
             }

            executeSaveBookOperations(userToken, adminToken)
            executeBookBorrowOperations(userToken, adminToken)
    }

    private suspend fun executeSaveBookOperations(
        userToken: TokenResponse?,
        adminToken: TokenResponse?
    ) {

        val adminSavedBook = saveBook(adminToken, bookAdmin)
        logger.info("Admin Saved book: ${adminSavedBook.getOrNull()?.body}")

        val userSavedBook = saveBook(userToken, bookUser)
        logger.info("User trying to save book; response: $userSavedBook")
    }

    private suspend fun saveBook(
        tokenResponse: TokenResponse?,
        book: BookDto
    ): Result<ResponseEntity<*>> {
        return runCatching {
            val requestEntity = RequestEntity.post(URI("$baseUrl/book/save"))
                .withBearerToken(tokenResponse)
                .contentType(MediaType.APPLICATION_JSON)
                .body(book)

            restClient.saveBook("$baseUrl/book/save", requestEntity)
        }
    }


    private suspend fun executeBookBorrowOperations(
        userToken: TokenResponse?,
        adminToken: TokenResponse?
    ) {


        borrowedBookResponseDto =  setBorrow(adminToken).getOrNull()
        logger.info("User borrowed book: $borrowedBookResponseDto")

       val updateBorrow = updateBorrow(adminToken)
        logger.info("User update borrow: ${updateBorrow.getOrNull()}")
    }

    private suspend fun setBorrow(tokenResponse: TokenResponse?): Result<BorrowedBookResponseDto?> {
        return runCatching {
            val calendar = Calendar.getInstance()
            val startDate = Date()
            calendar.time = startDate
            calendar.add(Calendar.MONTH, 2)
            val endDate = calendar.time

            val borrowDto = BorrowDto(1L, startDate, endDate)

            val requestEntity = RequestEntity.post(URI("$baseUrl/borrow/set"))
                .withBearerToken(tokenResponse)
                .contentType(MediaType.APPLICATION_JSON)
                .body(borrowDto)

            val borrowedBook = restClient.setBorrow(requestEntity)

            borrowedBook.body
        }
    }

    private suspend fun updateBorrow(tokenResponse: TokenResponse?): Result<BorrowedBookResponseDto?> {
        return runCatching {
            val updateBorrowDto = UpdateBorrowDto(borrowedBookResponseDto?.id, BorrowStatus.RETURNED)

            val requestEntity = RequestEntity.post(URI("$baseUrl/borrow/update"))
                .withBearerToken(tokenResponse)
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateBorrowDto)

            restClient.updateBorrow(requestEntity).body
        }
    }



    private fun RequestEntity.BodyBuilder.withBearerToken(
        tokenResponse: TokenResponse?
    ): RequestEntity.BodyBuilder {
        return this.header("Authorization", "Bearer ${tokenResponse?.getAccessToken()}")
    }
}

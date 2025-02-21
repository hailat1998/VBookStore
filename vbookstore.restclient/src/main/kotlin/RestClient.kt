package com.hd.vbookstore.restclient

import com.hd.vbookstore.commons.*
import com.hd.vbookstore.domain.Book
import com.hd.vbookstore.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class RestClient {

    private val rest = RestTemplate()

    suspend fun register(userRequest: RequestEntity<RegisterUserDto>): ResponseEntity<User> {
        return rest.exchange(userRequest, User::class.java)
    }

    suspend fun login(loginRequestEntity: RequestEntity<LoginUserDto>) : ResponseEntity<TokenResponse> {
       return rest.exchange(loginRequestEntity, TokenResponse::class.java)
    }

    suspend fun getBooks(uri: String, page: Int = 0, size: Int = 10): ResponseEntity<RestPageImpl<Book>> =
        withContext(Dispatchers.IO) {
            val myURI = URI(uri)
            val builder = UriComponentsBuilder.fromUri(myURI)
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
                .toUri()

            val responseType = object : ParameterizedTypeReference<RestPageImpl<Book>>() {}

            rest.exchange(
                builder,
                HttpMethod.GET,
                null,
                responseType
            )
        }

    suspend fun getBook(uri: String): ResponseEntity<Book> =
        withContext(Dispatchers.IO) {
            rest.exchange(uri, HttpMethod.GET)
        }

    suspend fun saveBook(uri: String, book: RequestEntity<BookDto>): ResponseEntity<Book> =
        withContext(Dispatchers.IO) {
            rest.exchange(uri, HttpMethod.POST, book)
        }

    suspend fun searchBook(uri: String, searchTerm: String, page: Int = 0, size: Int = 10): ResponseEntity<RestPageImpl<Book>> =
        withContext(Dispatchers.IO) {
            val uri = URI(uri)
            val builder = UriComponentsBuilder.fromUri(uri)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("searchTerm", searchTerm)
                .build()
                .toUri()

            val responseType = object : ParameterizedTypeReference<RestPageImpl<Book>>() {}

            rest.exchange(
                builder,
                HttpMethod.GET,
                null,
                responseType
            )
        }

    suspend fun countByAuthor(uri: String, author: String = ""): ResponseEntity<AuthorBookCountDTO> =
        withContext(Dispatchers.IO) {
            val uriBuilder = UriComponentsBuilder.fromUriString(uri)
                .queryParam("author", author)
                .build()
                .toUri()

            val responseType = object : ParameterizedTypeReference<AuthorBookCountDTO>() {}

            rest.exchange(
                uriBuilder,
                HttpMethod.GET,
                null,
                responseType
            )
        }
    suspend fun countByAuthors(uri: String): ResponseEntity<List<AuthorBookCountDTO>> =
        withContext(Dispatchers.IO) {
            val uriBuilder = UriComponentsBuilder.fromUriString(uri)
                .build()
                .toUri()

            val responseType = object : ParameterizedTypeReference<List<AuthorBookCountDTO>>() {}

            rest.exchange(
                uriBuilder,
                HttpMethod.GET,
                null,
                responseType
            )
        }
}
package com.hd.vbookstore.restclient

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.data.web.config.SpringDataJacksonConfiguration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate


class AppConfig {

    @Bean
    fun restTemplate(): RestTemplate{
        val restTemplate = RestTemplate()

        // Add message converters
        val messageConverters = ArrayList<HttpMessageConverter<*>>()
        val jsonConverter = MappingJackson2HttpMessageConverter()
        messageConverters.add(jsonConverter)


        val objectMapper = ObjectMapper().apply {
            registerModule(KotlinModule.Builder().build())
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
        jsonConverter.objectMapper = objectMapper

        restTemplate.messageConverters = messageConverters
        return restTemplate
    }

    @Bean
    fun springDataPageModule(): SpringDataJacksonConfiguration.PageModule {
        return SpringDataJacksonConfiguration().pageModule()
    }

}
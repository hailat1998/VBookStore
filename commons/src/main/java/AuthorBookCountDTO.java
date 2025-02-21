package com.hd.vbookstore.commons;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorBookCountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonProperty("author")
    private String author;


    @JsonProperty("bookCount")
    private Long bookCount;

    public AuthorBookCountDTO(@NotNull String author, Long bookCount) {
        this.author = author;
        this.bookCount = bookCount;
    }

}

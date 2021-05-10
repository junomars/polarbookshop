package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.persistence.BookRepositoryImpl
import org.springframework.stereotype.Service

@Service
class BookService {
    private val bookRepository = BookRepositoryImpl()

    fun viewBookList(): Collection<Book> {
        return bookRepository.findAll()
    }

    fun viewBookDetails(isbn: String): Book? {
        return bookRepository.findByIsbnOrNull(isbn) ?: throw BookNotFoundException(isbn)
    }

    fun addBookToCatalog(book: Book): Book {
        if (bookRepository.existsByIsbn(book.isbn))
            throw BookAlreadyExistsException(book.isbn)

        return bookRepository.save(book)
    }

    fun removeBookFromCatalog(isbn: String) {
        if (!bookRepository.existsByIsbn(isbn))
            throw BookNotFoundException(isbn)

        bookRepository.deleteByIsbn(isbn)
    }

    fun editBookDetails(isbn: String, book: Book): Book {
        return bookRepository.findByIsbnOrNull(isbn)?.let {
            it.title = book.title
            it.author = book.author
            it.publishingYear = book.publishingYear
            it.price = book.price
            bookRepository.save(it)
        } ?: addBookToCatalog(book)
    }
}
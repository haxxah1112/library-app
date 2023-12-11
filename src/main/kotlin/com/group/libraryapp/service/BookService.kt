package com.group.libraryapp.service

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.dto.book.request.BookRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
  private val bookRepository: BookRepository,
) {

  @Transactional
  fun saveBook(request: BookRequest) {
    val book = Book(request.name, request.type)
    bookRepository.save(book)
  }

}
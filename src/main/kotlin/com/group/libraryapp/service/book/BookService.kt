package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
  private val bookRepository: BookRepository,
  private val userLoanHistoryRepository: UserLoanHistoryRepository,
  private val userRepository: UserRepository
) {

  @Transactional
  fun saveBook(request: BookRequest) {
    val book = Book(request.name, request.type)
    bookRepository.save(book)
  }

  @Transactional
  fun loanBook(request: BookLoanRequest) {
    val book = bookRepository.findByName(request.bookName) ?: throw IllegalArgumentException("not found book")
    if (userLoanHistoryRepository.findByBookNameAndIsReturn(request.bookName, false) != null) {
      throw IllegalArgumentException("이미 대출되어 있는 책입니다")
    }

    val user = userRepository.findByName(request.userName) ?: throw IllegalArgumentException("not found book")
    user.loanBook(book)
  }

  @Transactional
  fun returnBook(request: BookReturnRequest) {
    val user = userRepository.findByName(request.userName) ?: throw IllegalArgumentException("not found book")
    user.returnBook(request.bookName)
  }

  @Transactional(readOnly = true)
  fun countLoanBook(): Int {
    return userLoanHistoryRepository.countByStatus(UserLoanStatus.LOANED).toInt()
  }

  fun getBookStatistics(): List<BookStatResponse> {
    return bookRepository.getStats()
  }
}

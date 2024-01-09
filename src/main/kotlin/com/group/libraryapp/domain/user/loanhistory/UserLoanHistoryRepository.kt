package com.group.libraryapp.domain.user.loanhistory

import com.group.libraryapp.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long> {
    fun findByBookNameAndIsReturn(bookName: String?, isReturn: Boolean): UserLoanHistory?

    fun findAllByStatus(status: UserLoanStatus): List<UserLoanHistory>
}
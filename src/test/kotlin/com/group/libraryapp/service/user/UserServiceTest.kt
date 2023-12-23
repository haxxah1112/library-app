package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
  private val userRepository: UserRepository,
  private val userService: UserService,
  private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

  @AfterEach
  fun clean() {
    userRepository.deleteAll()
  }

  @Test
  @DisplayName("유저 저장이 정상 동작한다")
  fun saveUserTest() {
    // given
    val request = UserCreateRequest("최태현", null)

    // when
    userService.saveUser(request)

    // then
    val results = userRepository.findAll()
    assertThat(results).hasSize(1)
    assertThat(results[0].name).isEqualTo("최태현")
    assertThat(results[0].age).isNull()
  }

  @Test
  @DisplayName("유저 조회가 정상 동작한다")
  fun getUsersTest() {
    // given
    userRepository.saveAll(listOf(
      User("A", 20),
      User("B", null),
    ))

    // when
    val results = userService.getUsers()

    // then
    assertThat(results).hasSize(2) // [UserResponse(), UserResponse()]
    assertThat(results).extracting("name").containsExactlyInAnyOrder("A", "B") // ["A", "B"]
    assertThat(results).extracting("age").containsExactlyInAnyOrder(20, null)
  }

}
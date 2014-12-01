package com.alexshabanov.kotwebdemo.service

import java.util.Collections
import java.util.ArrayList

import com.truward.ascetic.Request
import com.truward.ascetic.Response
import com.truward.ascetic.CallResult
import com.truward.ascetic.CallResultCreator
import com.truward.ascetic.support.ImmediateCallResultCreator
import com.truward.ascetic.Context
import com.truward.ascetic.Service
import com.truward.ascetic.support.DefaultContext
import com.truward.ascetic.support.ServiceSupport

//
// Service API
//

data class UserProfile(val id: Long?,
                       val name: String,
                       val avatarUrl: String)

data class GetUserProfilesResponse(val profiles: List<UserProfile>): Response

data class GetUserProfilesRequest(override val context: Context = DefaultContext(),
                                  val userIds: List<Long>): Request

/** User service interface. */
trait UserService: Service {
  fun getUserProfiles(request: GetUserProfilesRequest): CallResult<GetUserProfilesResponse>
}

//
// Service Implementation
//

/** User service implementation. */
class UserServiceImpl(override val resultCreator: CallResultCreator): ServiceSupport, UserService {
  override fun getUserProfiles(request: GetUserProfilesRequest): CallResult<GetUserProfilesResponse> {
    return resultFrom(request, {
      val result = ArrayList<UserProfile>(it.userIds.size)
      for (id in it.userIds) {
        result.add(UserProfile(id = id, name = "user#${id}", avatarUrl = "http://avatarUrl_${id}"))
      }
      GetUserProfilesResponse(profiles = result)
    })
  }
}

//
// Demo
//

fun main(args: Array<String>) {
  // [1] "DI container" initialization
  val resultCreator = ImmediateCallResultCreator() // will be shared across different services
  // [2] User Service
  val userService = UserServiceImpl(resultCreator = resultCreator)

  // [3] Usage...
  println("Before using userService...")
  val response = userService.getUserProfiles(GetUserProfilesRequest(userIds = listOf(1L, 2L, 3L)))
  println("After using userService, result = ${response.get()}")
}

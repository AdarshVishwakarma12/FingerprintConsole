package com.example.figerprintconsole.app.ui.screen.users.event

import com.example.figerprintconsole.app.domain.model.User

sealed class UsersScreenEvent {
    object GetAllUsers: UsersScreenEvent()
    data class GetUserById(val user: User): UsersScreenEvent()
}
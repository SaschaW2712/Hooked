package com.saschaw.hooked.feature.search

import androidx.lifecycle.ViewModel
import com.saschaw.hooked.core.authentication.AuthenticationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    val authenticationManager: AuthenticationManager
) : ViewModel()

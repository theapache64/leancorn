package com.theapache64.leancorn.data.repo

import com.theapache64.leancorn.data.remote.ApiInterface
import javax.inject.Inject

class MoviesRepo @Inject constructor(
    private val apiInterface: ApiInterface
) : ApiInterface by apiInterface
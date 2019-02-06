package com.lckiss.usercenter.data.protocol;

import java.io.Serializable;

data class User(val id: Int, val email: String, val logo_url: String, val username: String, val mobi: String) : Serializable

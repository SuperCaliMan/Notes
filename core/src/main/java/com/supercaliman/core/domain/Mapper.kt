package com.supercaliman.core.domain

interface Mapper<in T,out R> {

    fun map(data:T):R
}
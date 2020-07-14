package com.supercaliman.domain

interface Mapper<in T,out R> {

    fun map(data:T):R
}
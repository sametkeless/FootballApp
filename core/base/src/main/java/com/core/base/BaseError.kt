package com.core.base

abstract class BaseError : Throwable() {
    abstract var resID: Int?
}
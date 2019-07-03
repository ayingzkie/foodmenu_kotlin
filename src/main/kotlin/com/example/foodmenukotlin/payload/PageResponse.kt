package com.example.foodmenukotlin.payload

class PagedResponse<T> {

    var content: List<T>? = null
    var page: Int = 0
    var size: Int = 0
    var totalElements: Long = 0
    var totalPages: Int = 0
    var isLast: Boolean = false

    constructor() {

    }

    constructor(content: List<T>, page: Int, size: Int, totalElements: Long, totalPages: Int, last: Boolean) {
        this.content = content
        this.page = page
        this.size = size
        this.totalElements = totalElements
        this.totalPages = totalPages
        this.isLast = last
    }
}

package com.example.foodmenukotlin.model.audit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy

import javax.persistence.MappedSuperclass


@MappedSuperclass
@JsonIgnoreProperties(value = *arrayOf("createdBy", "updatedBy"), allowGetters = true)
abstract class UserDateAudit : DateAudit() {

    @CreatedBy
    var createdBy: Long? = null

    @LastModifiedBy
    var updatedBy: Long? = null
}

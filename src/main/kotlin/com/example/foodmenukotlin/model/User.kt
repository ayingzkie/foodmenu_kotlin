package com.example.foodmenukotlin.model

import com.example.foodmenukotlin.model.audit.DateAudit
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import java.util.HashSet
import java.util.UUID

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = arrayOf("username")), UniqueConstraint(columnNames = arrayOf("email"))])
class User : DateAudit {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "uuid")
    @Type(type = "pg-uuid")
    var id: UUID? = null

    @NotBlank
    @Size(max = 40)
    var name: String? = null

    @NotBlank
    @Size(max = 15)
    var username: String? = null

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    var email: String? = null

    @NotBlank
    @Size(max = 100)
    var password: String? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")], inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: Set<Role> = HashSet()

    constructor() {

    }

    constructor(name: String, username: String, email: String, password: String) {
        this.name = name
        this.username = username
        this.email = email
        this.password = password
    }
}
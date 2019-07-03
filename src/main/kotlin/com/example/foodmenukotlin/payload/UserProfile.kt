package com.example.foodmenukotlin.payload

import java.time.Instant
import java.util.UUID

class UserProfile(var id: UUID?, var username: String?, var name: String?, var joinedAt: Instant?, var pollCount: Int?, var voteCount: Int?)

package me.antandtim.telegram.api.domain

import javax.persistence.*

@Entity
@Table
data class UserStep(
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long? = null,
  
  @Column
  val chatId: Long? = null,
  
  @Column
  val step: String? = null
)
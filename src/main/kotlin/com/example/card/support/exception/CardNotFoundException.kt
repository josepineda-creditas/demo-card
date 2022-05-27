package com.example.card.support.exception

class CardNotFoundException(id: String?) : Exception(
  "Card $id was not found"
)

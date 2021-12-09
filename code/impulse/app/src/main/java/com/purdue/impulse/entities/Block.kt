package com.purdue.impulse.entities

class Block(data: EventItem, hash: String, prev: Block?) {

    var data: EventItem = data
    var hash: String = hash
    var prev: Block? = prev
}
package com.purdue.impulse.entities

class Block(data: String, hash: String, prev: Block?) {

    var data: String = data
    var hash: String = hash
    var prev: Block? = prev
}
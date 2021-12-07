package com.purdue.impulse.entities

class EventItem(eventTitle: String, eventDetails: String, eventBounty: Double, eventTime: String) {

    constructor() : this("", "",0.0,"")

    var eventTitle: String = eventTitle
    var eventDetails: String = eventDetails
    var eventBounty: Double = eventBounty
    var eventTime: String = eventTime
}
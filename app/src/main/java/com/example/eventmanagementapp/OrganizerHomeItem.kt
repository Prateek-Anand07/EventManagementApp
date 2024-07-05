package com.example.eventmanagementapp

import java.sql.Time
import java.util.Date

class OrganizerHomeItem(val eventTitle: String, val eventVenue: String, val eventTime: String, val eventDate: String, val eventDetails: String, val organizerName: String, val organizerContact: String, val category: String, val userId: String) {
    constructor(): this("", "", "", "", "", "", "", "", "")
}
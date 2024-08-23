package com.utms.urbantrafficmanagementsimulation.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.Node
import java.util.*

@Node
data class Road(
    @Id @GeneratedValue
    var id: UUID? = null,
    var name: String,
    var length: Double? = null,  // Length in meters (optional for segments)
    var maxSpeed: Double? = null, // Max speed in km/h (optional for segments)

    @Relationship(type = "NEXT", direction = Relationship.Direction.OUTGOING)
    var next: List<Road> = mutableListOf()
)

@Node
data class Vehicle(
    @Id @GeneratedValue
    var id: UUID? = null,
    var name: String,
    var type: String,

    @Relationship(type = "CURRENT_LOCATION", direction = Relationship.Direction.OUTGOING)
    var currentLocation: Road? = null,

    var timeRequired: Double = 0.0, // Time needed to move to the next location
    var timePassed: Double = 0.0   // Time already spent on the current road
)
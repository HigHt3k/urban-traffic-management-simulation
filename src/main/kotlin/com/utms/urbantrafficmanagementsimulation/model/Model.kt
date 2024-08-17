package com.utms.urbantrafficmanagementsimulation.model

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.Node

@Node
data class Intersection(
    @Id
    var name: String,

    @Relationship(type = "CONNECTED_TO", direction = Relationship.Direction.OUTGOING)
    var connectedRoads: List<RoadSegment> = mutableListOf()
)

@Node
data class RoadSegment(
    @Id
    var name: String,
    var length: Double, //in meters
    var maxSpeed: Double, //in km/h

    @Relationship(type = "TO", direction = Relationship.Direction.OUTGOING)
    var toIntersection: Intersection? = null
)

@Node
data class Vehicle(
    @Id
    var name: String,
    var type: String,

    @Relationship(type = "CURRENT_LOCATION", direction = Relationship.Direction.OUTGOING)
    var currentLocation: Intersection? = null
)
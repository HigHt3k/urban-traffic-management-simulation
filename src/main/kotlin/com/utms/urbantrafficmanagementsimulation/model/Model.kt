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
    var length: Double,  // Length in meters (optional for segments)
    var maxSpeed: Double, // Max speed in km/h (optional for segments)
    var lanes: Int = 1,
    var hasBikeLane: Boolean = false,

    @Relationship(type = "NEXT", direction = Relationship.Direction.OUTGOING)
    var next: List<Road> = mutableListOf(),

    @Relationship(type = "HAS_TRAFFIC_LIGHT", direction = Relationship.Direction.OUTGOING)
    var trafficLight: TrafficLight? = null
)

@Node
data class Vehicle(
    @Id @GeneratedValue
    var id: UUID? = null,
    var name: String,
    var type: VehicleType,

    @Relationship(type = "CURRENT_LOCATION", direction = Relationship.Direction.OUTGOING)
    var currentLocation: Road? = null,

    var timeRequired: Double = 0.0, // Time needed to move to the next location
    var timePassed: Double = 0.0   // Time already spent on the current road
)

enum class VehicleType(val maxSpeedMultiplier: Double) {
    BIKE(0.5),
    CAR(1.0),
    TRUCK(0.9)
}

enum class TrafficLightState(val cycleTime: Double) {
    RED(30.0), GREEN(30.0), YELLOW(5.0)
}

@Node
data class TrafficLight(
    @Id @GeneratedValue
    var id: UUID? = null,
    var state: TrafficLightState = TrafficLightState.RED,
    var timeInCurrentState: Double = 0.0
)
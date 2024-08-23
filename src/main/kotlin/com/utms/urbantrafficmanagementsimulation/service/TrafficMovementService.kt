package com.utms.urbantrafficmanagementsimulation.service

import com.utms.urbantrafficmanagementsimulation.model.Vehicle
import com.utms.urbantrafficmanagementsimulation.repository.VehicleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TrafficMovementService(
    private val vehicleRepository: VehicleRepository
) {
    private val logger = LoggerFactory.getLogger(TrafficMovementService::class.java)

    fun run() {
        vehicleRepository.findAll().forEach { vehicle ->
            logger.info("Vehicle: $vehicle")
            moveVehicle(vehicle, 1.0)
        }
    }

    fun moveVehicle(vehicle: Vehicle, timeStep: Double) {
        val currentLocation = vehicle.currentLocation
        if (currentLocation == null) {
            logger.error("Vehicle ${vehicle.name} has no current location")
            return
        }

        if (vehicle.timeRequired <= vehicle.timePassed) {
            // Ready to move to the next road segment
            currentLocation.next.let { nextRoads ->
                if (nextRoads.isEmpty()) {
                    logger.info("Can't move from $currentLocation - no next road assigned")
                    return
                }
                val nextLocation = nextRoads.random()
                vehicle.currentLocation = nextLocation
                vehicle.timeRequired = nextLocation.length?.div((nextLocation.maxSpeed?.div(3.6)!!)) ?: return // km/h to m/s
                vehicle.timePassed = 0.0
            }
        } else {
            // Increment time passed on the current road
            vehicle.timePassed += timeStep
        }

        vehicleRepository.save(vehicle)
    }
}

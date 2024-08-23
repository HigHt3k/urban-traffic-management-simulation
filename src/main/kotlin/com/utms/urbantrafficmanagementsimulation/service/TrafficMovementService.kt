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
            moveVehicle(vehicle)
        }
    }

    fun moveVehicle(vehicle: Vehicle) {
        vehicle.currentLocation?.let { currentLocation ->
            if (currentLocation.next.isEmpty()) {
                logger.info("Can't move from $currentLocation - no next road assigned")
                return
            }

            // Pick a random next location
            val nextLocation = currentLocation.next.random()
            vehicle.currentLocation = nextLocation

            vehicleRepository.save(vehicle)
        } ?: logger.error("Vehicle ${vehicle.name} has no current location")
    }
}

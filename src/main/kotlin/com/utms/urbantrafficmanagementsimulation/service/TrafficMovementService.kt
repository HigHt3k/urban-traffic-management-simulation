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
        val vehicleDTOs = vehicleRepository.findAllVehicles()
        val vehicles = vehicleDTOs.map { dto ->
            dto.vehicle.apply {
                this.currentLocation = dto.intersection.apply {
                    this.connectedRoads = dto.roads.map { road ->
                        road.apply {
                            // Match the toIntersection by name
                            this.toIntersection = dto.nextIntersections.find { it.name == road.name.split("-")[1] }
                        }
                    }
                }
            }
        }
        logger.info("Found a total of ${vehicles.size} vehicles")
        vehicles.forEach { vehicle ->
            logger.info("Current vehicle ${vehicle.type} is at ${vehicle.currentLocation?.name}")
            logger.info("Moving vehicle with ID: ${vehicle.name} from intersection: ${vehicle.currentLocation?.name}")
            moveVehicle(vehicle)
        }
    }

    fun moveVehicle(vehicle: Vehicle) {
        val currentIntersection = vehicle.currentLocation ?: return
        val availableRoads = currentIntersection.connectedRoads

        if (availableRoads.isEmpty()) {
            logger.warn("No available roads to move the vehicle with ID: ${vehicle.name} from intersection: ${currentIntersection.name}")
            return
        }

        val nextRoad = availableRoads.random()
        logger.info("Vehicle with ID: ${vehicle.name} is moving from intersection ${currentIntersection.name} to ${nextRoad.toIntersection?.name} via road ${nextRoad.name}")

        // Remove the existing CURRENT_LOCATION relationship
        vehicleRepository.removeCurrentLocationRelationship(vehicle.name)

        // Update the vehicle's location
        vehicle.currentLocation = nextRoad.toIntersection

        vehicleRepository.save(vehicle)
        logger.info("Vehicle with ID: ${vehicle.name} successfully moved to intersection: ${vehicle.currentLocation?.name}")
    }

}

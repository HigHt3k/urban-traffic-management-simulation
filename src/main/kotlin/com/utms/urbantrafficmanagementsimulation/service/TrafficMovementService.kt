package com.utms.urbantrafficmanagementsimulation.service

import com.utms.urbantrafficmanagementsimulation.model.Intersection
import com.utms.urbantrafficmanagementsimulation.model.RoadSegment
import com.utms.urbantrafficmanagementsimulation.model.Vehicle
import com.utms.urbantrafficmanagementsimulation.repository.IntersectionRepository
import com.utms.urbantrafficmanagementsimulation.repository.RoadSegmentRepository
import com.utms.urbantrafficmanagementsimulation.repository.VehicleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TrafficMovementService(
    private val vehicleRepository: VehicleRepository,
    private val roadSegmentRepository: RoadSegmentRepository,
    private val intersectionRepository: IntersectionRepository
) {
    private val logger = LoggerFactory.getLogger(TrafficMovementService::class.java)

    fun run() {
        logger.info("Found following road segments: ${roadSegmentRepository.findAll()}")
        logger.info("Found following intersections: ${intersectionRepository.findAll()}")
        logger.info("Found following vehicles: ${vehicleRepository.findAll()}")
        /*
        val vehicleDTOs = vehicleRepository.findAllVehicles()
        val vehicles = vehicleDTOs.map { dto ->
            dto.vehicle.apply {
                this.currentLocation = dto.location
            }
        }
        logger.info("Found a total of ${vehicles.size} vehicles")
        vehicles.forEach { vehicle ->
            logger.info("Current vehicle ${vehicle.type} is at ${vehicle.currentLocation}")
            logger.info("Moving vehicle with ID: ${vehicle.name} from intersection: ${vehicle.currentLocation}")
            moveVehicle(vehicle)
        }
         */
    }

    fun moveVehicle(vehicle: Vehicle) {
        if(vehicle.currentIntersection != null) {
            val availableRoads = vehicle.currentIntersection!!.connectedRoads

            if (availableRoads.isEmpty()) {
                logger.warn("No available roads to move the vehicle ${vehicle.name} from current position: ${vehicle.currentIntersection}")
                return
            }

            // implement targets and simulation exactly here later
            val nextRoad = availableRoads.random()

            vehicle.id?.let { vehicleRepository.removeCurrentIntersectionRelationship(it) }

            vehicle.currentRoadSegment = nextRoad
            vehicleRepository.save(vehicle)
        }

        else if (vehicle.currentRoadSegment != null) {
            val nextLocation = vehicle.currentRoadSegment!!.nextSegment ?: vehicle.currentRoadSegment!!.toIntersection

            if (nextLocation == null) {
                logger.warn("No available roads to move the vehicle ${vehicle.name} from current position: ${vehicle.currentRoadSegment}")
                return
            }

            vehicle.id?.let { vehicleRepository.removeCurrentRoadSegmentRelationship(it) }

            when(nextLocation) {
                is Intersection -> {
                    vehicle.currentIntersection = nextLocation
                    vehicle.currentRoadSegment = null
                }
                is RoadSegment -> {
                    vehicle.currentRoadSegment = nextLocation
                    vehicle.currentIntersection = null
                }
                else -> logger.error("Next segment invalid type")
            }

            vehicleRepository.save(vehicle)
        }

        else {
            logger.error("Unknown location type for vehicle with ${vehicle.name}")
        }
    }
}

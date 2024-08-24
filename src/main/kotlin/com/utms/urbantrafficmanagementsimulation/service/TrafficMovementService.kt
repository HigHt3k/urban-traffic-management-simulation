package com.utms.urbantrafficmanagementsimulation.service

import com.utms.urbantrafficmanagementsimulation.model.TrafficLight
import com.utms.urbantrafficmanagementsimulation.model.TrafficLightState
import com.utms.urbantrafficmanagementsimulation.model.Vehicle
import com.utms.urbantrafficmanagementsimulation.model.VehicleType
import com.utms.urbantrafficmanagementsimulation.repository.TrafficLightRepository
import com.utms.urbantrafficmanagementsimulation.repository.VehicleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TrafficMovementService(
    private val vehicleRepository: VehicleRepository,
    private val trafficLightRepository: TrafficLightRepository
) {
    private val logger = LoggerFactory.getLogger(TrafficMovementService::class.java)

    fun run() {
        trafficLightRepository.findAll().forEach { trafficLight ->
            updateTrafficLight(trafficLight, 1.0)
        }
        vehicleRepository.findAll().forEach { vehicle ->
            logger.info("Vehicle: $vehicle")
            moveVehicle(vehicle, 1.0)
        }
    }

    fun moveVehicle(vehicle: Vehicle, timeStep: Double) {
        val currentLocation = vehicle.currentLocation ?: return logger.error("Vehicle $vehicle has no current location")

        // Check traffic lights
        if(currentLocation.trafficLight?.state == TrafficLightState.RED) {
            logger.info("Vehicle ${vehicle.name} stopped at red light at ${currentLocation.name}")
            return
        }

        var effectiveSpeed = currentLocation.maxSpeed * (vehicle.type.maxSpeedMultiplier / 3.6)
        if(vehicle.type == VehicleType.BIKE && currentLocation.hasBikeLane) {
            effectiveSpeed *= 1.1
        } else {
            effectiveSpeed *= currentLocation.lanes * 1.1
        }

        vehicle.timePassed += timeStep
        if(vehicle.timePassed >= vehicle.timeRequired) {
            currentLocation.next.randomOrNull()?.let { nextRoad ->
                vehicle.currentLocation = nextRoad
                vehicle.timeRequired = nextRoad.length / effectiveSpeed
                vehicle.timePassed = 0.0
                logger.info("Vehicle ${vehicle.name} moved to ${nextRoad.name}")
            } ?: logger.warn("No available roads for vehicle ${vehicle.name} at ${currentLocation.name}")
        }

        vehicleRepository.save(vehicle)
    }

    fun updateTrafficLight(trafficLight: TrafficLight, timeStep: Double) {
        trafficLight.timeInCurrentState += timeStep

        if (trafficLight.timeInCurrentState >= trafficLight.state.cycleTime) {
            trafficLight.timeInCurrentState = 0.0
            trafficLight.state = when (trafficLight.state) {
                TrafficLightState.RED -> TrafficLightState.GREEN
                TrafficLightState.GREEN -> TrafficLightState.YELLOW
                TrafficLightState.YELLOW -> TrafficLightState.RED
            }
        }
        trafficLightRepository.save(trafficLight)
    }
}

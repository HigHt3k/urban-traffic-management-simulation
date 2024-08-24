package com.utms.urbantrafficmanagementsimulation.service

import com.utms.urbantrafficmanagementsimulation.model.*
import com.utms.urbantrafficmanagementsimulation.repository.RoadRepository
import com.utms.urbantrafficmanagementsimulation.repository.TrafficLightRepository
import com.utms.urbantrafficmanagementsimulation.repository.VehicleRepository
import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DataInitializer(
    private val roadRepository: RoadRepository,
    private val vehicleRepository: VehicleRepository,
    private val trafficLightRepository: TrafficLightRepository
) {
    private val logger = LoggerFactory.getLogger(TrafficMovementService::class.java)

    @PostConstruct
    @Transactional
    fun init() {
        trafficLightRepository.deleteAll()
        roadRepository.deleteAll()
        vehicleRepository.deleteAll()

        val trafficLight1 = TrafficLight(state = TrafficLightState.GREEN)
        val trafficLight2 = TrafficLight(state = TrafficLightState.YELLOW)

        trafficLightRepository.saveAll(listOf(trafficLight1, trafficLight2))

        // Define intersections
        val elementA = Road(name = "A", length = 100.0, maxSpeed = 100.0, trafficLight = trafficLight1)
        val elementB = Road(name = "B", length = 500.0, maxSpeed = 50.0)
        val elementC = Road(name = "C", length = 300.0, maxSpeed = 40.0)
        val elementD = Road(name = "D", length = 700.0, maxSpeed = 60.0, trafficLight = trafficLight2)
        val elementE = Road(name = "E", length = 400.0, maxSpeed = 45.0)
        val elementF = Road(name = "F", length = 600.0, maxSpeed = 55.0)

        // Connect elements
        elementA.next = listOf(elementB, elementF)
        elementB.next = listOf(elementC)
        elementC.next = listOf(elementD)
        elementD.next = listOf(elementE)
        elementE.next = listOf()  // End of path
        elementF.next = listOf(elementE)  // Circular or additional path

        // Save elements
        roadRepository.saveAll(listOf(elementA, elementB, elementC, elementD, elementE, elementF))

        // Initialize vehicles
        val vehicle1 = Vehicle(name = "car1", type = VehicleType.CAR, currentLocation = elementA)
        val vehicle2 = Vehicle(name = "car2", type = VehicleType.CAR, currentLocation = elementB)
        val vehicle3 = Vehicle(name = "bike1", type = VehicleType.BIKE, currentLocation = elementB)

        // Save vehicles
        vehicleRepository.saveAll(listOf(vehicle1, vehicle2, vehicle3))

    }
}
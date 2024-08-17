package com.utms.urbantrafficmanagementsimulation.service

import com.utms.urbantrafficmanagementsimulation.model.Intersection
import com.utms.urbantrafficmanagementsimulation.model.RoadSegment
import com.utms.urbantrafficmanagementsimulation.model.Vehicle
import com.utms.urbantrafficmanagementsimulation.repository.IntersectionRepository
import com.utms.urbantrafficmanagementsimulation.repository.RoadSegmentRepository
import com.utms.urbantrafficmanagementsimulation.repository.VehicleRepository
import org.springframework.stereotype.Service

@Service
class DataInitializer(
    private val intersectionRepository: IntersectionRepository,
    private val roadSegmentRepository: RoadSegmentRepository,
    private val vehicleRepository: VehicleRepository
) {

    fun init() {
        // Define intersections
        val intersectionA = Intersection(name = "A")
        val intersectionB = Intersection(name = "B")
        val intersectionC = Intersection(name = "C")
        val intersectionD = Intersection(name = "D")
        val intersectionE = Intersection(name = "E")

        // Define road segments
        val roadAB = RoadSegment(name = "A-B", length = 500.0, maxSpeed = 50.0, toIntersection = intersectionB)
        val roadBC = RoadSegment(name = "B-C", length = 300.0, maxSpeed = 40.0, toIntersection = intersectionC)
        val roadCD = RoadSegment(name = "C-D", length = 700.0, maxSpeed = 60.0, toIntersection = intersectionD)
        val roadDE = RoadSegment(name = "D-E", length = 400.0, maxSpeed = 45.0, toIntersection = intersectionE)
        val roadAE = RoadSegment(name = "A-E", length = 600.0, maxSpeed = 55.0, toIntersection = intersectionE)

        // Set up relationships
        intersectionA.connectedRoads = listOf(roadAB, roadAE)
        intersectionB.connectedRoads = listOf(roadBC)
        intersectionC.connectedRoads = listOf(roadCD)
        intersectionD.connectedRoads = listOf(roadDE)

        // Save intersections and road segments to Neo4j
        intersectionRepository.save(intersectionA)
        intersectionRepository.save(intersectionB)
        intersectionRepository.save(intersectionC)
        intersectionRepository.save(intersectionD)
        intersectionRepository.save(intersectionE)

        roadSegmentRepository.save(roadAB)
        roadSegmentRepository.save(roadBC)
        roadSegmentRepository.save(roadCD)
        roadSegmentRepository.save(roadDE)
        roadSegmentRepository.save(roadAE)

        // setup vehicles
        val vehicle = Vehicle(type = "car", name = "car1", currentLocation = intersectionA)
        // save vehicles to Neo4j
        vehicleRepository.save(vehicle)
    }
}
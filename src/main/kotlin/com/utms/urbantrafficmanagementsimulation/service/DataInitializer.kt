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
        val roadAB2 = RoadSegment(name = "A-B2", length = 500.0, maxSpeed = 50.0, toIntersection = intersectionB)
        val roadAB1 = RoadSegment(name = "A-B1", length = 500.0, maxSpeed = 50.0, nextSegment = roadAB2)
        val roadBC = RoadSegment(name = "B-C", length = 300.0, maxSpeed = 40.0, toIntersection = intersectionC)
        val roadCD = RoadSegment(name = "C-D", length = 700.0, maxSpeed = 60.0, toIntersection = intersectionD)
        val roadDE = RoadSegment(name = "D-E", length = 400.0, maxSpeed = 45.0, toIntersection = intersectionE)
        val roadAE = RoadSegment(name = "A-E", length = 600.0, maxSpeed = 55.0, toIntersection = intersectionE)

        // Set up relationships
        intersectionA.connectedRoads = listOf(roadAB1, roadAE)
        intersectionB.connectedRoads = listOf(roadBC)
        intersectionC.connectedRoads = listOf(roadCD)
        intersectionD.connectedRoads = listOf(roadDE)

        // Save intersections and road segments to Neo4j
        intersectionRepository.save(intersectionA)
        intersectionRepository.save(intersectionB)
        intersectionRepository.save(intersectionC)
        intersectionRepository.save(intersectionD)
        intersectionRepository.save(intersectionE)

        roadSegmentRepository.save(roadAB1)
        roadSegmentRepository.save(roadAB2)
        roadSegmentRepository.save(roadBC)
        roadSegmentRepository.save(roadCD)
        roadSegmentRepository.save(roadDE)
        roadSegmentRepository.save(roadAE)

        // setup vehicles
        val vehicle = Vehicle(type = "car", name = "car1", currentIntersection = intersectionA, currentRoadSegment = null)
        val vehicle2 = Vehicle(type = "car", name = "car2", currentIntersection = null, currentRoadSegment = roadAB1)
        // save vehicles to Neo4j
        vehicleRepository.save(vehicle)
        vehicleRepository.save(vehicle2)
    }
}
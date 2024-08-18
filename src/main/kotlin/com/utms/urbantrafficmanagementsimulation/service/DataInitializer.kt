package com.utms.urbantrafficmanagementsimulation.service

import com.utms.urbantrafficmanagementsimulation.model.Intersection
import com.utms.urbantrafficmanagementsimulation.model.RoadSegment
import com.utms.urbantrafficmanagementsimulation.model.Vehicle
import com.utms.urbantrafficmanagementsimulation.repository.IntersectionRepository
import com.utms.urbantrafficmanagementsimulation.repository.RoadSegmentRepository
import com.utms.urbantrafficmanagementsimulation.repository.VehicleRepository
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DataInitializer(
    private val intersectionRepository: IntersectionRepository,
    private val roadSegmentRepository: RoadSegmentRepository,
    private val vehicleRepository: VehicleRepository
) {
    private val logger = LoggerFactory.getLogger(TrafficMovementService::class.java)

    @PostConstruct
    fun init() {
        intersectionRepository.deleteAll()
        roadSegmentRepository.deleteAll()
        vehicleRepository.deleteAll()
        // Define intersections
        val intersectionA = Intersection(name = "A")
        val intersectionB = Intersection(name = "B")
        val intersectionC = Intersection(name = "C")
        val intersectionD = Intersection(name = "D")
        val intersectionE = Intersection(name = "E")
        val intersectionF = Intersection(name = "F")
        val intersectionG = Intersection(name = "G")
        val intersectionH = Intersection(name = "H")
        val intersectionI = Intersection(name = "I")
        val intersectionJ = Intersection(name = "J")

        // Define road segments with varying segment lengths

        // Road 1: A to B (3 segments)
        val roadAB3 = RoadSegment(name = "A-B3", length = 500.0, maxSpeed = 50.0, toIntersection = intersectionB)
        val roadAB2 = RoadSegment(name = "A-B2", length = 500.0, maxSpeed = 50.0, nextSegment = roadAB3)
        val roadAB1 = RoadSegment(name = "A-B1", length = 500.0, maxSpeed = 50.0, nextSegment = roadAB2)

        // Road 2: B to C (1 segment)
        val roadBC = RoadSegment(name = "B-C", length = 300.0, maxSpeed = 40.0, toIntersection = intersectionC)

        // Road 3: C to D (4 segments)
        val roadCD4 = RoadSegment(name = "C-D4", length = 500.0, maxSpeed = 50.0, toIntersection = intersectionD)
        val roadCD3 = RoadSegment(name = "C-D3", length = 400.0, maxSpeed = 50.0, nextSegment = roadCD4)
        val roadCD2 = RoadSegment(name = "C-D2", length = 300.0, maxSpeed = 50.0, nextSegment = roadCD3)
        val roadCD1 = RoadSegment(name = "C-D1", length = 200.0, maxSpeed = 50.0, nextSegment = roadCD2)

        // Road 4: D to E (2 segments)
        val roadDE2 = RoadSegment(name = "D-E2", length = 400.0, maxSpeed = 45.0, toIntersection = intersectionE)
        val roadDE1 = RoadSegment(name = "D-E1", length = 400.0, maxSpeed = 45.0, nextSegment = roadDE2)

        // Road 5: A to E (1 segment)
        val roadAE = RoadSegment(name = "A-E", length = 600.0, maxSpeed = 55.0, toIntersection = intersectionE)

        // Road 6: E to F (5 segments)
        val roadEF5 = RoadSegment(name = "E-F5", length = 100.0, maxSpeed = 40.0, toIntersection = intersectionF)
        val roadEF4 = RoadSegment(name = "E-F4", length = 100.0, maxSpeed = 40.0, nextSegment = roadEF5)
        val roadEF3 = RoadSegment(name = "E-F3", length = 100.0, maxSpeed = 40.0, nextSegment = roadEF4)
        val roadEF2 = RoadSegment(name = "E-F2", length = 100.0, maxSpeed = 40.0, nextSegment = roadEF3)
        val roadEF1 = RoadSegment(name = "E-F1", length = 100.0, maxSpeed = 40.0, nextSegment = roadEF2)

        // Road 7: B to G (2 segments)
        val roadBG2 = RoadSegment(name = "B-G2", length = 400.0, maxSpeed = 45.0, toIntersection = intersectionG)
        val roadBG1 = RoadSegment(name = "B-G1", length = 300.0, maxSpeed = 45.0, nextSegment = roadBG2)

        // Road 8: G to H (1 segment)
        val roadGH = RoadSegment(name = "G-H", length = 400.0, maxSpeed = 50.0, toIntersection = intersectionH)

        // Road 9: Circular road around intersections H, I, J (3 segments)
        val roadHI = RoadSegment(name = "H-I", length = 300.0, maxSpeed = 30.0, toIntersection = intersectionI)
        val roadIJ = RoadSegment(name = "I-J", length = 300.0, maxSpeed = 30.0, toIntersection = intersectionJ)
        val roadJH = RoadSegment(name = "J-H", length = 300.0, maxSpeed = 30.0, toIntersection = intersectionH)

        // Road 10: F to C (3 segments)
        val roadFC3 = RoadSegment(name = "F-C3", length = 500.0, maxSpeed = 50.0, toIntersection = intersectionC)
        val roadFC2 = RoadSegment(name = "F-C2", length = 400.0, maxSpeed = 50.0, nextSegment = roadFC3)
        val roadFC1 = RoadSegment(name = "F-C1", length = 300.0, maxSpeed = 50.0, nextSegment = roadFC2)

        // Set up relationships
        intersectionA.connectedRoads = listOf(roadAB1, roadAE)
        intersectionB.connectedRoads = listOf(roadBC, roadBG1)
        intersectionC.connectedRoads = listOf(roadCD1)
        intersectionD.connectedRoads = listOf(roadDE1)
        intersectionE.connectedRoads = listOf(roadEF1)
        intersectionF.connectedRoads = listOf(roadFC1)
        intersectionG.connectedRoads = listOf(roadGH)
        intersectionH.connectedRoads = listOf(roadHI)
        intersectionI.connectedRoads = listOf(roadIJ)
        intersectionJ.connectedRoads = listOf(roadJH)

        // Save intersections and road segments to Neo4j
        intersectionRepository.save(intersectionA)
        intersectionRepository.save(intersectionB)
        intersectionRepository.save(intersectionC)
        intersectionRepository.save(intersectionD)
        intersectionRepository.save(intersectionE)
        intersectionRepository.save(intersectionF)
        intersectionRepository.save(intersectionG)
        intersectionRepository.save(intersectionH)
        intersectionRepository.save(intersectionI)
        intersectionRepository.save(intersectionJ)
        logger.info("Set up intersections")

        roadSegmentRepository.save(roadAB1)
        roadSegmentRepository.save(roadAB2)
        roadSegmentRepository.save(roadAB3)
        roadSegmentRepository.save(roadBC)
        roadSegmentRepository.save(roadCD1)
        roadSegmentRepository.save(roadCD2)
        roadSegmentRepository.save(roadCD3)
        roadSegmentRepository.save(roadCD4)
        roadSegmentRepository.save(roadDE1)
        roadSegmentRepository.save(roadDE2)
        roadSegmentRepository.save(roadAE)
        roadSegmentRepository.save(roadEF1)
        roadSegmentRepository.save(roadEF2)
        roadSegmentRepository.save(roadEF3)
        roadSegmentRepository.save(roadEF4)
        roadSegmentRepository.save(roadEF5)
        roadSegmentRepository.save(roadBG1)
        roadSegmentRepository.save(roadBG2)
        roadSegmentRepository.save(roadGH)
        roadSegmentRepository.save(roadHI)
        roadSegmentRepository.save(roadIJ)
        roadSegmentRepository.save(roadJH)
        roadSegmentRepository.save(roadFC1)
        roadSegmentRepository.save(roadFC2)
        roadSegmentRepository.save(roadFC3)
        logger.info("Set up roads")

        // setup vehicles
        val vehicle1 = Vehicle(type = "car", name = "car1", currentIntersection = intersectionA, currentRoadSegment = null)
        val vehicle2 = Vehicle(type = "car", name = "car2", currentIntersection = null, currentRoadSegment = roadAB1)
        val vehicle3 = Vehicle(type = "bus", name = "bus1", currentIntersection = intersectionF, currentRoadSegment = null)
        val vehicle4 = Vehicle(type = "bike", name = "bike1", currentIntersection = intersectionH, currentRoadSegment = null)
        val vehicle5 = Vehicle(type = "car", name = "car3", currentIntersection = null, currentRoadSegment = roadFC1)

        // save vehicles to Neo4j
        vehicleRepository.save(vehicle1)
        vehicleRepository.save(vehicle2)
        vehicleRepository.save(vehicle3)
        vehicleRepository.save(vehicle4)
        vehicleRepository.save(vehicle5)
        logger.info("Set up cars")

    }
}
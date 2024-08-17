package com.utms.urbantrafficmanagementsimulation.service

import com.utms.urbantrafficmanagementsimulation.repository.IntersectionRepository
import com.utms.urbantrafficmanagementsimulation.repository.RoadSegmentRepository
import com.utms.urbantrafficmanagementsimulation.repository.VehicleRepository
import org.springframework.stereotype.Service

@Service
class DatabaseCleanupService(
    private val intersectionRepository: IntersectionRepository,
    private val roadSegmentRepository: RoadSegmentRepository,
    private val vehicleRepository: VehicleRepository
) {

    fun clearDatabase() {
        intersectionRepository.deleteAll()
        roadSegmentRepository.deleteAll()
        vehicleRepository.deleteAll()
    }
}
package com.utms.urbantrafficmanagementsimulation.service

import com.utms.urbantrafficmanagementsimulation.repository.RoadRepository
import com.utms.urbantrafficmanagementsimulation.repository.VehicleRepository
import org.springframework.stereotype.Service

@Service
class DatabaseCleanupService(
    private val roadRepository: RoadRepository,
    private val vehicleRepository: VehicleRepository
) {

    fun clearDatabase() {
        roadRepository.deleteAll()
        vehicleRepository.deleteAll()
    }
}
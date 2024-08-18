package com.utms.urbantrafficmanagementsimulation.simulation

import com.utms.urbantrafficmanagementsimulation.service.TrafficMovementService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SimulationRunner(
    private val trafficMovementService: TrafficMovementService
) {

    private val logger = LoggerFactory.getLogger(SimulationRunner::class.java)

    fun runSimulation() {
        synchronized(this) {
            try {
                trafficMovementService.run()
            } catch (e: Exception) {
                logger.error("Error during simulation run: ${e.message}", e)
            }
        }
    }
}

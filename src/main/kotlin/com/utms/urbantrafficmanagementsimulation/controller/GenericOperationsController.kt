package com.utms.urbantrafficmanagementsimulation.controller

import com.utms.urbantrafficmanagementsimulation.service.DataInitializer
import com.utms.urbantrafficmanagementsimulation.service.DatabaseCleanupService
import com.utms.urbantrafficmanagementsimulation.simulation.SimulationRunner
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/operations")
class GenericOperationsController(
    private val databaseCleanupService: DatabaseCleanupService,
    private val dataInitializer: DataInitializer,
    private val simulationRunner: SimulationRunner
) {

    @DeleteMapping("/clear-database")
    fun clearDatabase(): ResponseEntity<String> {
        databaseCleanupService.clearDatabase()
        return ResponseEntity.ok("Database has been cleared successfully.")
    }

    @PostMapping("/load-initial-data")
    fun loadInitialData(): ResponseEntity<String> {
        dataInitializer.init()
        return ResponseEntity.ok("Database initially loaded")
    }

    @PostMapping("/run-simulation")
    fun runSimulation(@RequestParam("steps", defaultValue = "1") steps: Int): ResponseEntity<String> {
        repeat(steps) {
            simulationRunner.runSimulation()
        }
        return ResponseEntity.ok("Simulation ran for $steps step(s) successfully.")
    }
}
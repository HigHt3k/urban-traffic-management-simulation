package com.utms.urbantrafficmanagementsimulation.controller

import com.utms.urbantrafficmanagementsimulation.service.DataInitializer
import com.utms.urbantrafficmanagementsimulation.service.DatabaseCleanupService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/operations")
class GenericOperationsController(
    private val databaseCleanupService: DatabaseCleanupService,
    private val dataInitializer: DataInitializer
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
}
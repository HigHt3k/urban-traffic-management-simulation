package com.utms.urbantrafficmanagementsimulation.dto

import com.utms.urbantrafficmanagementsimulation.model.Vehicle

data class VehicleWithLocationDTO(
    val vehicle: Vehicle,
    val location: Any
)
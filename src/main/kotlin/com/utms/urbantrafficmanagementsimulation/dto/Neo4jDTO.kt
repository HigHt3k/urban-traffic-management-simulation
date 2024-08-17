package com.utms.urbantrafficmanagementsimulation.dto

import com.utms.urbantrafficmanagementsimulation.model.Intersection
import com.utms.urbantrafficmanagementsimulation.model.RoadSegment
import com.utms.urbantrafficmanagementsimulation.model.Vehicle

data class VehicleWithLocationDTO(
    val vehicle: Vehicle,
    val intersection: Intersection,
    val roads: List<RoadSegment>,
    val nextIntersections: List<Intersection>
)
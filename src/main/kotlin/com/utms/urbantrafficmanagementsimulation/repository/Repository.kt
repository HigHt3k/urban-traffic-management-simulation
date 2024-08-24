package com.utms.urbantrafficmanagementsimulation.repository

import com.utms.urbantrafficmanagementsimulation.model.Road
import com.utms.urbantrafficmanagementsimulation.model.TrafficLight
import com.utms.urbantrafficmanagementsimulation.model.Vehicle
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoadRepository: Neo4jRepository<Road, UUID>

@Repository
interface VehicleRepository : Neo4jRepository<Vehicle, UUID>

@Repository
interface TrafficLightRepository : Neo4jRepository<TrafficLight, UUID>

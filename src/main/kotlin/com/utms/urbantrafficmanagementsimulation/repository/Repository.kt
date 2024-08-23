package com.utms.urbantrafficmanagementsimulation.repository

import com.utms.urbantrafficmanagementsimulation.model.Road
import com.utms.urbantrafficmanagementsimulation.model.Vehicle
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoadRepository: Neo4jRepository<Road, UUID>

@Repository
interface VehicleRepository : Neo4jRepository<Vehicle, UUID> {

    @Query("""
        MATCH (v:Vehicle {id: :#{#id}})-[r:CURRENT_LOCATION]->(i:Road)
        DELETE r
    """)
    fun removeCurrentRoad(id: UUID)
}

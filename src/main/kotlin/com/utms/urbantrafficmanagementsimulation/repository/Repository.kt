package com.utms.urbantrafficmanagementsimulation.repository

import com.utms.urbantrafficmanagementsimulation.model.Intersection
import com.utms.urbantrafficmanagementsimulation.model.RoadSegment
import com.utms.urbantrafficmanagementsimulation.model.Vehicle
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IntersectionRepository: Neo4jRepository<Intersection, UUID>

@Repository
interface RoadSegmentRepository: Neo4jRepository<RoadSegment, UUID>

@Repository
interface VehicleRepository : Neo4jRepository<Vehicle, UUID> {

    @Query("""
        MATCH (v:Vehicle {id: :#{#id}})-[r:CURRENT_INTERSECTION]->(i:Intersection)
        DELETE r
    """)
    fun removeCurrentIntersectionRelationship(id: UUID)

    @Query("""
        MATCH (v:Vehicle {id: :#{#id}})-[r:CURRENT_ROAD_SEGMENT]->(i:ROAD_SEGMENT)
        DELETE r
    """)
    fun removeCurrentRoadSegmentRelationship(id: UUID)
}

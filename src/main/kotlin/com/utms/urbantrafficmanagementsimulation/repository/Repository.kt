package com.utms.urbantrafficmanagementsimulation.repository

import com.utms.urbantrafficmanagementsimulation.model.Intersection
import com.utms.urbantrafficmanagementsimulation.model.RoadSegment
import com.utms.urbantrafficmanagementsimulation.model.Vehicle
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface IntersectionRepository: Neo4jRepository<Intersection, String>

@Repository
interface RoadSegmentRepository: Neo4jRepository<RoadSegment, String>

@Repository
interface VehicleRepository : Neo4jRepository<Vehicle, String> {

    @Query("""
        MATCH (v:Vehicle {name: :#{#name}})-[r:CURRENT_INTERSECTION]->(i:Intersection)
        DELETE r
    """)
    fun removeCurrentIntersectionRelationship(name: String)

    @Query("""
        MATCH (v:Vehicle {name: :#{#name}})-[r:CURRENT_ROAD_SEGMENT]->(i:ROAD_SEGMENT)
        DELETE r
    """)
    fun removeCurrentRoadSegmentRelationship(name: String)
}

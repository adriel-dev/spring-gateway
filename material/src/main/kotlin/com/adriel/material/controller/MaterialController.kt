package com.adriel.material.controller

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

/*
    buscando simplicidade, tudo está no mesmo arquivo.
    foco está no uso do eureka e gateway.
 */

@RestController
@RequestMapping("/api/materials")
class MaterialController(private val materialRepository: MaterialRepository) {

    @GetMapping("/all")
    fun findAllMaterials(): ResponseEntity<List<Material>> {
        val materials = materialRepository.findAll()
        return ok().body(materials)
    }

    @PostMapping("/save")
    fun createMaterial(@RequestBody materialDTO: MaterialDTO): ResponseEntity<Any> {
        materialRepository.save(Material(0, materialDTO.name))
        return created(URI.create("/api/materials")).build()
    }

}

interface MaterialRepository {
    fun save(material: Material): Boolean
    fun findAll(): MutableList<Material>
}

@Component
class MaterialInMemoryRepository(private val materials: MutableList<Material> = mutableListOf(
    Material(1, "wood"),
    Material(2, "glass"),
    Material(3, "iron")
    ), private var lastId: Int = 3
): MaterialRepository {

    override fun findAll(): MutableList<Material> {
        return this.materials
    }

    override fun save(material: Material): Boolean {
        this.lastId++
        material.id = lastId
        this.materials.add(material)
        return true
    }

}

data class Material(var id: Int, val name: String)

data class MaterialDTO(val name: String)

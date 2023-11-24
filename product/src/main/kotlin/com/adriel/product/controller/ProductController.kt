package com.adriel.product.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/products")
class ProductController(private val productRepository: ProductRepository) {

    @GetMapping("/all")
    fun findAllProducts(): ResponseEntity<List<Product>> {
        val products = productRepository.findAll()
        return ResponseEntity.ok().body(products)
    }

    @PostMapping("/save")
    fun createProduct(@RequestBody productDTO: ProductDTO): ResponseEntity<Any> {
        productRepository.save(Product(0, productDTO.name))
        return ResponseEntity.created(URI.create("/api/products")).build()
    }

}

interface ProductRepository {
    fun save(product: Product): Boolean
    fun findAll(): MutableList<Product>
}

@Component
class ProductInMemoryRepository(private val products: MutableList<Product> = mutableListOf(
    Product(1, "door"),
    Product(2, "window")
), private var lastId: Int = 2
): ProductRepository {

    override fun findAll(): MutableList<Product> {
        return this.products
    }

    override fun save(product: Product): Boolean {
        this.lastId++
        product.id = lastId
        this.products.add(product)
        return true
    }

}

data class Product(var id: Int, val name: String)

data class ProductDTO(val name: String)

package com.example.demo.controller



import com.example.demo.service.MetaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/meta")
class MetaController(
    private val metaService: MetaService
) {

    @GetMapping
    fun getMeta() = metaService.getMeta()
}
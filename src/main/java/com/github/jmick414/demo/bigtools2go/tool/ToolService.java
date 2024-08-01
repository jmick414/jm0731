package com.github.jmick414.demo.bigtools2go.tool;

import org.springframework.stereotype.Service;

@Service
public class ToolService {

    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public Tool getToolByCode(String toolCode) {
        return toolRepository.findById(toolCode).orElse(null);
    }
}

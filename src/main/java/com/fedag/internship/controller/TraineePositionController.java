package com.fedag.internship.controller;

import com.fedag.internship.domain.dto.TraineePositionRequest;
import com.fedag.internship.domain.dto.TraineePositionResponse;
import com.fedag.internship.domain.dto.TraineePositionUpdate;
import com.fedag.internship.domain.entity.TraineePositionEntity;
import com.fedag.internship.domain.mapper.TraineePositionMapper;
import com.fedag.internship.service.TraineePositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class TraineePositionController {
    private final TraineePositionService positionService;
    private final TraineePositionMapper positionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<TraineePositionResponse> getPosition(@PathVariable Long id) {
        TraineePositionResponse companyResponse = Optional.of(id)
                .map(positionService::getPositionById)
                .map(positionMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(companyResponse, OK);
    }

    @GetMapping
    public ResponseEntity<Page<TraineePositionResponse>> getAllPosition(@PageableDefault(size = 10) Pageable pageable) {
        Page<TraineePositionResponse> positions = positionService.getAllPositions(pageable)
                .map(positionMapper::toResponse);
        return new ResponseEntity<>(positions, OK);
    }

    @PostMapping
    public ResponseEntity<TraineePositionResponse> createPosition(@RequestParam Long id,
                                                         @RequestBody @Valid TraineePositionRequest positionRequest) {
        TraineePositionResponse positionResponse = Optional.ofNullable(positionRequest)
                .map(positionMapper::fromRequest)
                .map(positionEntity -> positionService.createPosition(id, positionEntity))
                .map(positionMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(positionResponse, CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TraineePositionResponse> updatePosition(@PathVariable Long id,
                                                         @RequestBody TraineePositionUpdate positionUpdate) {
        TraineePositionResponse positionResponse = Optional.ofNullable(positionUpdate)
                .map(positionMapper::fromRequestUpdate)
                .map(position -> positionService.updatePosition(id, position))
                .map(positionMapper::toResponse)
                .orElseThrow();
        return new ResponseEntity<>(positionResponse, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return new ResponseEntity<>(OK);
    }
}

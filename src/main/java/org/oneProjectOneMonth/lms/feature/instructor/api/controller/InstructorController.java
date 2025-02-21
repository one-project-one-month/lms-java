//package org.oneProjectOneMonth.lms.feature.instructor.api.controller;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.oneProjectOneMonth.lms.config.exception.EntityNotFoundException;
//import org.oneProjectOneMonth.lms.config.response.dto.ApiResponseDTO;
//import org.oneProjectOneMonth.lms.feature.instructor.domain.model.Instructor;
//import org.oneProjectOneMonth.lms.feature.instructor.domain.service.InstructorService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * Author : Min Myat Thu Kha
// * Created At : 15/02/2025, Feb
// * Project Name : lms-java
// **/
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/instructor")
//@Slf4j
//@Tag(name = "Admin", description = "Enrollment Api")
//public class InstructorController {
//
//    private final InstructorService instructorService;
//
//    @GetMapping
//    public ResponseEntity<ApiResponseDTO<List<Instructor>>> getAllInstructors(
//            //From
//            //To
//            //Size
//    ) {
//        List<Instructor> instructors = instructorService.getAllInstructors();
//        return ResponseEntity.ok(new ApiResponseDTO<>(instructors, "Fetched all instructors successfully"));
//    }
//
//    @PostMapping
//    public ResponseEntity<ApiResponseDTO<Boolean>> createInstructor(@Valid @RequestBody Instructor instructor) {
//        try {
//            Instructor createdInstructor = instructorService.createInstructor(instructor);
//            if (createdInstructor == null) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(new ApiResponseDTO<>(false, "Failed to create instructor"));
//            }
//            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Instructor created successfully"));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ApiResponseDTO<>(false, "An error occurred: " + e.getMessage()));
//        }
//    }
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponseDTO<Instructor>> updateInstructor(
//            @PathVariable Long id,
//            @Valid @RequestBody Instructor instructor) {
//        try {
//            Instructor updatedInstructor = instructorService.updateInstructor(id, instructor);
//            return ResponseEntity.ok(new ApiResponseDTO<>(updatedInstructor, "Update Successful"));
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ApiResponseDTO<>("fail", e.getMessage(), null));
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<ApiResponseDTO<Boolean>> deleteInstructor(@PathVariable Long id) {
//        try {
//            instructorService.deleteInstructor(id);
//            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Delete Successful"));
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ApiResponseDTO<>("fail", e.getMessage(), false));
//        }
//    }
//}

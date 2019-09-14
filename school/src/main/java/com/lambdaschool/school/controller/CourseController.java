package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;
    @ApiOperation(value = "Return All Courses with Paging and Sorting", response = Course.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "Results page you want to retrieve(0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "Number of records per page"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria supported")})
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCoursesWithPaging(
            @PageableDefault(page = 0,
                             size = 3)
                    Pageable pageable)
    {
        ArrayList<Course> myCourses = courseService.findAll(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }
    @ApiOperation(value = "Return A Count of Students in Courses", response = Course.class, responseContainer = "List")
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }
    @ApiOperation(value = "Deletes a course based on id", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Course Deleted Successfully", response = void.class),
    })
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(
            @ApiParam(value = "Course Id", required = true, example = "1")
            @PathVariable long courseid)
    {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

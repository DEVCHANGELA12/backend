package in.ac.charusat.studentmgmtsystem.controller;

import in.ac.charusat.studentmgmtsystem.model.Student;
import in.ac.charusat.studentmgmtsystem.payload.LoginRequest;
import in.ac.charusat.studentmgmtsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AuthenticationManager authenticationManager;
//    List<Student> students = new ArrayList<>(
//            Arrays.asList(
//                    new Student(1, "Tom", "US"),
//                    new Student(2, "Harry", "Canada"),
//                    new Student(3, "Nick", "UK")
//            )
//    );

    // Mappings - URL endpoints
    // Get the list of all student
    @GetMapping
    public String getWelcomeMessage(){
        return "<h1> Welcome to the world of Spring Boot!!!</h1>";
    }
    @GetMapping("/listStudents")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get the student information
    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable Integer id) {
        return studentRepository.findById(id).get();
    }

    // Delete the student
    @DeleteMapping("/student/{id}")
    public List<Student> deleteStudent(@PathVariable Integer id) {
        studentRepository.delete(studentRepository.findById(id).get());
        return studentRepository.findAll();
    }

    // Add new student
    @PostMapping("/student")
    public List<Student> addStudent(@RequestBody Student student) {
        studentRepository.save(student);
        return studentRepository.findAll();
    }

    // Update the student information
    @PutMapping("/student/{id}")
    public List<Student> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
        Student studentObj = studentRepository.findById(id).get();
        studentObj.setName(student.getName());
        studentObj.setAddress(student.getAddress());
        studentRepository.save(studentObj);
        return studentRepository.findAll();
    }

    @PostMapping("/login")
    public String doLogin(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Login Successful";
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Logout");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Logged out successful";
    }
}

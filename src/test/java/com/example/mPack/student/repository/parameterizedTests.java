package com.example.mPack.student.repository;


import com.example.mPack.student.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class parameterizedTests {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void testCompare (){
        assertEquals(4,2+2);
    }

    @Test
    void testFindByName(){

        assertNotNull(studentRepository.findByName("Ehahi"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "1,2,1"
    })
    void test (int a, int b, int expected){
        assertEquals(a+b, expected);
    }

    @ParameterizedTest
    @CsvSource({
            "Saiful Imteaz",
            "Ehahi",
            "abdul"
    })
    void findByName(String name){
        assertNotNull(studentRepository.findByName(name), "Failed for "+name);

    }

    @ParameterizedTest
    @ValueSource(longs = {
            102,103,104,105,106,107


            })
    void findById(Long id ){
       Optional<Student> student=studentRepository.findById(id);
        assertNotNull(studentRepository.findById(id));
        assertTrue(student.isPresent(), "Failed for ID : "+id);
        student.ifPresent(student1 -> System.out.println(student));
    }
}

package com.deloitte.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.deloitte.entity.Student;

public class StudentServiceTest {

	@Test
	public void test() {
		
		StudentService sSvc = new StudentService();
		Student st1 = sSvc.getStudentById(1);
		
		assertEquals(1, st1.getId());
		assertEquals("Said", st1.getName());
	}

}

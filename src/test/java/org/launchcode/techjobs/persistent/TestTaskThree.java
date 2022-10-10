package org.launchcode.techjobs.persistent;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Test;
import org.launchcode.techjobs.persistent.controllers.HomeController;
import org.launchcode.techjobs.persistent.models.AbstractEntity;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskThree extends AbstractTest {

    @Test
    public void testJobFieldIsProperlyDefined() throws ClassNotFoundException, IllegalAccessException {
        Class employerClass = getClassByName("models.Employer");
        Field jobsField = null;

        try {
            jobsField = employerClass.getDeclaredField("jobs");
        } catch (NoSuchFieldException e) {
            fail("Employer does not have a jobs field");
        }

        Type type = jobsField.getType();
        assertEquals(List.class, type);

        Employer employer = new Employer();
        jobsField.setAccessible(true);
        ArrayList<Job> initializedList = (ArrayList<Job>) jobsField.get(employer);

        for (Job item : initializedList) {
            fail("jobs should be initialized to an empty ArrayList");
        }
    }

    @Test
    public void testJobsHasCorrectPersistenceAnnotations() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class employerClass = getClassByName("models.Employer");
        Field jobsField = employerClass.getDeclaredField("jobs");

        assertNotNull(jobsField.getAnnotation(OneToMany.class));
        Annotation joinColAnnotation = jobsField.getAnnotation(JoinColumn.class);
        Method nameMethod = joinColAnnotation.getClass().getMethod("name");
        assertEquals("employer_id", nameMethod.invoke(joinColAnnotation));
    }

    @Test
    public void testJobExtendsAbstractEntity() throws ClassNotFoundException {
        Class jobClass = getClassByName("models.Job");
        assertEquals(AbstractEntity.class, jobClass.getSuperclass());

        try {
            jobClass.getDeclaredField("name");
            fail("Job should not have a name field");
        } catch (NoSuchFieldException e) {
            // Do nothing - we expect this to be thrown
        }

        try {
            jobClass.getDeclaredField("id");
            fail("Job should not have an id field");
        } catch (NoSuchFieldException e) {
            // Do nothing - we expect this to be thrown
        }
    }

    @Test
    public void testEmployerField() throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        Class jobClass = getClassByName("models.Job");
        Field employerField = jobClass.getDeclaredField("employer");

        assertEquals(Employer.class, employerField.getType());

        Method getEmployerMethod = jobClass.getMethod("getEmployer");
        assertEquals(Employer.class, getEmployerMethod.getReturnType());

        try {
            Method setEmployerMethod = jobClass.getMethod("setEmployer", Employer.class);
        } catch (NoSuchMethodException e) {
            fail("Employer should have a setEmployer method that returns an instance of Employer");
        }

        assertNotNull(employerField.getAnnotation(ManyToOne.class));
    }

    @Test
    public void testHomeControllerUsesEmployerRepository() throws ClassNotFoundException {
        Class homeControllerClass = getClassByName("controllers.HomeController");
        Field employerRepositoryField = null;

        try {
            employerRepositoryField = homeControllerClass.getDeclaredField("employerRepository");
        } catch (NoSuchFieldException e) {
            fail("HomeController should have an employerRepository field");
        }

        Class employerRepositoryClass = getClassByName("models.data.EmployerRepository");
        assertEquals(employerRepositoryClass, employerRepositoryField.getType());

        assertNotNull(employerRepositoryField.getAnnotation(Autowired.class));
    }

    @Test
    public void testHomeControllerFetchesEmployers(@Mocked EmployerRepository employerRepository, @Mocked SkillRepository skillRepository) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class homeControllerClass = getClassByName("controllers.HomeController");
        HomeController homeController = new HomeController();

        Field employerRepositoryField = homeControllerClass.getDeclaredField("employerRepository");
        employerRepositoryField.setAccessible(true);
        employerRepositoryField.set(homeController, employerRepository);

        Field skillRepositoryField = null;
        try {
            skillRepositoryField = homeControllerClass.getDeclaredField("skillRepository");
            skillRepositoryField.setAccessible(true);
            skillRepositoryField.set(homeController, skillRepository);
        } catch (NoSuchFieldException e) {
            // do nothing
        }

        Model model = new ExtendedModelMap();

        new Expectations() {{
            employerRepository.findAll();
        }};

        homeController.displayAddJobForm(model);
    }

    @Test
    public void testSqlQuery() throws IOException {
        String queryFileContents = getFileContents("queries.sql");

        Pattern queryPattern = Pattern.compile("DROP\\s+TABLE\\s+job;", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher queryMatcher = queryPattern.matcher(queryFileContents);
        boolean queryFound = queryMatcher.find();
        assertTrue(queryFound, "Task 3 SQL query is incorrect. Test your query against your database to find the error.");
    }

}
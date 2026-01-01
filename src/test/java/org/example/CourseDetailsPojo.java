package org.example;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsPojo {

    // Serialization - converting java object to request/response payload.
    // DeSerialization - Converting request/response payload to java object.




    public static void main(String[] args) {
        List<WebAutomation> webAutomationList = new ArrayList<>();
        List<API> apiList = new ArrayList<>();
        List<Mobile> mobileList = new ArrayList<>();
        WebAutomation seleniumWD = new WebAutomation();
        seleniumWD.setCourseTitle("Selenium WebDriver Java");
        seleniumWD.setPrice("50");
        WebAutomation protractor = new WebAutomation();
        protractor.setCourseTitle("Protractor");
        protractor.setPrice("40");
        WebAutomation playwright = new WebAutomation();
        playwright.setCourseTitle("Playwright using Java");
        playwright.setPrice("50");
        API restAssured = new API();
        restAssured.setCourseTitle("Rest Assured automation Java");
        restAssured.setPrice("40");
        API soap = new API();
        soap.setCourseTitle("SOAP automation Java");
        soap.setPrice("40");
        Mobile android = new Mobile();
        android.setCourseTitle("Android automation Java");
        android.setPrice("50");
        Mobile ios = new Mobile();
        ios.setCourseTitle("ios automation Java");
        ios.setPrice("50");

        webAutomationList.add(seleniumWD);
        webAutomationList.add(protractor);
        webAutomationList.add(playwright);
        apiList.add(restAssured);
        apiList.add(soap);
        mobileList.add(android);
        mobileList.add(ios);

        Courses courses = new Courses();

        courses.setWebAutomation(webAutomationList);
        courses.setApi(apiList);
        courses.setMobile(mobileList);

        CoursesPojo coursesPojo = new CoursesPojo();
        coursesPojo.setInstructor("Arun");
        coursesPojo.setExpertise("Automation");
        coursesPojo.setServices("learning");
        coursesPojo.setCoursesList(courses);



    }




}


// Pojo class to achieve serialization and deserialization

class CoursesPojo {
    private String instructor;
    private String url;
    private String services;
    private String expertise;

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    private Courses courses;

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    private String linkedIn;

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Courses getCoursesList() {
        return courses;
    }

    public void setCoursesList(Courses coursesList) {
        this.courses = coursesList;
    }
}


class Courses {
    private List<WebAutomation> webAutomation;
    private List<API> api;
    private List<Mobile> mobile;

    public List<WebAutomation> getWebAutomation() {
        return webAutomation;
    }

    public void setWebAutomation(List<WebAutomation> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public List<API> getApi() {
        return api;
    }

    public void setApi(List<API> api) {
        this.api = api;
    }

    public List<Mobile> getMobile() {
        return mobile;
    }

    public void setMobile(List<Mobile> mobile) {
        this.mobile = mobile;
    }
}


class WebAutomation {
    private String courseTitle;
    private String price;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

class API {
    private String courseTitle;
    private String price;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

class Mobile {
    private String courseTitle;
    private String price;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

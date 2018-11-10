package com.ebs.beans;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Servlet implementation class ObjectToJson
 */
import org.codehaus.jackson.map.ObjectMapper;
public class ObjectToJson{

    public static void main(String[] a){
         
        Employee emp = new Employee();
        ObjectMapper mapperObj = new ObjectMapper();
         
        try {
            // get Employee object as a json string
            String jsonStr = mapperObj.writeValueAsString(emp);
            System.out.println(jsonStr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

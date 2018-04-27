package com.asseco.cas.controllers;


import com.asseco.cas.interfaces.ParameterInterface;
import com.asseco.cas.parameters.dao.ParameterRepositoryImpl;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cass.application.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@ComponentScan
@RestController
@EnableAutoConfiguration
public class ParameterController {

//    @Autowired
    private ParameterInterface parameterInterface;

    @Autowired
    public ParameterController(ParameterInterface parameterInterface){
        this.parameterInterface = parameterInterface;
    }


    @RequestMapping(value = "/parameter-items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterItem> readAll() {
        System.out.println("udje u read all");
        if(parameterInterface == null) {
            System.out.println("djole djole");
        } else {System.out.println("nece djole");}
        return parameterInterface.readList();
    }


    @RequestMapping(value = "/parameter-item", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem addParameter(@RequestBody ParameterItem parameterItem, HttpServletResponse response){
        //TODO proveriti sta radi ovo!
        boolean check1 = parameterItem.getKey() == null ? false : true;
        boolean check2 = parameterItem.getValue() == null ? false : true;
        boolean check3 = parameterItem.getDescription() == null ? false : true;

        if(check1 && check2 && check3) {
            try {
                parameterInterface.save(parameterItem);
                response.setStatus(201);
                return findById(parameterItem.getId(), response);
            } catch (Exception e){
                response.setStatus(400);
                return null;
            }
        }
        response.setStatus(400);
        return null;
    }


    @RequestMapping (value = "/parameter-item", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem updateParameter (@RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem p = parameterInterface.update(parameterItem);
        if (!(p==null)) {
            response.setStatus(200);
            return findById(parameterItem.getId(), response);
        }
        response.setStatus(400);
        return null;

    }

    @RequestMapping(value = "/parameter-item/{list}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "list")Long idParameterList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        try {
            response.setStatus(204);
            parameterInterface.delete(idParameterList, parameterItem);
            return ("Successfully deleted ParameterItem with ID: " + String.valueOf(parameterItem.getId()));
        } catch (ApplicationException e) {
            response.setStatus(400);
            System.out.println(e.getStackTrace());
            return e.getMessage();
        }

    }

    @RequestMapping(value = "/parameter-item/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem findById(@PathVariable(value = "id")Long id, HttpServletResponse response){
        ParameterItem p = parameterInterface.findById(id);
        if (!(p==null)) {
            if(response==null)
                response.setStatus(200);
            return p;
        }
        response.setStatus(400);
        return null;
    }

    @RequestMapping (value = "/parameter-items/{name:[\\D]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<ParameterItem> allFromList(@PathVariable(value = "name")String name, HttpServletResponse response){
        try {
            ArrayList<ParameterItem> p = (ArrayList<ParameterItem>) parameterInterface.findAllParameterFromList(name);
            if (!(p.isEmpty())){
                response.setStatus(200);
                return p;
            } else {
                response.setStatus(400);
                return null;
            }
        } catch (Exception e){response.setStatus(400); return null;}



    }

    @RequestMapping (value = "/parameter-items/{id:[\\d]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterItem> allFromList(@PathVariable(value = "id")Long id, HttpServletResponse response){
        List<ParameterItem> p = parameterInterface.findAllParameterFromList(id);

        if (!(p.isEmpty())){
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);
            return null;
        }

    }


    @RequestMapping (value = "/parameter-item/{list}/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem getByName(@PathVariable(value = "list")String listName, @PathVariable(value = "key")String parameterKey, HttpServletResponse response){
        ParameterItem p = parameterInterface.getParameterFromListByName(listName, parameterKey);
        if (p!=null) {
            response.setStatus(200);
            return p;
        } else {
            response.setStatus(400);
            return null;
        }
    }

}
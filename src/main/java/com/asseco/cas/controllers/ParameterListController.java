package com.asseco.cas.controllers;


import com.asseco.cas.facade.ParameterListFacade;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@ComponentScan
@RestController
@EnableAutoConfiguration
public class ParameterListController {


    private ParameterListFacade parameterListFacade;

    @Autowired
    public ParameterListController(ParameterListFacade parameterListFacade){
        this.parameterListFacade = parameterListFacade;
    }


    @RequestMapping(value = "/parameter-lists", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParameterList> getParameterLists(HttpServletResponse response){
        List<ParameterList> list  = parameterListFacade.findAll();
        if (!list.isEmpty()){
            return list;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists/{id}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList getParameterList(@PathVariable(value = "id")Long id, HttpServletResponse response){
        ParameterList list =  parameterListFacade.findById(id);
        if (!(list==null)){
            response.setStatus(200);
            return list;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter/{idList}/{idItem}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem getParameterItemFromList(@PathVariable(value = "idList")Long idList, @PathVariable(value = "idItem")Long idItem, HttpServletResponse response){
        ParameterItem parameterItem = parameterListFacade.getParameterItem(idList, idItem);
        if (!(parameterItem==null)){
            response.setStatus(200);
            return parameterItem;
        } else {response.setStatus(400); return null;}
    }


    //TODO Potrebno je napraviti razliku izmedju System i ApplicationParameterList, jer implementacija samo ParameterLista ne moze da se mapira iz JSON-a

    @RequestMapping(value = "/parameter-list", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList addParameterList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        ParameterList list = parameterListFacade.store(parameterList);
        if (!(list==null)){
            response.setStatus(201);
            return list;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem addParameterToList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem pItem = parameterListFacade.saveParameterToList(idList, parameterItem);
        if (!(pItem==null)){
            response.setStatus(201);
            return pItem;
        } else {response.setStatus(400); return null;}
    }


    @RequestMapping(value = "/parameter-lists", method = RequestMethod.PUT,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterList editList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        ParameterList list =  parameterListFacade.update(parameterList);
        if (!(list==null)){
            response.setStatus(200);
            return list;
        } else {response.setStatus(400); return null;}
    }

    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.PUT,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ParameterItem editParameterInList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        ParameterItem pItem = parameterListFacade.updateParameterInList(idList, parameterItem);
        if (!(pItem==null)){
            response.setStatus(200);
            return pItem;
        } else {response.setStatus(400); return null;}
    }


    //TODO void je definisan u DAO

    @RequestMapping(value = "/parameter-lists", method = RequestMethod.DELETE,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteList(@RequestBody SystemParameterList parameterList, HttpServletResponse response){
        parameterListFacade.remove(parameterList);
    }


    @RequestMapping(value = "/parameter-lists/{idList}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteParameterFromList(@PathVariable(value = "idList") Long idList, @RequestBody ParameterItem parameterItem, HttpServletResponse response){
        parameterListFacade.deleteFromList(idList, parameterItem);
    }


}

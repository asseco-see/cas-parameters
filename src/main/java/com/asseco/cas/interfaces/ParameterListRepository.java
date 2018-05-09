package com.asseco.cas.interfaces;

import com.asseco.cas.parameters.domain.ApplicationParameterList;
import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.asseco.cass.persist.EntityRepository;


import java.util.List;

public interface ParameterListRepository extends EntityRepository<ParameterList> {


    List<ParameterList> findAll();

    ParameterList findById(Long idParameterList);

    List<ParameterList> findByName(String parameterListName);

    List<ApplicationParameterList> findAllApplicationLists();

    List<SystemParameterList> findAllSystemLists();

    ParameterList update(ParameterList parameterList);

    void remove(Long idList);

}

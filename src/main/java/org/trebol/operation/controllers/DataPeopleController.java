package org.trebol.operation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.trebol.config.OperationProperties;
import org.trebol.jpa.entities.Person;
import org.trebol.jpa.services.GenericCrudJpaService;
import org.trebol.jpa.services.IPredicateJpaService;
import org.trebol.operation.GenericDataController;
import org.trebol.pojo.DataPagePojo;
import org.trebol.pojo.PersonPojo;

import java.util.Map;

/**
 * Controller that maps API resource to read existing People
 *
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
@RestController
@RequestMapping("/data/people")
@PreAuthorize("isAuthenticated()")
public class DataPeopleController
  extends GenericDataController<PersonPojo, Person> {

  @Autowired
  public DataPeopleController(OperationProperties globals,
                              GenericCrudJpaService<PersonPojo, Person> crudService,
                              IPredicateJpaService<Person> predicateService) {
    super(globals, crudService, predicateService);
  }

  @GetMapping({"", "/"})
  @PreAuthorize("hasAuthority('people:read')")
  public DataPagePojo<PersonPojo> readMany(@RequestParam Map<String, String> allRequestParams) {
    return super.readMany(allRequestParams);
  }
}

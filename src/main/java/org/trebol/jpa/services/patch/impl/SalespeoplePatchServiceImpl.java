/*
 * Copyright (c) 2023 The Trebol eCommerce Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.trebol.jpa.services.patch.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trebol.api.models.SalespersonPojo;
import org.trebol.common.Utils;
import org.trebol.common.exceptions.BadInputException;
import org.trebol.jpa.entities.Person;
import org.trebol.jpa.entities.Salesperson;
import org.trebol.jpa.services.patch.PeoplePatchService;
import org.trebol.jpa.services.patch.SalespeoplePatchService;

import java.util.Map;

@Service
public class SalespeoplePatchServiceImpl
  implements SalespeoplePatchService {
  private final PeoplePatchService peoplePatchService;

  @Autowired
  public SalespeoplePatchServiceImpl(
    PeoplePatchService peoplePatchService
  ) {
    this.peoplePatchService = peoplePatchService;
  }

  @Override
  public Salesperson patchExistingEntity(Map<String, Object> changes, Salesperson existing) throws BadInputException {
    Salesperson target = new Salesperson(existing);

    Map<String, Object> personChanges = Utils.copyMapWithUnprefixedEntries(changes, "person.");
    if (!personChanges.isEmpty()) {
      Person existingPerson = existing.getPerson();
      Person person = peoplePatchService.patchExistingEntity(personChanges, existingPerson);
      target.setPerson(person);
    }

    return target;
  }

  @Override
  public Salesperson patchExistingEntity(SalespersonPojo changes, Salesperson existing) throws BadInputException {
    throw new UnsupportedOperationException("This method has been deprecated");
  }
}

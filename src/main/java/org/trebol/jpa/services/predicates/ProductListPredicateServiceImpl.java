/*
 * Copyright (c) 2022 The Trebol eCommerce Project
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

package org.trebol.jpa.services.predicates;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.trebol.jpa.entities.ProductList;
import org.trebol.jpa.entities.QProductList;
import org.trebol.jpa.services.PredicateService;

import java.util.Map;

@Transactional
@Service
public class ProductListPredicateServiceImpl
  implements PredicateService<ProductList> {
  private final Logger logger = LoggerFactory.getLogger(ProductListPredicateServiceImpl.class);

  @Override
  public QProductList getBasePath() {
    return QProductList.productList;
  }

  @Override
  public Predicate parseMap(Map<String, String> queryParamsMap) {
    BooleanBuilder predicate = new BooleanBuilder();
    if (queryParamsMap != null) {
      for (String paramName : queryParamsMap.keySet()) {
        String value = queryParamsMap.get(paramName);
        try {
          switch (paramName) {
            case "name":
            case "listName":
              return predicate.and(getBasePath().name.eq(value));
            case "code":
            case "listCode":
              return predicate.and(getBasePath().code.eq(value));
            case "nameLike":
              predicate.and(getBasePath().name.likeIgnoreCase("%" + value + "%"));
              break;
            case "codeLike":
              predicate.and(getBasePath().code.likeIgnoreCase("%" + value + "%"));
              break;
            default:
              break;
          }
        } catch (NumberFormatException exc) {
          logger.warn("Param '{}' couldn't be parsed as number (value: '{}')", paramName, value, exc);
        }
      }
    }
    return predicate;
  }
}

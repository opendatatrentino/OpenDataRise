/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.services;

import eu.trentorise.opendata.opendatarise.semantics.model.entity.IEntityType;
import eu.trentorise.opendata.opendatarise.semantics.services.model.ICorrespondence;
import java.util.List;

/**
 *
 * @author Juan
 */
public interface ISemanticMatchingService {
    
    
    ICorrespondence matchSchemas(String souceName, List<String> sourceColumns,
            IEntityType targetEntityType);
    
}

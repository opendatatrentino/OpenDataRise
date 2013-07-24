/**
 * *****************************************************************************
 * Copyright 2012-2013 Trento Rise (www.trentorise.eu/)
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License (LGPL)
 * version 2.1 which accompanies this distribution, and is available at
 *
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *******************************************************************************
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.trentorise.opendata.opendatarise.semantics.services;

import eu.trentorise.opendata.opendatarise.semantics.model.entity.IAttribute;
import eu.trentorise.opendata.opendatarise.semantics.model.entity.IEntity;
import eu.trentorise.opendata.opendatarise.semantics.model.entity.IValue;

/**
 *
 * @author Juan
 */
public interface IEntityServices {
    
    public void createEntity(IEntity entity);
    
    public void updateEntity(IEntity entity);
    
    public void deleteEntity(IEntity entity);
    
    public void readEntity(IEntity entity);
    
    public void addAttribute(IEntity entity, IAttribute attribute);
    
    public void addAttributeValue(IEntity entity, IAttribute attribute, 
            IValue value);
    
    public void updateAttributeValue(IEntity entity, IAttribute attribute, 
            IValue newValue);
    
}

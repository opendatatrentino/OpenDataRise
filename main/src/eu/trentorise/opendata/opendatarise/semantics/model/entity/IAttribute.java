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
package eu.trentorise.opendata.opendatarise.semantics.model.entity;


import java.util.List;

/**
 *
 * @author Juan
 */
public interface IAttribute {
    
    public Long getGUID();

    public IAttributeDef getAttributeDefinition();

    public void setAttributeDefinition(IAttributeDef ad);

    public void addValue(IValue value);
    
    public void removeValue(IValue value);
    
    public List<IValue> getValues();

    public String getFirstValue();

    public Long getValuesCount();

}

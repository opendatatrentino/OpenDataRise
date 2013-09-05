/*
CKANClient-J - Data Catalogue Software client in Java
Copyright (C) 2013 Newcastle University

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.ckan.result.impl;

import java.util.List;
import org.ckan.resource.impl.Status;
import org.ckan.result.CKANResult;

/**
 *
 * @author      Andrew Martin <andrew.martin@ncl.ac.uk>
 * @version     1.8
 * @since       2013-04-15
 */
public class ResourceStatusResult extends CKANResult
{
    public List<Status> result;
}
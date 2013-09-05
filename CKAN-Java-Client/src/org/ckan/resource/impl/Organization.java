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

package org.ckan.resource.impl;

import org.ckan.resource.CKANResource;

/**
 *
 * @author      Andrew Martin <andrew.martin@ncl.ac.uk>
 * @version     1.8
 * @since       2013-02-18
 */
public class Organization extends CKANResource
{
    public String approval_status;
    public String created;
    public String description;
    public String id;
    public String image_url;
    public boolean is_organization;
    public String name;
    public String revision_id;
    public String revision_timestamp;
    public String state;
    public String title;
    public String type;
}

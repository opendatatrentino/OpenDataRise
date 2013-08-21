/*
CKANClient-J - Data Catalogue Software client in Java
Copyright (C) 2013 Newcastle University
Copyright (C) 2012 Open Knowledge Foundation

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
 * Represents a single resource within a Dataset
 *
 * @author      Andrew Martin <andrew.martin@ncl.ac.uk>, Ross Jones <ross.jones@okfn.org>
 * @version     1.8
 * @since       2012-05-01
 */
public class Resource extends CKANResource
{
    private String cache_last_updated;
    private String cache_url;
    private String created;
    private String description;
    private String format;
    private String hash;
    private String id;
    private String last_modified;
    private String message;
    private String mimetype;
    private String mimetype_inner;
    private String name;
    private String package_id;
    private int position;
    private String resource_group_id;
    private String resource_type;
    private String revision_id;
    private String revision_timestamp;
    private int size;
    private String state;
    private TrackingSummary tracking_summary;
    private String url;
    private String webstore_last_updated;
    private String webstore_url;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
        
/*tracking_summary: {
total: 3,
recent: 3
},*/
    
    public Resource() {}

    public String getResource_group_id() {
        return resource_group_id;
    }

    public void setResource_group_id(String resource_group_id) {
        this.resource_group_id = resource_group_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return created;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setWebstore_url(String webstore_url) {
        this.webstore_url = webstore_url;
    }

    public String getWebstore_url() {
        return webstore_url;
    }

    public void setCache_last_updated(String cache_last_updated) {
        this.cache_last_updated = cache_last_updated;
    }

    public String getCache_last_updated() {
        return cache_last_updated;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setWebstore_last_updated(String webstore_last_updated) {
        this.webstore_last_updated = webstore_last_updated;
    }

    public String getWebstore_last_updated() {
        return webstore_last_updated;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype_inner(String mimetype_inner) {
        this.mimetype_inner = mimetype_inner;
    }

    public String getMimetype_inner() {
        return mimetype_inner;
    }

    public void setCache_url(String cache_url) {
        this.cache_url = cache_url;
    }

    public String getCache_url() {
        return cache_url;
    }

}







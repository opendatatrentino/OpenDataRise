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
public interface IEntity {

    public Long getLocalID();

    public Long getGUID();

    public String getURI();

    public String getUrl();

    public void setUrl(String url);

    public Long getOwnerId();

    public void setOwnerId(Long ownerId);

    public String getExternalId();

    public void setExternalId(String externalId);

    public List<IAttribute> getAttributes();

    public void setAttributes(List<IAttribute> attributes);

    public void addAttribute(IAttribute attribute);

    public IEntityType gettype();

    public void setEtype(IEntityType type);
}

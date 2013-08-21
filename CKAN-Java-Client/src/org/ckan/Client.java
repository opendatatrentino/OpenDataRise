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

package org.ckan;

import org.ckan.resource.impl.Dataset;
import org.ckan.resource.impl.Group;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import org.ckan.resource.impl.Resource;
import org.ckan.resource.impl.Revision;
import org.ckan.resource.impl.User;
import org.ckan.result.CKANResult;
import org.ckan.result.impl.ActivityResult;
import org.ckan.result.list.impl.DatasetList;
import org.ckan.result.impl.BooleanResult;
import org.ckan.result.impl.IntegerResult;
import org.ckan.result.impl.StringResult;
import org.ckan.result.impl.DatasetResult;
import org.ckan.result.impl.GroupResult;
import org.ckan.result.impl.DatasetSearchResult;
import org.ckan.result.impl.FollowingResult;
import org.ckan.result.impl.MembershipResult;
import org.ckan.result.impl.ResourceResult;
import org.ckan.result.impl.ResourceStatusResult;
import org.ckan.result.impl.RevisionResult;
import org.ckan.result.impl.UserResult;
import org.ckan.result.list.impl.ActivityList;
import org.ckan.result.list.impl.LicenceList;
import org.ckan.result.list.impl.RevisionList;
import org.ckan.result.list.impl.StringList;
import org.ckan.result.list.impl.UserList;

/**
 * The primary interface to this package the Client class is responsible
 * for managing all interactions with a given connection.
 *
 * @author      Andrew Martin <andrew.martin@ncl.ac.uk>, Ross Jones <ross.jones@okfn.org>
 * @version     1.8
 * @since       2013-02-18
 */
public final class Client
{
    protected Connection connection = null;
    protected Gson gson;
    private int DEFAULT_SEARCH_FACET_LIMIT = -1; /** Unlimited **/
    private int DEFAULT_SEARCH_FACET_MIN_COUNT = 1;
    private int DEFAULT_SEARCH_FIRST_ROW = 0;
    private int DEFAULT_SEARCH_MAX_RETURNED_ROWS = 100;
    private boolean DEBUG = false;
    private boolean DEBUG_ALL_CALLS = false;

    private Client(){}
    
    /**
    * Constructs a new Client for making requests to a remote CKAN instance.
    *
    * @param  c A Connection object containing info on the location of the
    *         CKAN Instance.
    * @param  apikey A user's API Key sent with every request.
    */
    public Client(Connection c, String apikey)
    {
        this.connection = c;
        this.connection.setApiKey(apikey);
        this.gson = new Gson();
    }

    /**
     * Same as default constructor, but allows you to specify
     * whether all calls will be debugged<br/>
     * <br/>
     * WARNING: Specifying false for debugAllCalls does not
     * permanently turn off debugging, so debugThis() calls
     * will still work
     * 
     * @param c
     * @param apikey
     * @param debugAllCalls 
     */
    public Client(Connection c, String apikey, boolean debugAllCalls)
    {
        this.connection = c;
        this.connection.setApiKey(apikey);
        this.gson = new Gson();
        this.DEBUG_ALL_CALLS = debugAllCalls;
    }

    /**
     * Use this to return the JSON for this client call ONLY,
     * the client call after that will not be debugged unless
     * you specify debugThis() again
     * 
     * @return Client
     */
    public Client debugThis()
    {
        this.DEBUG = true;
        return this;
    }

    /**
     * Use this to return the JSON for this client call ONLY,
     * the client call after that will not be debugged unless
     * you specify debug(true) again.
     * 
     * This differs from debugThis() as you can set debug on every
     * call a use your own code to set a default. E.g....<br/>
     * <br/>
     * boolean myDebugOption = true;<br/>
     * <br/>
     * Client.debugThis().methodCall(); //Debugged<br/>
     * Client.debugThis(myDebugOption).methodCall(); //Debugged<br/>
     * Client.debugThis().methodCall(); //Debugged<br/>
     * Client.debugThis(false).methodCall(); //Not Debugged<br/>
     * Client.debugThis().methodCall(); //Debugged<br/>
     * Client.debugThis(myDebugOption).methodCall(); //Debugged<br/>
     * Client.methodCall() //Not Debugged<br/>
     * myDebugOption = false;<br/>
     * Client.debugThis(myDebugOption).methodCall(); //Not Debugged<br/>
     * Client.debugThis().methodCall(); //Debugged<br/>
     * Client.methodCall() //Not Debugged<br/>
     * 
     * @return Client
     */
    public Client debugThis(boolean debug)
    {
        this.DEBUG = debug;
        return this;
    }
    
    /**
     * Allows you to permanently turn the debugging
     * on or off, this means even a call without
     * specifying debugThis() would be debugged.<br/>
     * <br/>
     * WARNING: This also overrides debugThis(false)
     * 
     * @param debugAllCalls
     * @return 
     */
    public Client debugAllCalls(boolean debugAllCalls)
    {
        this.DEBUG = debugAllCalls;
        this.DEBUG_ALL_CALLS = debugAllCalls;
        return this;
    }
    
    /**
    * Loads a JSON string into a class of the specified type.
    */
    protected <T> T getGsonObjectFromJson(Class<T> cls, String data, String action) throws CKANException
    {
        Object o = gson.fromJson(data, cls);
        handleError((CKANResult)o,data,action);
        return (T)o;
    }

    protected String getJsonFromGsonObject(Object o)
    {
        return gson.toJson(o);
    }
    
    /**
    * Handles error responses from CKAN
    *
    * When given a JSON string it will generate a valid CKANException
    * containing all of the error messages from the JSON.
    *
    * @param  json The JSON response
    * @param  action The name of the action calling this for the primary
    *         error message.
    * @throws A CKANException containing the error messages contained in the
    *         provided JSON.
    */
    protected void handleError(String json, String action) throws CKANException
    {
        CKANException exception = new CKANException("Error at: Client."+action+"()");
        HashMap hm  = gson.fromJson(json,HashMap.class);
        Map<String,Object> m = (Map<String,Object>)hm.get("error");
        for (Map.Entry<String,Object> entry : m.entrySet())
        {
            if (!entry.getKey().startsWith("_"))
            {
                exception.addError(entry.getValue()+" - "+entry.getKey());
            }
        }
        throw exception;
    }

    protected void handleError(CKANResult result, String json, String action) throws CKANException
    {
        if(!result.success)
        {
            handleError(json, action);
        }
    }
    
    protected String postAndReturnTheJSON(String uri, String jsonParams) throws CKANException
    {
        String json = this.connection.post(uri,jsonParams);
        if(DEBUG_ALL_CALLS||DEBUG)
        {
            System.out.println(json);
            DEBUG = false; /** Reset debugging to default (false) **/
        }
        return json;
    }

    protected <T> T getGsonResult(Class<T> cls, String uri, String jsonParams, String action) throws CKANException
    {
        return getGsonObjectFromJson(cls,postAndReturnTheJSON(uri,jsonParams),action);
    }
    
    protected DatasetList getDatasetList(String uri, String jsonParams, String action) throws CKANException
    {
        return getGsonResult(DatasetList.class,uri,jsonParams,action);
    }

    protected UserList getUserList(String uri, String jsonParams, String action) throws CKANException
    {
        return getGsonResult(UserList.class,uri,jsonParams,action);
    }
    
    public Gson getGsonObject()
    {
        return this.gson;
    }
    
    /*********************************************************************************************/

    /** WIP **/
    
    /** Activity_renders
     * https://github.com/icmurray/ckan/blob/master/ckan/logic/action/get.py
     **/

    public void createActivity(String userId, String objectId, String activityType) throws CKANException
    {
        getGsonObjectFromJson(ActivityResult.class,postAndReturnTheJSON("/api/action/activity_create","{\"user_id\":\""+userId+"\",\"object_id\":\""+objectId+"\",\"activity_type\":\""+activityType+"\"}"),"createActivity");
    }

    /**
    * Creates a dataset on the server
    *
    * Takes the provided dataset and sends it to the server to
    * perform an create, and then returns the newly created dataset.
    *
    * @param  dataset A dataset instance
    * @returns The Dataset as it now exists
    * @throws A CKANException if the request fails
    */
    public Dataset createDataset(Dataset dataset) throws CKANException
    {
        DatasetResult dr = getGsonObjectFromJson(DatasetResult.class,postAndReturnTheJSON("/api/action/package_create",getJsonFromGsonObject(dataset)),"createDataset");
        return dr.result;
    }

    /**
    * Creates a Group on the server
    *
    * Takes the provided Group and sends it to the server to
    * perform an create, and then returns the newly created Group.
    *
    * @param  group A Group instance
    * @returns The Group as it now exists on the server
    * @throws A CKANException if the request fails
    */
    public Group createGroup(Group group) throws CKANException
    {
        GroupResult r = getGsonObjectFromJson(GroupResult.class,postAndReturnTheJSON("/api/action/package_create",getJsonFromGsonObject(group)),"createGroup");
        return r.result;
    }

    /********************/

    public MembershipResult createMember(String id, String object, String object_type, String capacity) throws CKANException
    {
        return getGsonObjectFromJson(MembershipResult.class,this.postAndReturnTheJSON("/api/action/member_create","{\"id\":\""+id+"\",\"object\":\""+object+"\",\"object_type\":\""+object_type+"\",\"capacity\":\""+capacity+"\"}"),"createMember");
    }
    
    /** WIP **/

    public void createRelated(String title, String type) throws CKANException
    {
        //getGsonObjectFromJson(StringResult.class,this.postAndReturnTheJSON("/api/action/related_create","{\"dataset_id\":\"\",\"description\":\"\",\"id\":\"\",\"title\":\""+title+"\",\"type\":\""+type+"\",\"url\":\"\"}"),"createRelated");
        getGsonObjectFromJson(StringResult.class,this.postAndReturnTheJSON("/api/action/related_create","{\"title\":\""+title+"\",\"type\":\""+type+"\"}"),"createRelated");
    }
    
    public void createRelated(String dataset_id, String description, String id, String image_url, String title, String type, String url) throws CKANException
    {
        getGsonObjectFromJson(StringResult.class,this.postAndReturnTheJSON("/api/action/related_create","{\"dataset_id\":\""+dataset_id+"\",\"description\":\""+description+"\",\"id\":\""+id+"\",\"title\":\""+title+"\",\"type\":\""+type+"\",\"url\":\""+url+"\"}"),"createRelated");
    }
    
    /**
    * Deletes a dataset
    *
    * Deletes the dataset specified with the provided name/id
    *
    * @param  name The name or ID of the dataset to delete
    * @throws A CKANException if the request fails
    */
    public void deleteDataset(String name) throws CKANException
    {
        getGsonObjectFromJson(DatasetResult.class,postAndReturnTheJSON("/api/action/package_delete","{\"id\":\""+name+"\"}"),"deleteDataset");
    }

    /**
    * Deletes a Group
    *
    * Deletes the group specified with the provided name/id
    *
    * @param  name The name or ID of the group to delete
    * @throws A CKANException if the request fails
    */
    public void deleteGroup(String name) throws CKANException
    {
        getGsonObjectFromJson(GroupResult.class,postAndReturnTheJSON("/api/action/group_delete","{\"id\":\""+name+"\"}"),"deleteGroup");
    }

    /********************/

    public void deleteRelated(String id) throws CKANException
    {
        getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/related_delete","{\"id\":\""+id+"\"}"),"deleteRelated");
    }

    /********************/

    public void deleteTaskStatus(String id) throws CKANException
    {
        getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/task_status_delete","{\"id\":\""+id+"\"}"),"deleteTaskStatus");
    }

    /********************/
    
    public void deleteVocabulary(String id) throws CKANException
    {
        getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/vocabulary_delete","{\"id\":\""+id+"\"}"),"deleteVocabulary");
    }

    /********************/

    public FollowingResult followDataset(String id) throws CKANException
    {
        return getGsonObjectFromJson(FollowingResult.class,postAndReturnTheJSON("/api/action/follow_dataset","{\"id\":\""+id+"\"}"),"followDataset");
    }

    /********************/

    public FollowingResult followUser(String id) throws CKANException
    {
        return getGsonObjectFromJson(FollowingResult.class,postAndReturnTheJSON("/api/action/follow_user","{\"id\":\""+id+"\"}"),"followUser");
    }
    
    /********************/

    public ActivityList getActivityDetailList(String id) throws CKANException
    {
        return getGsonObjectFromJson(ActivityList.class,postAndReturnTheJSON("/api/action/activity_detail_list","{\"id\":\""+id+"\"}"),"getActivityDetailList");
    }

    /********************/
    
    public BooleanResult getAmFollowingDataset(String id) throws CKANException
    {
        return getGsonObjectFromJson(BooleanResult.class,postAndReturnTheJSON("/api/action/am_following_dataset","{\"id\":\""+id+"\"}"),"amFollowingDataset");
    }

    /********************/
    
    public BooleanResult getAmFollowingUser(String id) throws CKANException
    {
        return getGsonObjectFromJson(BooleanResult.class,postAndReturnTheJSON("/api/action/am_following_user","{\"id\":\""+id+"\"}"),"amFollowingUser");
    }

    /********************/

    public DatasetList getCurrentPackageListWithResources(int limit, int page) throws CKANException
    {
        return getDatasetList("/api/action/current_package_list_with_resources","{\"limit\":\""+limit+"\",\"page\":\""+page+"\"}","getCurrentPackageListWithResources");
    }
    
    /********************/

    public ActivityList getDashboardActivityList(String id) throws CKANException
    {
        return getGsonObjectFromJson(ActivityList.class,postAndReturnTheJSON("/api/action/dashboard_activity_list","{\"id\":\""+id+"\"}"),"getDashboardActivityList");
    }

    /********************/

    public StringResult getDashboardActivityListHTML(String id) throws CKANException
    {
        return getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/dashboard_activity_list_html","{\"id\":\""+id+"\"}"),"getDashboardActivityListHTML");
    }

    /********************/

    public IntegerResult getDatasetFolloweeCount(String id) throws CKANException
    {
        return getGsonObjectFromJson(IntegerResult.class,postAndReturnTheJSON("/api/action/dataset_followee_count","{\"id\":\""+id+"\"}"),"getDatasetFolloweeCount");
    }

    /********************/

    public DatasetList getDatasetFolloweeList(String id) throws CKANException
    {
        return getDatasetList("/api/action/dataset_followee_list","{\"id\":\""+id+"\"}","getDatasetFolloweeList");
    }

    /********************/

    public IntegerResult getDatasetFollowerCount(String id) throws CKANException
    {
        return getGsonObjectFromJson(IntegerResult.class,postAndReturnTheJSON("/api/action/dataset_follower_count","{\"id\":\""+id+"\"}"),"getDatasetFollowerCount");
    }

    /********************/

    public DatasetList getDatasetFollowerList(String id) throws CKANException
    {
        return getDatasetList("/api/action/dataset_follower_list","{\"id\":\""+id+"\"}","getDatasetFollowerList");
    }

    /**
    * Retrieves a dataset
    *
    * Retrieves the dataset with the given name, or ID, from the CKAN
    * connection specified in the Client constructor.
    *
    * @param  name The name or ID of the dataset to fetch
    * @returns The Dataset for the provided name.
    * @throws A CKANException if the request fails
    */
    public Dataset getDataset(String name) throws CKANException
    {
        DatasetResult dr = getGsonObjectFromJson(DatasetResult.class,postAndReturnTheJSON("/api/action/package_show","{\"id\":\""+name+"\"}"),"getDataset");
        return dr.result;
    }

    /********************/

    public StringList getDatasetList() throws CKANException
    {
        return getGsonObjectFromJson(StringList.class,postAndReturnTheJSON("/api/action/package_list","{}"),"getDatasetList");
    }

    /********************/

    public StringList getFormatAutocomplete(String query, int limit) throws CKANException
    {
        return getGsonObjectFromJson(StringList.class,postAndReturnTheJSON("/api/action/format_autocomplete","{\"q\":\""+query+"\",\"limit\":\""+limit+"\"}"),"getFormatAutocomplete");
    }

    /**
    * Retrieves a group
    *
    * Retrieves the group with the given name, or ID, from the CKAN
    * connection specified in the Client constructor.
    *
    * @param  name The name or ID of the group to fetch
    * @returns The Group instance for the provided name.
    * @throws A CKANException if the request fails
    */
    public Group getGroup(String id) throws CKANException
    {
        GroupResult r = getGsonObjectFromJson(GroupResult.class,postAndReturnTheJSON("/api/action/group_show","{\"id\":\""+id+"\"}"),"getGroup");
        return r.result;
    }

    /********************/

    public ActivityList getGroupActivityList(String id) throws CKANException
    {
        return getGsonObjectFromJson(ActivityList.class,postAndReturnTheJSON("/api/action/group_activity_list","{\"id\":\""+id+"\"}"),"getGroupActivityList");
    }

    /********************/

    public StringResult getGroupActivityListHTML(String id) throws CKANException
    {
        return getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/group_activity_list_html","{\"id\":\""+id+"\"}"),"getGroupActivityList");
    }

    /*******************/ /** WIP **/

    public StringList getGroupList() throws CKANException
    {
        /*
         * OPT DEPR : order_by <- don't include?
         * OPT : sort - name/packages
         * OPT : sort order - asc/"desc?"
         * OPT : groups - ["group1","group2"]
         * OPT : all_fields - full group instead of just names (i.e. verbosity)
         */
        
        return getGsonObjectFromJson(StringList.class,postAndReturnTheJSON("/api/action/group_list","{\"groups\":[\"test-group\"]}"),"getGroupList");
    }

    /*******************/

    public StringList getGroupListAuthz(boolean availableOnly) throws CKANException
    {
        return getGsonObjectFromJson(StringList.class,postAndReturnTheJSON("/api/action/group_list_authz","{\"available_only\":\""+availableOnly+"\"}"),"getGroupListAuthz");
    }

    /*******************/

    public DatasetList getGroupPackages(String id, int limit) throws CKANException
    {
        return getGsonObjectFromJson(DatasetList.class,postAndReturnTheJSON("/api/action/group_package_show","{\"id\":\""+id+"\",\"limit\":\""+limit+"\"}"),"getGroupPackages");
    }

    /*******************/

    public RevisionList getGroupRevisions(String id) throws CKANException
    {
        return getGsonObjectFromJson(RevisionList.class,postAndReturnTheJSON("/api/action/group_revision_list","{\"id\":\""+id+"\"}"),"getGroupRevisions");
    }

    /********************/

    public LicenceList getLicenceList() throws CKANException
    {
        return getGsonObjectFromJson(LicenceList.class,postAndReturnTheJSON("/api/action/licence_list","{}"),"getLicenceList");
    }

    /********************/ /** WIP **/

    public void getMemberList(String id, String object_type, String capacity) throws CKANException
    {
        getGsonObjectFromJson(LicenceList.class,postAndReturnTheJSON("/api/action/member_list","{\"id\":\""+id+"\",\"object_type\":\""+object_type+"\",\"capacity\":\""+capacity+"\"}"),"getMemberList");
    }
    
    /********************/

    public ActivityList getPackageActivityList(String id) throws CKANException
    {
        return getGsonObjectFromJson(ActivityList.class,postAndReturnTheJSON("/api/action/package_activity_list","{\"id\":\""+id+"\"}"),"getPackageActivityList");
    }

    /********************/  /** WIP **/

    public void getPackageRelationships(String id) throws CKANException
    {
        getGsonObjectFromJson(StringList.class,postAndReturnTheJSON("/api/action/package_relationships_list","{\"id\":\""+id+"\"}"),"getPackageRelationships");
    }
    
    /********************/

    public RevisionList getPackageRevisions(String id) throws CKANException
    {
        return getGsonObjectFromJson(RevisionList.class,postAndReturnTheJSON("/api/action/package_revision_list","{\"id\":\""+id+"\"}"),"getPackageRevisions");
    }
    
    /********************/

    public ActivityList getRecentlyChangedDatasetsActivityList() throws CKANException
    {
        return getGsonObjectFromJson(ActivityList.class,postAndReturnTheJSON("/api/action/recently_changed_packages_activity_list","{}"),"getRecentlyChangedDatasetsActivityList");
    }

    /********************/

    public String getRecentlyChangedDatasetsActivityListHTML(String id) throws CKANException
    {
        StringResult s = getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/recently_changed_packages_activity_list_html","{\"id\":\""+id+"\"}"),"getRecentlyChangedDatasetsActivityListHTML");
        return s.result;
    }

    /********************/ /** WIP **/

    public void getRelated(String id) throws CKANException
    {
        getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/related_show","{\"id\":\""+id+"\"}"),"getRelated");
    }

    /********************/ /** WIP **/

    public void getRelatedList(String id, Dataset ds) throws CKANException
    {
        if(id!=null&&!id.isEmpty())
        {
            getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/related_list","{\"id\":\""+id+"\"}"),"getRelatedList");
        }
        else
        {
            getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/related_list","{\"dataset\":\""+ds.toJson(gson) +"\"}"),"getRelatedList");
        }
    }

    /********************/

    public Resource getResource(String id) throws CKANException
    {
        ResourceResult rr = getGsonObjectFromJson(ResourceResult.class,postAndReturnTheJSON("/api/action/resource_show","{\"id\":\""+id+"\"}"),"getResource");
        return rr.result;
    }

    /********************/ /** WIP **/

    public ResourceStatusResult getResourceStatus(String id) throws CKANException
    {
        return getGsonObjectFromJson(ResourceStatusResult.class,postAndReturnTheJSON("/api/action/resource_status_show","{\"id\":\""+id+"\"}"),"getResourceStatus");
    }

    /********************/ /** Does this need a site id as a param??? **/

    public StringList getRevisionList() throws CKANException
    {
        return getGsonObjectFromJson(StringList.class,postAndReturnTheJSON("/api/action/revision_list","{\"q\":\"\"}"),"getRevisionList");
    }

    /********************/

    public Revision getRevision(String id) throws CKANException
    {
        RevisionResult rr = getGsonObjectFromJson(RevisionResult.class,postAndReturnTheJSON("/api/action/revision_show","{\"id\":\""+id+"\"}"),"getRevision");
        return rr.result;
    }

    /********************/ /** WIP **/

    public void getRolesList(String domainObject, String user, String authorizationGroup) throws CKANException
    {
        /*return*/ getGsonObjectFromJson(StringList.class,postAndReturnTheJSON("/api/action/roles_show","{\"domain_object\":\""+domainObject+"\",\"user\":\""+user+"\",\"authorization_group\":\""+authorizationGroup+"\"}"),"getRolesList");
    }

    /********************/

    public ActivityList getUserActivityList(String id) throws CKANException
    {
        return getGsonObjectFromJson(ActivityList.class,postAndReturnTheJSON("/api/action/user_activity_list","{\"id\":\""+id+"\"}"),"getUserActivityList");
    }

    /********************/

    public StringResult getUserActivityListHTML(String id) throws CKANException
    {
        return getGsonObjectFromJson(StringResult.class,postAndReturnTheJSON("/api/action/user_activity_list_html","{\"id\":\""+id+"\"}"),"getUserActivityListHTML");
    }
    
    /********************/

    public UserList getUserAutocomplete(String query, int limit) throws CKANException
    {
        return getUserList("/api/action/user_autocomplete","{\"q\":\""+query+"\",\"limit\":\""+limit+"\"}","getUserAutocomplete");
    }

    /********************/

    public IntegerResult getUserFolloweeCount(String id) throws CKANException
    {
        return getGsonObjectFromJson(IntegerResult.class,postAndReturnTheJSON("/api/action/user_followee_count","{\"id\":\""+id+"\"}"),"getUserFolloweeCount");
    }

    /********************/

    public UserList getUserFolloweeList(String id) throws CKANException
    {
        return getUserList("/api/action/user_followee_list","{\"id\":\""+id+"\"}","getUserFolloweeList");
    }

    /********************/

    public IntegerResult getUserFollowerCount(String id) throws CKANException
    {
        return getGsonObjectFromJson(IntegerResult.class,postAndReturnTheJSON("/api/action/user_follower_count","{\"id\":\""+id+"\"}"),"getUserFollowerCount");
    }

    /********************/

    public UserList getUserFollowerList(String id) throws CKANException
    {
        return getUserList("/api/action/user_follower_list","{\"id\":\""+id+"\"}","getUserFollowerList");
    }

    /********************/

    public UserList getUserList(String query, User.OrderBy orderBy) throws CKANException
    {
        return getUserList("/api/action/user_list","{\"q\":\""+query+"\",\"order_by\":\""+orderBy+"\"}","getUserList");
    }
    
    /********************/

    public User getUser(String id) throws CKANException
    {
        UserResult ur = getGsonObjectFromJson(UserResult.class,postAndReturnTheJSON("/api/action/user_show","{\"id\":\""+id+"\"}"),"getUser");
        return ur.result;
    }

    public User getUser(User user) throws CKANException
    {
        String uid = user.getId();
        String name = user.getName();
        /* If uid is not blank use it, failing that use the name,
         * failing that just send a blank string
         */
        String id = uid!=null&&!uid.equals("")?uid:name!=null&&!name.equals("")?name:"";
        return getUser(id);
    }

    /** WIP **/
    
    public void getVocabulary(String id) throws CKANException
    {
        getGsonObjectFromJson(StringList.class,postAndReturnTheJSON("/api/action/vocabulary_show","{\"id\":\""+id+"\"}"),"getVocabulary");
    }
    
    public void getVocabularyList() throws CKANException
    {
        getGsonObjectFromJson(StringList.class,postAndReturnTheJSON("/api/action/vocabulary_list","{}"),"getVocabularyList");
    }

    /********************/ 

    public DatasetSearchResult searchDatasets(String q) throws CKANException
    {
        return searchDatasets(q,"",DEFAULT_SEARCH_MAX_RETURNED_ROWS,"",DEFAULT_SEARCH_FIRST_ROW,"",true,DEFAULT_SEARCH_FACET_MIN_COUNT,DEFAULT_SEARCH_FACET_LIMIT,null);
    }

    public DatasetSearchResult searchDatasets(String q, String filters) throws CKANException
    {
        return searchDatasets(q,filters,DEFAULT_SEARCH_MAX_RETURNED_ROWS,"",DEFAULT_SEARCH_FIRST_ROW,"",true,DEFAULT_SEARCH_FACET_MIN_COUNT,DEFAULT_SEARCH_FACET_LIMIT,null);
    }

    public DatasetSearchResult searchDatasets(String q, String filters, int rows) throws CKANException
    {
        return searchDatasets(q,filters,rows,"",DEFAULT_SEARCH_FIRST_ROW,"",true,DEFAULT_SEARCH_FACET_MIN_COUNT,DEFAULT_SEARCH_FACET_LIMIT,null);
    }

    public DatasetSearchResult searchDatasets(String q, String filters, int rows, String sort) throws CKANException
    {
        return searchDatasets(q,filters,rows,sort,DEFAULT_SEARCH_FIRST_ROW,"",true,DEFAULT_SEARCH_FACET_MIN_COUNT,DEFAULT_SEARCH_FACET_LIMIT,null);
    }

    public DatasetSearchResult searchDatasets(String q, String filters, int rows, String sort, int start) throws CKANException
    {
        return searchDatasets(q,filters,rows,sort,start,"",true,DEFAULT_SEARCH_FACET_MIN_COUNT,DEFAULT_SEARCH_FACET_LIMIT,null);
    }
    
    public DatasetSearchResult searchDatasets(String q, String filters, int rows, String sort, int start, String qf) throws CKANException
    {
        return searchDatasets(q,filters,rows,sort,start,qf,true,DEFAULT_SEARCH_FACET_MIN_COUNT,DEFAULT_SEARCH_FACET_LIMIT,null);
    }
    
    public DatasetSearchResult searchDatasets(String q, String filters, int rows, String sort, int start, String qf, boolean isFacetedResult) throws CKANException
    {
        return searchDatasets(q,filters,rows,sort,start,qf,isFacetedResult,DEFAULT_SEARCH_FACET_MIN_COUNT,DEFAULT_SEARCH_FACET_LIMIT,null);
    }
    
    public DatasetSearchResult searchDatasets(String q, String filters, int rows, String sort, int start, String qf, boolean isFacetedResult, int facetMinCount) throws CKANException
    {
        return searchDatasets(q,filters,rows,sort,start,qf,isFacetedResult,facetMinCount,DEFAULT_SEARCH_FACET_LIMIT,null);
    }
        
    public DatasetSearchResult searchDatasets(String q, String filters, int rows, String sort, int start, String qf, boolean isFacetedResult, int facetMinCount, int facetLimit) throws CKANException
    {
        return searchDatasets(q,filters,rows,sort,start,qf,isFacetedResult,facetMinCount,facetLimit,null);
    }
    
    public DatasetSearchResult searchDatasets(String q, String filters, int rows, String sort, int start, String qf, boolean isFacetedResult, int facetMinCount, int facetLimit, List<String> facetField) throws CKANException
    {
        /*
         * ,\"qf\":\""+qf+"\" -> removed from JSON
         * 
         * Dismax query fields not figured out yet
         */
        if(facetField==null)
        {
            facetField = new ArrayList<String>();
        }
        return getGsonObjectFromJson(DatasetSearchResult.class,postAndReturnTheJSON("/api/action/package_search","{\"q\":\""+q+"\",\"fq\":\""+filters+"\",\"rows\":\""+rows+"\",\"sort\":\""+sort+"\",\"start\":\""+start+"\",\"facet\":\""+isFacetedResult+"\",\"facet.mincount\":\""+facetMinCount+"\",\"facet.limit\":\""+facetLimit+"\",\"facet.field\":\""+gson.toJson(facetField)+"\"}"),"searchPackages");
    }
    
    /********************/

    public FollowingResult unfollowDataset(String id) throws CKANException
    {
        return getGsonObjectFromJson(FollowingResult.class,postAndReturnTheJSON("/api/action/unfollow_dataset","{\"id\":\""+id+"\"}"),"unfollowDataset");
    }
    
    /********************/

    public FollowingResult unfollowUser(String id) throws CKANException
    {
        return getGsonObjectFromJson(FollowingResult.class,postAndReturnTheJSON("/api/action/unfollow_user","{\"id\":\""+id+"\"}"),"unfollowUser");
    }
    
    public DatasetResult updateDataset(Dataset ds) throws CKANException
    {
        return getGsonObjectFromJson(DatasetResult.class,postAndReturnTheJSON("/api/action/package_update",gson.toJson(ds)),"updateDataset");
    }
}







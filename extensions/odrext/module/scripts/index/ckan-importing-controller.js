
var ODRCKAN = {
    
    
    
};
/*global Refine,ODRCKAN */


ODRCKAN.CkanImportingController = function(createProjectUI) {
  
  this._createProjectUI = createProjectUI;
  
  this._parsingPanel = createProjectUI.addCustomPanel();

  // this follows 'gdata' model
  createProjectUI.addSourceSelectionUI({
    label: "CKAN",
    id: "ckan-source",
    ui: new ODRCKAN.CkanSourceUI(this)
  }); 
  
  /*
    Refine.DefaultImportingController.sources.push({
        label: "CKAN",
        id: "ckan-source",
        ui: ODRCKAN.CkanSourceUI
    });   */
  
  //bah doesn't work createProjectUI.selectImportSource(ckanSourceUi);
};

// feel so dirty
ODRCKAN.CkanImportingController.prototype = Refine.DefaultImportingController.prototype;


Refine.CreateProjectUI.controllers.push(ODRCKAN.CkanImportingController);





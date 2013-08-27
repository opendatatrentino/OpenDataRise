
var ODRCKAN = {};
/*global Refine,ODRCKAN */
ODRCKAN.CkanImportingController = function(createProjectUI) {
  this._createProjectUI = createProjectUI;
  
  this._parsingPanel = createProjectUI.addCustomPanel();

  createProjectUI.addSourceSelectionUI({
    label: "CKAN",
    id: "ckan-source",
    ui: new ODRCKAN.CkanSourceUI(this)
  });
  
};
Refine.CreateProjectUI.controllers.push(ODRCKAN.CkanImportingController);



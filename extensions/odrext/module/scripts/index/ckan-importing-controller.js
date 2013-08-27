
var ODRCKAN = {};
/*global Refine,ODRCKAN */
ODRCKAN.CkanImportingController = function(createProjectUI) {
  var ckanSourceUi;
  this._createProjectUI = createProjectUI;
  
  this._parsingPanel = createProjectUI.addCustomPanel();
  ckanSourceUi = new ODRCKAN.CkanSourceUI(this);
  
  createProjectUI.addSourceSelectionUI({
    label: "CKAN",
    id: "ckan-source",
    ui: ckanSourceUi
  });
  //bah doesn't work createProjectUI.selectImportSource(ckanSourceUi);
};
Refine.CreateProjectUI.controllers.push(ODRCKAN.CkanImportingController);


